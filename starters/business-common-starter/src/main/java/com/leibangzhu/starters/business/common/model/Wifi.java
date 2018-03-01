package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.Dto;

public class Wifi implements Dto {

    private String mac;
    private int rssi;       // signal
    private String ssid;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
