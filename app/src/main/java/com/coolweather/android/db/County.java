package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * @ 作者: GB
 * @ 类名: County
 * @ 包名: com.coolweather.android.db
 * @ 描述:
 * @ 日期: 2021/2/4 0004 下午 02:39
 **/
public class County extends LitePalSupport {
    private int id;
    private String countyName;//县的名字
    private String weatherId;//对应的天气id
    private int cityId;//当前县所属市的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
