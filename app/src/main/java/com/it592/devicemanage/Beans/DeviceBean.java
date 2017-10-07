package com.it592.devicemanage.Beans;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by doublel on 17-10-7.
 */

public class DeviceBean extends BmobObject {
    private boolean status; //是否借出
    private String last_lend;
    private String device_name;
    private Date buy_time;
    private String add_user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLast_lend() {
        return last_lend;
    }

    public void setLast_lend(String last_lend) {
        this.last_lend = last_lend;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Date getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(Date buy_time) {
        this.buy_time = buy_time;
    }

    public String getAdd_user() {
        return add_user;
    }

    public void setAdd_user(String add_user) {
        this.add_user = add_user;
    }
}
