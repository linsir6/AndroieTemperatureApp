package com.qf58.temperature;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText tmp;
    private Button add;
    private LineChart lineChart;
    private String mPhone;
    Gson gson = new Gson();
    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmp = (EditText) findViewById(R.id.tmp);
        add = (Button) findViewById(R.id.add);
        lineChart = (LineChart) findViewById(R.id.chart);

        mPhone = getIntent().getExtras().getString("phone");

        JSONArray array = CacheUtils.getInstance().getJSONArray("user");
        List<User> userList = gson.fromJson(array.toString(),
                new TypeToken<List<User>>() {
                }.getType());

        for (User user : userList) {
            if (user.getPhone().equals(mPhone)) {
                me = user;
            }
        }
        if (me.getTem() == null) {
            me.setTem(new ArrayList<Float>());
        }

        draw();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    me.getTem().add(Float.valueOf(tmp.getText().toString()));
                    if (Float.valueOf(tmp.getText().toString()) > 40) {
                        ToastUtils.showShort("您的体温，超过40度，请注意休息！");
                    }
                    JSONArray array = CacheUtils.getInstance().getJSONArray("user");
                    List<User> userList = gson.fromJson(array.toString(),
                            new TypeToken<List<User>>() {
                            }.getType());

                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getPhone().equals(mPhone)) {
                            userList.get(i).setTem(me.getTem());
                        }
                    }
                    CacheUtils.getInstance().put("user", gson.toJson(userList));
                    draw();
                } catch (Exception e) {
                    ToastUtils.showShort("输入参数有误，请检查后重新输入");
                }

            }
        });

    }

    private void draw() {
        Description description = new Description();
        description.setText("摄氏度");//描述文字
        description.setTextColor(Color.BLUE);//描述文字字体颜色
        lineChart.setDescription(description);//设置图表描述


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴位置
        xAxis.setDrawGridLines(false);//绘制网格线,默认值可以不写
        xAxis.setGridLineWidth(6f);// 设置该轴网格线的宽度。
        xAxis.setEnabled(false);


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int) value;
                return "第" + i + "次";
            }
        });

        ArrayList<Entry> poitList = new ArrayList<Entry>();
        for (int i = 0; i < me.getTem().size(); i++) {
            poitList.add(new Entry(i, me.getTem().get(i)));
        }

        if (poitList.size() == 0) {
            return;
        }
        LineDataSet dataSet = new LineDataSet(poitList, "体温");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setHighLightColor(Color.RED); // 设置点击某个点时，横竖两条线的颜色
        dataSet.setDrawValues(true);//在点上显示数值 默认true
        dataSet.setValueTextSize(10f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        LineData data = new LineData(dataSet);

        lineChart.setData(data);
        lineChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
