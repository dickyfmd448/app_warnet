package com.example.app_warnet.model;

public class ViewModelNetUser {
    String NetName, WarnetID;

    public ViewModelNetUser(){}

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
        WarnetID = warnetID;
    }

    public ViewModelNetUser(String netName, String warnetID) {
        NetName = netName;
        WarnetID = warnetID;
    }
}
