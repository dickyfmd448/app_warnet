package com.example.app_warnet;

public class ViewModelWarnet {

    String NetName, WarnetID;

    public ViewModelWarnet(){}

    public String getNetName() {
        return NetName;
    }

    public void setNetName(String netName) {
        NetName = netName;
    }

    public String getWarnetID() {
        return WarnetID;
    }

    public void setWarnetID(String warnetID) {
        this.WarnetID = warnetID;
    }

    public ViewModelWarnet(String netName, String warnetID) {
        NetName = netName;
        this.WarnetID = warnetID;
    }
}
