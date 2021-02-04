package com.coolweather.android.gson;

/**
 * @ 作者: GB
 * @ 类名: AQI
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 上午 11:50
 **/
public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
