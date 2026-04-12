package com.dondonqiang2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dondonqiang2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity {

    private ListView lvRank;
    private List<Map<String,String>> list;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();//初始化，要自定义

        lvRank = findViewById(R.id.list_rank_list);
        //构建请求参数
        RequestParams params=
                new RequestParams();
        //异步请求的客户端
        AsyncHttpClient client=
                new AsyncHttpClient();
        client.setTimeout(50000);
        //发送请求
        client.post(ListActivity.this,
                "http://192.168.43.147:8080/DonDonQiang20_Server/ListViewServlet",
                params,new BaseJsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                        System.out.println("success");
                        System.out.println("ddd:"+rawJsonResponse);
                        JSONArray arrs = JSONArray.parseArray(rawJsonResponse);
                        //必须清空容器
                        list.clear();
                        for(int i = 0;i<arrs.size();i++){
                            JSONObject obj = arrs.getJSONObject(i);
                            Map<String,String> map =
                                    new HashMap<>();
                            map.put("userRank",(int)Float.parseFloat(obj.getString("user_rank"))+"");

                            map.put("userName",obj.getString("user_name"));
                            map.put("userMaxscore",obj.getString("user_maxscore"));
                            list.add(map);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
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
    }


    //btnSearch end



    private void init() {
        lvRank = findViewById(R.id.list_rank_list);
        list = new ArrayList<>();//构建空间
        adapter = new SimpleAdapter(ListActivity.this,list,R.layout.activity_list_item,
                new String[]{"userRank","userName","userMaxscore"},
                new int[]{R.id.list_item_rank_number,R.id.list_item_rank_name,R.id.list_item_rank_score});
        lvRank.setAdapter(adapter);
    }

}