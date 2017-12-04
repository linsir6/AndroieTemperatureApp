package com.qf58.temperature;

import java.util.ArrayList;

/**
 * Created by linSir
 * date at 2017/12/3.
 * describe:
 */

public class User {

    private String phone;
    private String pwd;
    private ArrayList<Float> tem;

    public User(String phone, String pwd, ArrayList<Float> tem) {
        this.phone = phone;
        this.pwd = pwd;
        this.tem = tem;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ArrayList<Float> getTem() {
        return tem;
    }

    public void setTem(ArrayList<Float> tem) {
        this.tem = tem;
    }
}
