package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @ 作者: GB
 * @ 类名:
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 上午 11:42
 **/
public class Basic {
    @SerializedName("city")//建立映射关系
    public String cityName;
    @SerializedName("id")//建立映射关系
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
