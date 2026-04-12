package com.dondonqiang2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ResultActivity extends AppCompatActivity {
    TextView tvFishCount;
    TextView tvScore;
    TextView tvMaxScore;
    TextView tvStartAgain;
    TextView tvQuitGame;
    TextView tvWorldRank;
    TextView tvRankNumber;
    ImageView topImg;
    String userName;
    int fishCount;
    int score;
    int maxScore;
    int TARGET;
    int rankNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();
        setListener();
        showRankNumber();
        setTopImg();

    }

    private void setTopImg() {
        if(score<TARGET){
            topImg.setBackground(getDrawable(R.drawable.resultfail));
        }else{
            topImg.setBackground(getDrawable(R.drawable.resultwin));
        }
    }

    private void init() {
        tvFishCount = findViewById(R.id.result_fishCount);
        tvScore = findViewById(R.id.result_score);
        tvMaxScore = findViewById(R.id.result_maxScore);
        tvStartAgain = findViewById(R.id.result_startAgain);
        tvQuitGame = findViewById(R.id.result_quitGame);
        tvWorldRank = findViewById(R.id.result_worldRank);
        tvRankNumber = findViewById(R.id.result_rankNumber);
        topImg = findViewById(R.id.result_topImg);
        //接受数据
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("user_name");
        fishCount = bundle.getInt("fishCount");
        score = bundle.getInt("score");
        TARGET = bundle.getInt("TARGET");
        tvScore.setText(score+"");
        tvFishCount.setText(fishCount+"");
        tvMaxScore.setText(maxScore+"");
    }

    private void showRankNumber() {
        //构建请求参数
        RequestParams params=
                new RequestParams();
        params.put("user_Name",userName);
        params.put("user_Score",score);
        //Toast.makeText(ResultActivity.this,userName, Toast.LENGTH_SHORT).show();
        //异步请求的客户端
        AsyncHttpClient client=
                new AsyncHttpClient();
        client.setTimeout(50000);
        //发送请求
        client.post(ResultActivity.this,
                "http://192.168.43.147:8080/DonDonQiang20_Server/RankServlet",
                params,new BaseJsonHttpResponseHandler(){
                    //接收请求
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                        System.out.println("success");
                        System.out.println("ddd:"+rawJsonResponse);
                        JSONArray arrs = JSONArray.parseArray(rawJsonResponse);
                        //处理数据

                        JSONObject obj = arrs.getJSONObject(0);
                        //Iterator<String> iter = map.keySet().iterator();
                        //String key = iter.next();
                        tvMaxScore.setText(obj.getString("user_maxscore"));
                        tvRankNumber.setText(obj.getString("user_rank"));
                        //Toast.makeText(ResultActivity.this, "fg"+tmp, Toast.LENGTH_SHORT).show();
                        // int tmp = Integer.parseInt(obj.getString("user_rank").toString());
                        tvRankNumber.setText(""+ (int) Float.parseFloat(obj.getString("user_rank")));
                        //Toast.makeText(ResultActivity.this, obj.getString("user_rank"), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ResultActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();



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
    }

    private void setListener() {
        tvStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_name",userName);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        tvQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvWorldRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ListActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("user_name",userName);
//                intent.putExtras(bundle);
                startActivity(intent);
                //finish();
            }
        });
    }

}