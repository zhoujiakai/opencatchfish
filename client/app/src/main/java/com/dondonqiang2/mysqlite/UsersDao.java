package com.dondonqiang2.mysqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DonDonQiang on 2021/7/21.
 */

public class UsersDao {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public UsersDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int save(String name,String password){
        int row=0;
        String  sql="insert into users(user_name,user_password,user_maxsocore)" +
                "values(?,?,0)";
        try {
            db=dbHelper.getWritableDatabase();
            db.execSQL(sql,new String[]{name,password,"0"});
            row=1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
        return row;
    }

    public List<Map<String,String>> search(){
        List<Map<String,String>> list=
                new ArrayList<>();
        String sql="select * from users";
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            Map<String,String> map=new HashMap<>();
            map.put("user_id",
                    cursor.getString(cursor.getColumnIndex("user_id")));
            map.put("user_name",
                    cursor.getString(cursor.getColumnIndex("user_name")));
            map.put("user_password",
                    cursor.getString(cursor.getColumnIndex("user_password")));
            list.add(map);
        }
        return list;
    }
}
