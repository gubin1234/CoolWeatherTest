package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @ 作者: GB
 * @ 类名: Forecast
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 下午 12:00
 **/
public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
