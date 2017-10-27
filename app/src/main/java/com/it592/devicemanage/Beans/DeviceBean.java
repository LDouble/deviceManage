package com.it592.devicemanage.Beans;

import java.io.Serializable;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by doublel on 17-10-7.
 */

public class DeviceBean extends BmobObject implements Serializable {
    private boolean status; //是否借出
    private UserBean last_lend;
    private String device_name;
    private BmobDate buy_time;
    private UserBean add_user;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public UserBean getLast_lend() {
        return last_lend;
    }

    public void setLast_lend(UserBean last_lend) {
        this.last_lend = last_lend;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public BmobDate getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(BmobDate buy_time) {
        this.buy_time = buy_time;
    }

    public UserBean getAdd_user() {
        return add_user;
    }

    public void setAdd_user(UserBean add_user) {
        this.add_user = add_user;
    }
}
