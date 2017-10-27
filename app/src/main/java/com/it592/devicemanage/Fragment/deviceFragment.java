package com.it592.devicemanage.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.it592.devicemanage.Adapter.DeviceAdapter;
import com.it592.devicemanage.AddDeviceActivity;
import com.it592.devicemanage.Beans.DeviceBean;
import com.it592.devicemanage.MainActivity;
import com.it592.devicemanage.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class deviceFragment extends Fragment {

    List<DeviceBean> deviceBeanList;
    RecyclerView recyclerView;
    DeviceAdapter deviceAdapter;
    TwinklingRefreshLayout swipeRefreshLayout;
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多

    private int limit = 15; // 每页的数据是10条
    private int curPage = 0; // 当前页的编号，从0开始
    private String lastTime = null;
    UpdateReceiver updateReceiver;

    public deviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_device, container, false);
        deviceBeanList = new ArrayList<DeviceBean>();
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_device);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout = (TwinklingRefreshLayout)view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getData(0,STATE_REFRESH);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                //getData(curPage,STATE_MORE);
                getData(curPage,STATE_MORE);

            }
        });
        swipeRefreshLayout.startRefresh();
        recyclerView = (RecyclerView)view.findViewById(R.id.device_lists);
        deviceAdapter = new DeviceAdapter(getContext(),deviceBeanList);
        recyclerView.setAdapter(deviceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.it592.deviceManage.update_device");
        updateReceiver = new UpdateReceiver();
        ((MainActivity)getActivity()).registerReceive(updateReceiver,intentFilter);
        return view;
    }

    public void reload(){
        swipeRefreshLayout.startRefresh();
    }
    public   void getData(int page, final int actionType){
        BmobQuery<DeviceBean> query;
        query = new BmobQuery<DeviceBean>();
        query.order("-createdAt");
        query.setLimit(limit);
        if(actionType == STATE_MORE){
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 只查询小于等于最后一个item发表时间的数据
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            // 跳过之前页数并去掉重复数据
            query.setSkip(page * limit + 1);
            curPage++;
        }else{ //下拉刷新
            page = 0;
            query.setSkip(page);
        }
        query.findObjects(new FindListener<DeviceBean>() {
            @Override
            public void done(List<DeviceBean> list, BmobException e) {
                if(list.size() > 0){
                    if(actionType == STATE_REFRESH){
                        curPage = 0;
                        lastTime = list.get(list.size()-1).getCreatedAt();
                        deviceBeanList.clear();
                    }
                    deviceBeanList.addAll(list);
                    deviceAdapter.notifyDataSetChanged();
                }else if (actionType == STATE_MORE){
                    Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(),"没有数据",Toast.LENGTH_SHORT).show();
                deviceAdapter.notifyDataSetChanged();
                if(actionType == STATE_REFRESH)
                    swipeRefreshLayout.finishRefreshing();
                    swipeRefreshLayout.finishLoadmore();
            }
        });
    }

  private   class  UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            getData(0,0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(updateReceiver);
    }
}
