package com.dondonqiang2.mysqlite;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private int userRank;
    private int userMaxscore;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    public int getUserMaxscore() {
        return userMaxscore;
    }

    public void setUserMaxscore(int maxscore) {
        this.userMaxscore = maxscore;
    }
}
