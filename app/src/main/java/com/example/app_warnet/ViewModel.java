package com.example.app_warnet;

public class ViewModel {
    String UserID, Name, Email, Password;

    public ViewModel(){}

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ViewModel(String userID, String name, String email, String password) {
        UserID = userID;
        Name = name;
        Email = email;
        Password = password;
    }
}
