package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * @ 作者: GB
 * @ 类名: City
 * @ 包名: com.coolweather.android.db
 * @ 描述:
 * @ 日期: 2021/2/4 0004 下午 02:34
 **/
public class City extends LitePalSupport {
    private int id;
    private String cityName;//市的名字
    private int cityCode;//市代号
    private int provinceId;//当前市所属省的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
