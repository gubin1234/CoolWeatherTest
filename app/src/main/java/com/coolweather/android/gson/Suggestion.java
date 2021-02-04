package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @ 作者: GB
 * @ 类名: Suggestion
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 上午 11:55
 **/
public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;
    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
