package com.it592.devicemanage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.it592.devicemanage.Beans.DeviceBean;
import com.it592.devicemanage.Beans.UserBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddDeviceActivity extends AppCompatActivity {
    TextView buy_date;
    EditText deviceNameET;
    EditText deviceAddressET;
    EditText deviceNoteET;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = (Toolbar)findViewById(R.id.action_bar_add);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("设备添加");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button saveButton = (Button)findViewById(R.id.save_device);
        buy_date = (TextView)findViewById(R.id.add_device_date);
        deviceNameET = (EditText)findViewById(R.id.add_device_name);
        deviceAddressET = (EditText)findViewById(R.id.add_device_address);
        deviceNoteET = (EditText)findViewById(R.id.add_device_note);
        buy_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int pyear, int pmonth, int pday) {
                        year = pyear;
                        month = pmonth;
                        day = pday;
                        buy_date.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(AddDeviceActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return  true;
    }

    private void getDate(){
        Calendar cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);    ;
    }

    private  void save(){
        String name =  deviceNameET.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String address = deviceAddressET.getText().toString();
        String note = deviceNoteET.getText().toString();
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setBuy_time(BmobDate.createBmobDate("yyyy-MM-dd", String.format("%d-%d-%d", year,month,day)));
        deviceBean.setDevice_name(name);
        deviceBean.setAddress(address);
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        deviceBean.setAdd_user(userBean);
        deviceBean.setNote(note);
        deviceBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e != null)
                    Toast.makeText(AddDeviceActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddDeviceActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });
        

    }
}
