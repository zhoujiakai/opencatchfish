package com.dondonqiang2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    //定义变量--------------------------------------------------------------------------s
    EditText etUserName;
    EditText etPassword;
    TextView tvLogin;
    TextView tvRegister;
    //注册弹框控件
    final Context context = this;
    String userName;
    String password;
    //定义变量--------------------------------------------------------------------------d


    //onCreate()-----------------------------------------------------------------------s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //变量赋值---------------------------------------s
        etUserName = findViewById(R.id.login_userName);
        etPassword = findViewById(R.id.login_password);
        tvLogin = findViewById(R.id.login_login);
        tvRegister = findViewById(R.id.login_register);
        //变量赋值---------------------------------------d

        setListener();//监听按钮函数
        init();//初始化，自定义
    }
    //onCreate() ----------------------------------------------------------------------d


    //setListener() -------------------------------------------------------------------s
    private void setListener() {

        //tvLogin 登录按钮---------------------------------------s
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();
                password = etPassword.getText().toString();
                if(userName==null||userName.length()<=0){
                    Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //向servlet传递数据，检查用户名密码是否匹配 --------s
                //构建请求参数
                RequestParams params=
                        new RequestParams();
                params.put("user_Name",userName);
                //异步请求的客户端
                AsyncHttpClient client=
                        new AsyncHttpClient();
                //Toast.makeText(LoginActivity.this, "已设置参数", Toast.LENGTH_SHORT).show();
                client.setTimeout(50000);
                //发送请求
                client.post(LoginActivity.this,
                        "http://192.168.43.147:8080/DonDonQiang20_Server/LoginServlet",
                        params,new BaseJsonHttpResponseHandler(){
                //接收请求
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    //Toast.makeText(LoginActivity.this, "已连接Web", Toast.LENGTH_SHORT).show();
                    System.out.println("success");
                    System.out.println("ddd:"+rawJsonResponse);
                    JSONArray arrs = JSONArray.parseArray(rawJsonResponse);
                    //处理数据

                    JSONObject obj = arrs.getJSONObject(0);
                    Map<String,String> map =
                            new HashMap<>();
                    map.put("userPassword",obj.getString("user_password"));
                    Iterator<String> iter = map.keySet().iterator();
                    if(iter.hasNext()){
                        String key = iter.next();
                        String value = map.get(key);
                        if (password.equals(value)) {
                            Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                            //传输数据
                            Bundle bundle = new Bundle();
                            bundle.putString("user_name", userName);
                            intent.putExtras(bundle);
                            bundle.putString("password", password);
                            intent.putExtras(bundle);
                            //Toast.makeText(LoginActivity.this, userName, Toast.LENGTH_SHORT).show();
                            //跳转到游戏界面
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                    }


                }

                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

                                System.out.println("failure1");
                                System.out.println(throwable.getMessage());
                                System.out.println(rawJsonData);
                            }

                            @Override
                            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return null;
                            }
                        });
                //向servlet传递数据，检查用户名密码是否匹配 --------s


            }
        });
        //tvLogin 登录按钮---------------------------------------d

        // components from activity_login.xml
        tvRegister = findViewById(R.id.login_register);
        etUserName = findViewById(R.id.login_userName);

        // add tvRegister listener 注册按钮 ---------------------s
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get register_dialog.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View registerView = li.inflate(R.layout.dialog_register, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set dialog_register.xml to alertDialog builder
                alertDialogBuilder.setView(registerView);

                final EditText r_etUserName = registerView
                        .findViewById(R.id.register_userName);
                userName = r_etUserName.getText().toString();
                final EditText r_etPassword = registerView
                        .findViewById(R.id.register_password);
                password = r_etPassword.getText().toString();
                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("确认注册",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //etUserName.setText(r_etUserName.getText());
                                        //向servlet传递数据，检查用户名是否重复，若重复则保存成功 -------------------------------------------------------s
                                        //构建请求参数
                                        userName = r_etUserName.getText().toString();
                                        password = r_etPassword.getText().toString();
                                        RequestParams params=
                                                new RequestParams();
                                        params.put("user_Name",userName);
                                        params.put("user_Password",password);
                                        //Toast.makeText(LoginActivity.this, userName, Toast.LENGTH_SHORT).show();
                                        //异步请求的客户端
                                        AsyncHttpClient client=
                                                new AsyncHttpClient();
                                        client.setTimeout(50000);
                                        //Toast.makeText(LoginActivity.this, "用错误！", Toast.LENGTH_SHORT).show();


                                        //发送请求
                                        //Toast.makeText(LoginActivity.this, "户错误！", Toast.LENGTH_SHORT).show();//-------------
                                        client.post(LoginActivity.this,
                                                "http://192.168.43.147:8080/DonDonQiang20_Server/RegisterServlet",
                                                params,new BaseJsonHttpResponseHandler(){
                                                    //接收请求
                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                                                        System.out.println("success");
                                                        System.out.println("ddd:"+rawJsonResponse);
                                                        JSONArray arrs = JSONArray.parseArray(rawJsonResponse);
                                                        //处理数据

                                                        JSONObject obj = arrs.getJSONObject(0);
                                                        Map<String,String> map =
                                                                new HashMap<>();
                                                        map.put("myResult",obj.getString("ifAdded"));
                                                        Iterator<String> iter = map.keySet().iterator();
                                                            String key = iter.next();
                                                            String value = map.get(key);
                                                            //if(!iter.hasNext())
                                                            if(value.equals("yes")){
                                                                Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                            }else if(value.equals("existed")){
                                                                Toast.makeText(LoginActivity.this, "用户已存在！", Toast.LENGTH_SHORT).show();
                                                            }else if(value.equals("isNull")){
                                                                Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                                                            }




                                                    }

                                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

                                                        System.out.println("failure1");
                                                        System.out.println(throwable.getMessage());
                                                        System.out.println(rawJsonData);
                                                    }

                                                    @Override
                                                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                                        return null;
                                                    }
                                                });
                                        //向servlet传递数据，检查用户名是否重复，若重复则保存成功 ------------------------------------------------------d







                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

        });
        // add tvRegister listener 注册按钮 ---------------------d
    }
    //setListener() -------------------------------------------------------------------d


    private void init() {}

}