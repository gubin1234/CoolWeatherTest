package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ 作者: GB
 * @ 类名: Weather
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 下午 01:40
 **/
public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    /*未来几天天气*/
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
