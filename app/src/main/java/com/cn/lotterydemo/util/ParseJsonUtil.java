package com.cn.lotterydemo.util;

import android.util.Log;

import com.cn.lotterydemo.bean.SportBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/1/23.
 */

public class ParseJsonUtil {
    public static ArrayList<String> parseJson(String json) {
        ArrayList list=new ArrayList();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray heWeather6 = object.optJSONArray("HeWeather6");
            JSONObject object1 = heWeather6.optJSONObject(0);
            JSONObject object2 = object1.optJSONObject("now");
            String cond_txt = object2.getString("cond_txt");
            list.add(cond_txt);
            Log.d("lee", cond_txt);
            String tmp = object2.getString("tmp");
            list.add(tmp);
            // JSONObject object2 = object1.optJSONObject("now");
           /* String cond_txt = object2.getString("cond_txt");
            Log.d("lee",cond_txt);*/

        } catch (Exception e) {
            Log.d("lee", e.toString());
        }
        return list;
    }

/*    public static List<KaiJiangInfo> ParseKaijiang(String json){
        ArrayList list=new ArrayList();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray data = object.optJSONArray("data");
            Log.d("lee",data.length()+"");
            for (int l=0;l<data.length();l++){
                JSONObject object1=data.getJSONObject(l);
                KaiJiangInfo info=new KaiJiangInfo();
                info.setKaijiangCode(object1.getString("opencode"));
                info.setKaijiangNum(object1.getString("opentimestamp"));
                info.setKaijiangDate(object1.getString("opentime"));
                list.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  list;
    }*/


    public static ArrayList<SportBean> parseSportJson(String json){
        ArrayList<SportBean> sportlist=new ArrayList();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject result = object.optJSONObject("result");
            JSONArray list = result.optJSONArray("list");
            Log.d("lee","前后有几天:"+list.length());
            //前后三天
            for (int k=0;k<list.length();k++){
                JSONObject jsonObject = list.getJSONObject(k);
                //周几
                String title = jsonObject.getString("title");
                //每天有几场比赛
                JSONArray tr = jsonObject.getJSONArray("tr");
                Log.d("lee","每天有几场比赛:"+tr.length());
                for (int j=0;j<tr.length();j++){
                    JSONObject jsonObject1 = tr.getJSONObject(j);
                    //视频type  直播。锦集
                    String videoType = jsonObject1.getString("link1text");
                    //视频地址
                    String playOff = jsonObject1.getString("link1url");
                    //队伍1
                    String player1 = jsonObject1.getString("player1");
                    //队伍1 队标
                    String player1logo = jsonObject1.getString("player1logo");
                    //队伍2
                    String player2 = jsonObject1.getString("player2");
                    //队伍2 队标
                    String player2logo = jsonObject1.getString("player2logo");

                    String score = jsonObject1.getString("score");
                    String team1Score;
                    String team2Score;
                    String status=jsonObject1.getString("status");
                    if(score.contains("-")){
                        String[] split = score.split("-");
                        //队伍1得分
                        team1Score=split[0];
                        //队伍2得分
                         team2Score=split[1];
                    }else {
                        //队伍1得分
                        team1Score="-";
                        //队伍2得分
                        team2Score="-";
                    }

                    //比赛时间
                    String time = jsonObject1.getString("time");
                    SportBean bean=new SportBean(player1,player1logo,team1Score,player2,player2logo,team2Score,time,playOff,videoType,status);
                    sportlist.add(bean);
                }

            }

        } catch (JSONException e) {
          //  e.printStackTrace();
            Log.d("lee","parse:::"+e.toString());
        }
        return sportlist;
    }
}
