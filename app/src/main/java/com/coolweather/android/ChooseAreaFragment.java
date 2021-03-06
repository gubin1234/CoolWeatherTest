package com.coolweather.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ 作者: GB
 * @ 类名: ChooseAreaFragment
 * @ 包名: com.coolweather.android
 * @ 描述:
 * @ 日期: 2021/2/4 0004 下午 04:37
 **/
public class ChooseAreaFragment extends Fragment {


    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;

    private TextView titleText;

    private Button backButton;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    /**
     * 省列表
     */
    private List<Province> provinceList;

    /**
     * 市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */
    private List<County> countyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince;

    /**
     * 选中的城市
     */
    private City selectedCity;

    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);//加载布局(书中P146)
        titleText = (TextView) view.findViewById(R.id.title_text);//获得控件实例
        backButton = (Button) view.findViewById(R.id.back_button);//获得控件实例
        listView = (ListView) view.findViewById(R.id.list_view);//获得控件实例
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);//初始化ArrayAdapter(书中P114)
        listView.setAdapter(adapter);//设为ListView适配器

        //判断绑定的活动是否是天气活动(有DrawerLayout那个活动)
        if(getContext()instanceof WeatherActivity) {
            //获取状态栏高度
            int statusBarHeight = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0)
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            view.findViewById(R.id.choose_area_frameLayout).setPadding(0, statusBarHeight, 0, 0);
        }

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){//ListView点击事件(书中P120)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//点击按钮回调方法
                if (currentLevel == LEVEL_PROVINCE){//当前选中的级别==省
                    selectedProvince = provinceList.get(position);
                    queryCities();//再查询市
                }else if (currentLevel == LEVEL_CITY){//当前选中的级别==市
                    selectedCity = cityList.get(position);
                    queryCounties();//再查询县
                }else if (currentLevel == LEVEL_COUNTY){//当前选中的级别==县
                    String weatherId = countyList.get(position).getWeatherId();//天气id
                    if (getActivity() instanceof MainActivity) {//instanceof判断一个对象是否属于某个类
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();//销毁主活动
                    }else if (getActivity() instanceof WeatherActivity){
                        WeatherActivity activity = (WeatherActivity)getActivity();
                        activity.drawerLayout.closeDrawers();//关闭滑动菜单
                        activity.swipeRefresh.setRefreshing(true);//显示下拉刷新列表
                        activity.requestWeather(weatherId);//请求新城市的天气信息
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){//碎片中返回按钮点击事件
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY){//当前级别为县，返回到市中
                    queryCities();
                }else if (currentLevel == LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查
     */
    private void queryProvinces(){
        titleText.setText("中国");//头局部标题设为中国
        backButton.setVisibility(View.GONE);//将返回按钮隐藏
        provinceList = LitePal.findAll(Province.class);//从LitePal的查询接口读取省级数据(书中P241)
        if (provinceList.size() > 0){//读取到数据，直接显示在界面上
            dataList.clear();
            for (Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();//刷新列表
            listView.setSelection(0);//将列表滚动到顶部
            currentLevel = LEVEL_PROVINCE;
        }else {//没读取到数据
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");//从服务器上查询数据
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查
     */
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);//返回按钮显示
        cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);//根据省条件筛选市
        if (cityList.size() > 0){
            dataList.clear();
            for (City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查
     */
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);//根据省条件筛选市
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     */
    private void queryFromServer(String address, final String type){
        showProgressDialog();//显示进度条
        HttpUtil.sendOkHttpRequest(address, new Callback() {//向服务器发送请求
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();//具体返回的数据（书中P320）
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);//解析和处理服务器返回的数据Utility中的方法
                }else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());//获取城市所在省的id
                }else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {//queryProvinces()涉及UI操作，通过runOnUiThread（），返回到主线程
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();//显示在屏幕上
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());//书中P348
            progressDialog.setMessage("正在加载...");//设置提示信息
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
