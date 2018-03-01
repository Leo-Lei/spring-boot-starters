package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.Dto;

import java.util.Date;
import java.util.List;

public class Heartbeat implements Dto {

    private String communicateId;
    private LockEvent event;
    private String bikeId;
    private LockStatus lockStatus;
    private Double longitude;
    private Double latitude;
    private Integer battery;
    private Integer voltage;
    private Integer chargingVoltage;
    private Boolean isCharging;
    private List<CellTower> cellTowers;
    private List<Wifi> wifis;
    private Date timestamp;

    private String hardVersion;
    private String softVersion;
    private String imsi;

    private String bluetoothMac;            // 蓝牙的mac地址
    private String bluetoothPassword;       // 蓝牙的密码
    private String bluetoothsecret;         // 蓝牙的secret

    public String getCommunicateId() {
        return communicateId;
    }

    public void setCommunicateId(String communicateId) {
        this.communicateId = communicateId;
    }

    public LockEvent getEvent() {
        return event;
    }

    public void setEvent(LockEvent event) {
        this.event = event;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public LockStatus getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(LockStatus lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Integer getChargingVoltage() {
        return chargingVoltage;
    }

    public void setChargingVoltage(Integer chargingVoltage) {
        this.chargingVoltage = chargingVoltage;
    }

    public List<CellTower> getCellTowers() {
        return cellTowers;
    }

    public void setCellTowers(List<CellTower> cellTowers) {
        this.cellTowers = cellTowers;
    }

    public List<Wifi> getWifis() {
        return wifis;
    }

    public void setWifis(List<Wifi> wifis) {
        this.wifis = wifis;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getHardVersion() {
        return hardVersion;
    }

    public void setHardVersion(String hardVersion) {
        this.hardVersion = hardVersion;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getBluetoothPassword() {
        return bluetoothPassword;
    }

    public void setBluetoothPassword(String bluetoothPassword) {
        this.bluetoothPassword = bluetoothPassword;
    }

    public String getBluetoothsecret() {
        return bluetoothsecret;
    }

    public void setBluetoothsecret(String bluetoothsecret) {
        this.bluetoothsecret = bluetoothsecret;
    }

    public Boolean getCharging() {
        return isCharging;
    }

    public void setCharging(Boolean charging) {
        isCharging = charging;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("Heartbeat{");
        sb.append("communicateId=").append(communicateId);
        sb.append(", event=").append(event);
        sb.append(", bikeId=").append(bikeId);
        sb.append(", lockStatus=").append(lockStatus);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", battery=").append(battery);
        sb.append(", voltage=").append(voltage);
        sb.append(", chargingVoltage=").append(chargingVoltage);
        sb.append(", isCharging=").append(isCharging);
        sb.append(", cellTowers=").append(cellTowers);
        sb.append(", wifis=").append(wifis);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", hardVersion=").append(hardVersion);
        sb.append(", softVersion=").append(softVersion);
        sb.append(", imsi=").append(imsi);
        sb.append(", bluetoothMac=").append(bluetoothMac);
        sb.append(", bluetoothPassword=").append(bluetoothPassword);
        sb.append(", bluetoothsecret=").append(bluetoothsecret);

        sb.append('}');
        return sb.toString();
    }

}
