package com.it592.devicemanage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.it592.devicemanage.Adapter.BorrowAdapter;
import com.it592.devicemanage.Beans.DeviceBean;
import com.it592.devicemanage.Beans.LendBean;
import com.it592.devicemanage.Beans.UserBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Deviceinfo extends AppCompatActivity {
    DeviceBean deviceBean;
    TextView deviceNameTV;
    TextView dateTV;
    TextView addressTV;
    TextView noteTV;
    TextView statusTV;
    Boolean isBorrow = false;
    Boolean isSelfBorrow = false;
    UserBean currentUser;
    RecyclerView recyclerView;
    List<LendBean> lendBeanList;
    ActionBar actionBar;
    Button borrow;
    BorrowAdapter borrowAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);
        Toolbar toolbar = (Toolbar)findViewById(R.id.action_bar_info);
        String deviceID = getIntent().getStringExtra("device");
        BmobQuery<DeviceBean> query = new BmobQuery<DeviceBean>();
        query.include("last_lend");
        query.getObject(deviceID,new QueryListener<DeviceBean>(){
            @Override
            public void done(DeviceBean d, BmobException e) {
                deviceBean = d;
                initView();
                getData();
                actionBar.setTitle(deviceBean.getDevice_name());
            }
        });
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        borrow = (Button)findViewById(R.id.borrow);
        currentUser = UserBean.getCurrentUser(UserBean.class);
        recyclerView = (RecyclerView) findViewById(R.id.borrow_lists);
        lendBeanList = new ArrayList<LendBean>();
        borrowAdapter = new BorrowAdapter(lendBeanList);
        recyclerView.setAdapter(borrowAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(Deviceinfo.this,DividerItemDecoration.VERTICAL));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

        }
        return  true;
    }

    private void getData(){
        BmobQuery<LendBean> query = new BmobQuery<LendBean>();
        query.include("deviceBean");
        query.include("lender");
        query.addWhereEqualTo("deviceBean",deviceBean);
        query.findObjects(new FindListener<LendBean>() {
            @Override
            public void done(List<LendBean> list, BmobException e) {
                if(e == null){
                    lendBeanList.addAll(list);
                    borrowAdapter.notifyDataSetChanged();
                }else
                    Toast.makeText(Deviceinfo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void borrowDevice(){
        LendBean lendBean = new LendBean();
        lendBean.setDeviceBean(deviceBean);
        lendBean.setLender(UserBean.getCurrentUser(UserBean.class));
        lendBean.setLend_date(new BmobDate(new Date()));
        lendBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                statusTV.setText("设备状态: 您已借出该设备");
                Toast.makeText(Deviceinfo.this,"设备借出成功",Toast.LENGTH_SHORT).show();
                borrow.setText("归还");
                changeDeviceInfo(UserBean.getCurrentUser(UserBean.class));
                Intent intent = new Intent();
                intent.setAction("com.it592.deviceManage.update_device");
                sendBroadcast(intent);
            }
        });
    }

    private void changeDeviceInfo(UserBean curUser){
        deviceBean.setLast_lend(curUser);
        deviceBean.update(deviceBean.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
    }
    private  void initView(){
        if(deviceBean.getLast_lend() != null){
            isBorrow = true;
            if(deviceBean.getLast_lend().getObjectId().equals(currentUser.getObjectId())){
                Toast.makeText(Deviceinfo.this,"ss",Toast.LENGTH_SHORT).show();
                isSelfBorrow = true;
                borrow.setText("归还");
            }
        }
        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelfBorrow)
                    returnDevice();
                else if(isBorrow)
                    Toast.makeText(Deviceinfo.this,"该设备已被借出",Toast.LENGTH_SHORT).show();
                else
                    borrowDevice();
            }
        });
        deviceNameTV = (TextView)findViewById(R.id.device_name);
        dateTV = (TextView)findViewById(R.id.device_date);
        addressTV = (TextView)findViewById(R.id.device_address);
        noteTV = (TextView)findViewById(R.id.device_note);
        statusTV = (TextView)findViewById(R.id.device_status);
        deviceNameTV.setText("设备名称: " +deviceBean.getDevice_name());
        dateTV.setText("购置时间: " + deviceBean.getBuy_time().getDate());
        addressTV.setText("放置地点: " + deviceBean.getAddress());
        noteTV.setText("设备说明: " + deviceBean.getNote());
        if(deviceBean.getLast_lend() != null)
            statusTV.setText("设备状态: 已被" + deviceBean.getLast_lend().getUsername() + "于"+deviceBean.getUpdatedAt() + "借走");
        else
            statusTV.setText("设备状态: 在库");
    }

    private  void returnDevice(){
            deviceBean.remove("last_lend");
            deviceBean.update(deviceBean.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {
                        if(e != null)
                        Toast.makeText(Deviceinfo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        else{
                            Toast.makeText(Deviceinfo.this,"设备归还成功",Toast.LENGTH_SHORT).show();
                            statusTV.setText("设备状态: 在库");
                            borrow.setText("借出");
                            Intent intent = new Intent();
                            intent.setAction("com.it592.deviceManage.update_device");
                            sendBroadcast(intent);
                        }
                }
            });
    }
}
