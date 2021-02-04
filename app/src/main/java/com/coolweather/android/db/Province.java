package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * @ 作者: GB
 * @ 类名: Province
 * @ 包名: com.coolweather.android.db
 * @ 描述:
 * @ 日期: 2021/2/4 0004 下午 02:28
 **/
public class Province extends LitePalSupport {
    private int id;
    private String provinceName;//省的名字
    private int provinceCode;//省代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
