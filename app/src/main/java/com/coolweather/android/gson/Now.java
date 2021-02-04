package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @ 作者: GB
 * @ 类名: Now
 * @ 包名: com.coolweather.android.gson
 * @ 描述:
 * @ 日期: 2021/2/5 0005 上午 11:52
 **/
public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
