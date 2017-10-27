package com.it592.devicemanage.Beans;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by doublel on 17-10-8.
 */

public class LendBean extends BmobObject {
    private  UserBean lender;
    private DeviceBean deviceBean;
    private BmobDate lend_date;
    private BmobDate return_date;

    public UserBean getLender() {
        return lender;
    }

    public void setLender(UserBean lender) {
        this.lender = lender;
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public BmobDate getLend_date() {
        return lend_date;
    }

    public void setLend_date(BmobDate lend_date) {
        this.lend_date = lend_date;
    }

    public BmobDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(BmobDate return_date) {
        this.return_date = return_date;
    }
}
