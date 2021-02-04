package com.coolweather.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @ 作者: GB
 * @ 类名: HttpUtil
 * @ 包名: com.coolweather.android.util
 * @ 描述:
 * @ 日期: 2021/2/4 0004 下午 03:01
 **/

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){//(书中P319)
        OkHttpClient client = new OkHttpClient();//创建实例
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);//发送请求并获取服务端返回的数据
    }
}
