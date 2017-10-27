package com.it592.devicemanage.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it592.devicemanage.Adapter.BorrowAdapter;
import com.it592.devicemanage.Adapter.DeviceAdapter;
import com.it592.devicemanage.Beans.DeviceBean;
import com.it592.devicemanage.Beans.LendBean;
import com.it592.devicemanage.Beans.UserBean;
import com.it592.devicemanage.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    BorrowAdapter deviceAdapter;
    List<LendBean> deviceBeanList;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my, container, false);
        deviceBeanList = new ArrayList<LendBean>();
        deviceAdapter = new BorrowAdapter(deviceBeanList);
        RecyclerView recyclerView = view.findViewById(R.id.borrow_lists);
        recyclerView.setAdapter(deviceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        getData();
        return  view;
    }

    private void getData(){
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        BmobQuery query = new BmobQuery<LendBean>();
        query.include("deviceBean");
        query.addWhereEqualTo("lender",userBean);
        query.findObjects(new FindListener<LendBean>() {
            @Override
            public void done(List<LendBean> list, BmobException e) {
                if(e == null){
                        deviceBeanList.addAll(list);
                        deviceAdapter.notifyDataSetChanged();
                }
            }

        });
    }

}
