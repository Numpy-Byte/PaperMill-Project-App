package cse213.papermillapp;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
    private int userId;
    private String pw;
    private String userType;

    public LoginCredentials(int userId, String pw, String userType) {
        this.userId = userId;
        this.pw = pw;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getPw() {
        return pw;
    }

    public String getUserType() {
        return userType;
    }
}
