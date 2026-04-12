package com.dondonqiang2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {


    //定义变量 ------------------------------------------------------------------------s
    //控件
    TextView tvTarget;
    TextView tvScore;
    TextView tvFishCount;
    TextView tvLeftTime;
    ImageView ivLifeCount;
    ImageView ivCoughtFish;
    ImageView ivRestart;
    ImageView ivHelp;
    ImageView ivSettings;
    ImageView ivBack;
    LinearLayout llFishPool;
    ImageView[] ivMovingFish;
    //计时器
    Timer timerForMoving;
    Timer timerForLeftTime;
    //fishMap
    Map<Integer, Integer> goodFishMap = new HashMap<>();
    Map<Integer, Integer> meanFishMap = new HashMap<>();
    Map<Integer,Integer> lifeMap = new HashMap<Integer,Integer>();
    //其他变量
    String userName;
    int leftTime;
    int lifeCount;
    int fishCount;
    int score=8888;
    int speed = 1200;
    int MAXLIFECOUNT = 3;
    private final int MAXFISHNUMBER = 6;
    private final int MAXTIME = 60;
    private final int MOVINGFISH = 1;
    private final int LOSINGTIME = 2;
    private final int TARGET = 2000;
    //注册弹框控件
    final Context context = this;
    //定义变量 -----------------------------------------------------------------------d
    //Handler -----------------------------------------------------------------------s
    Handler myHandler =new Handler() {
        public void handleMessage (Message msg){
            switch (msg.what) {
                case MOVINGFISH:
                    for (int k = 0; k <= MAXFISHNUMBER-1; k++) {
                        //这里不需要findId，因为创建的时候已经确定哪个按钮对应哪个Id
                        ivMovingFish[k].setTranslationX((int) (Math.random() * 300-250));   //横坐标范围：0~1300
                        ivMovingFish[k].setTranslationY((int) (Math.random() * 900+200));  //纵坐标范围：0~1800
                    }
                    break;
                case LOSINGTIME:
                    tvLeftTime.setText(--leftTime+"");
                    if(leftTime<=0||lifeCount<=0){
                        timerForLeftTime.cancel();
                        timerForMoving.cancel();
                        //Toast.makeText(GameActivity.this,"游戏结束",Toast.LENGTH_SHORT).show();
                        //传输数据，将用户名传给ResultDialog
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("user_name",userName);
                        bundle.putInt("fishCount", fishCount);
                        bundle.putInt("score" , score);
                        bundle.putInt("TARGET", TARGET);
                        intent.putExtras(bundle);
                        //设置小鱼不可点击,设置鱼塘不可点击
                        for (int k = 0; k <= ivMovingFish.length-1; k++) {
                            ivMovingFish[k].setEnabled(false);
                        }
                        llFishPool.setEnabled(false);
                        //倒计时结束时跳到成功界面
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }
    };
    //Handler ------------------------------------------------------------------------d
    //onCreate() ---------------------------------------------------------------------s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //主要函数 ------------------------------------------s
        //初始化，给变量赋值
        init();
        //创建小鱼
        createFish();
        //监听事件
        setListener();
        //主要函数 ------------------------------------------d
    }
    //onCreate() ---------------------------------------------------------------------d


    //fishOut() ----------------------------------------------------------------------s
    private void createFish() {
        for  (int i=0; i<=MAXFISHNUMBER-1; i++) {
            ivMovingFish[i]= new ImageView(this);
            ivMovingFish[i].setId(2000+i);
            ivMovingFish[i].setEnabled(false);
            if(i<=MAXFISHNUMBER-3){
                ivMovingFish[i].setBackground(getDrawable(goodFishMap.get(i+1)));
            }else{
                ivMovingFish[i].setBackground(getDrawable(meanFishMap.get(i-3)));
            }

            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(180,130);  //设置小鱼的宽度和高度
            ivParams.leftMargin = 0;   //横坐标定位
            ivParams.topMargin = -200;   //纵坐标定位
            llFishPool.addView(ivMovingFish[i],ivParams);   //将按钮放入layout组件
        }

    }
    //fishOut() ----------------------------------------------------------------------d





    //setListener() ------------------------------------------------------------------s
    private void setListener() {

        //菜单栏开始按钮 ivRestart按钮 --------------------------------s
        ivRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                leftTime = MAXTIME;
                lifeCount = MAXLIFECOUNT;
                fishCount = 0;
                llFishPool.setEnabled(true);
                for (int k = 0; k <= MAXFISHNUMBER-1; k++) {
                    //这里不需要findId，因为创建的时候已经确定哪个按钮对应哪个Id
                    ivMovingFish[k].setEnabled(true);
                }
                tvTarget.setText(TARGET+"");
                tvScore.setText(score+"");
                tvFishCount.setText(fishCount+"");
                tvLeftTime.setText(leftTime+"");

                //先停止掉之前的计时器
                timerForMoving.cancel();
                timerForLeftTime.cancel();
                timerForMoving = new Timer();
                timerForLeftTime = new Timer();
                //小鱼开始移动------------------------s
                timerForMoving.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = MOVINGFISH;
                        myHandler.sendMessage(msg);
                    }
                }, 1000, speed);
                //小鱼开始移动 -----------------------d

                //计时器开始计时 ---------------------s

                timerForLeftTime.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = LOSINGTIME;
                        myHandler.sendMessage(msg);
                    }
                }, 0, 1000);
                //计时器开始计时 ---------------------d
            }
        });
        //菜单栏开始按钮 ivRestart按钮 --------------------------------d


        //菜单栏帮助按钮 ivHelp按钮 -----------------------------------s
        ivHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(GameActivity.this,
                        ""+"在倒计时结束之前抓到尽可能多的鱼，达到目标分数就胜利了。" +
                                "注意不要碰到鲨鱼和有毒的海蜇或者落空，以免损失生命值！",
                        Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.bubble);
                toast.setView(view);
                toast.show();
            }
        });
        //菜单栏帮助按钮 ivHelp按钮 -----------------------------------d


        //菜单栏设置按钮 ivSettings按钮 -------------------------------s
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get register_dialog.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View settingsView = li.inflate(R.layout.dialog_settings, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set dialog_register.xml to alertDialog builder
                alertDialogBuilder.setView(settingsView);

                final EditText s_etSpeed = settingsView
                        .findViewById(R.id.settings_speed);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to result
                                        // edit text
                                        if(s_etSpeed.getText().toString()!=null&&s_etSpeed.getText().toString().length()>0){
                                            speed-=Integer.parseInt(s_etSpeed.getText().toString())*100+100;
                                            Toast.makeText(GameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });
        //菜单栏设置按钮 ivSettings按钮 -------------------------------d


        //菜单栏放回按钮 ivBack按钮 -----------------------------------s
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GameActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //菜单栏放回按钮 ivBack按钮 -----------------------------------d

        //点击移动的小鱼 ivMovingFish按钮 -----------------------------s
        for(int i=0;i<MAXFISHNUMBER-1;i++){
            ivMovingFish[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ismean = 0 ;
//                    for(int i = 1;i<=2;i++){
//                        if(v.getBackground().equals(getDrawable(meanFishMap.get(i)))){
//                            ismean = 1;
//                        }
//                    }
                    if(v.getBackground().equals(getDrawable(meanFishMap.get(1)))||v.getBackground().equals(getDrawable(meanFishMap.get(2)))){
                        ismean = 1;
                    }
                    if(ismean == 0){
                        fishCount++;//鱼计数加1
                        tvFishCount.setText(fishCount+"");
                        score+=100;//分数加100
                        tvScore.setText(score+"");
                        ivCoughtFish.setBackground(v.getBackground());
                    }else{
                        //必须先判断，否则可能会有空指针
                        if(lifeCount>0){
                            ivLifeCount.setBackground(getDrawable(lifeMap.get(--lifeCount)));
                        }
                    }

                }
            });
        }

        //点击移动的小鱼 ivMovingFish按钮 -----------------------------d

        //落空点击鱼塘 llFishPool
        llFishPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //必须先判断，否则可能会有空指针
                if(lifeCount>0){
                    ivLifeCount.setBackground(getDrawable(lifeMap.get(--lifeCount)));
                }
            }
        });




    }
    //setListener() ------------------------------------------------------------------d


    //init() -------------------------------------------------------------------------s
    private void init() {
        //给变量赋值 -----------------------------------------s
        //控件
        tvTarget = findViewById(R.id.game_target);
        tvScore = findViewById(R.id.game_score);
        tvFishCount = findViewById(R.id.game_fishCount);
        tvLeftTime = findViewById(R.id.game_leftTime);
        ivLifeCount = findViewById(R.id.game_lifeCount);
        ivCoughtFish = findViewById(R.id.game_coughtFish);
        ivRestart = findViewById(R.id.game_restart);
        ivHelp = findViewById(R.id.game_help);
        ivSettings = findViewById(R.id.game_settings);
        ivBack = findViewById(R.id.game_back);
        llFishPool = findViewById(R.id.game_fishPool);
        //计时器
        timerForMoving = new Timer();
        timerForLeftTime = new Timer();
        //fishMap
        goodFishMap.put(1,R.drawable.fish1);
        goodFishMap.put(2,R.drawable.fish2);
        goodFishMap.put(3,R.drawable.fish3);
        goodFishMap.put(4,R.drawable.fish4);
        meanFishMap.put(1,R.drawable.meanfish1);
        meanFishMap.put(2,R.drawable.meanfish2);
        //lifeMap
        lifeMap.put(0,R.drawable.lifestar0);
        lifeMap.put(1,R.drawable.lifestar1);
        lifeMap.put(2,R.drawable.lifestar2);
        lifeMap.put(3,R.drawable.lifestar3);
        //其他变量
        leftTime = MAXTIME;
        lifeCount = 3;
        fishCount = 0;
        ivMovingFish = new ImageView[MAXFISHNUMBER];
        //接受数据：用户名
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("user_name");
        //Toast.makeText(GameActivity.this,userName, Toast.LENGTH_SHORT).show();
        //给变量赋值 ----------------------------------------d
        //设置鱼塘不可点击
        llFishPool.setEnabled(false);
    }
    //init() -------------------------------------------------------------------------d

}