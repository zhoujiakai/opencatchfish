package com.dondonqiang2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterDialog extends AppCompatActivity {

    //定义变量 -----------------------------------------------------------------------s
    EditText etUserName;
    EditText etPassword;
//    TextView tvRegister;
//    TextView tvBack;
    String userName;
    String password;
    //定义变量 -----------------------------------------------------------------------d


    //onCreate() --------------------------------------------------------------------s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);

        //给变量赋值 -------------------------------s
        etUserName = findViewById(R.id.register_userName);
        etPassword = findViewById(R.id.register_password);
//        tvRegister = findViewById(R.id.register_register);
//        tvBack = findViewById(R.id.register_back);
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        //给变量赋值 -------------------------------s
        init();//初始化
        setListener();//监听函数

    }


    //onCreate() --------------------------------------------------------------------d


    //setListener() -----------------------------------------------------------------s
    private void setListener() {}
    //setListener() -----------------------------------------------------------------d



    private void init() {}


}