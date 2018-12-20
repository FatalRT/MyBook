package com.example.taoch.mybook;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;


public class BillActivity extends AppCompatActivity {
    private PieChartView pie_chart;//数据
    private PieChartData pieChardata;
    List<SliceValue> values = new ArrayList<SliceValue>();//定义数据，实际情况肯定不是这样写固定值的
    private int[] data = {300000, 400000, 150000, 50000, 100000};
    private int[] colorData = {Color.parseColor("#ec063d"),
            Color.parseColor("#f1c704"),
            Color.parseColor("#c9c9c9"),
            Color.parseColor("#2bc208"),
            Color.parseColor("#333333")};
    private String[] stateChar = {"存款", "房产", "股票", "现金", "其他"};

    //柱状图控件
    private ColumnChartView column_chart_view;
    private ColumnChartView column_chart_view2;
    //统计图数据
    private ColumnChartData datas;
    //数据标志
    private List<String> week;
    //模拟数据
    private List<Float> testData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        pie_chart = (PieChartView) findViewById(R.id.pie_chart);
        pie_chart.setOnValueTouchListener(selectListener);//设置点击事件监听
        setPieChartData();
        initPieChart();

        column_chart_view = (ColumnChartView) findViewById(R.id.column_chart_view);
        testData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            testData.add(i *30 + 200f);
        }
        setHistoryChart(testData, Color.parseColor("#00FF00"), column_chart_view);

        column_chart_view2 = (ColumnChartView) findViewById(R.id.column_chart_view2);
        setHistoryChart(testData, Color.parseColor("#FF0000"), column_chart_view2);
    }

    /**
     * 饼图
     * 获取数据
     */
    private void setPieChartData() {
        for (int i = 0; i < data.length; ++i) {
            SliceValue sliceValue = new SliceValue((float) data[i], colorData[i]);
            values.add(sliceValue);
        }
    }
    /**
     * 初始化
     */
    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(values);//填充数据
        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
        pie_chart.setPieChartData(pieChardata);
        pie_chart.setValueSelectionEnabled(true);//选择饼图某一块变大
        pie_chart.setAlpha(0.9f);//设置透明度
        pie_chart.setCircleFillRatio(1f);//设置饼图大小
    }
    /**
     * 监听事件
     */
    private PieChartOnValueSelectListener selectListener = new PieChartOnValueSelectListener() {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onValueSelected(int arg0, SliceValue value) {
            //选择对应图形后，在中间部分显示相应信息
            pieChardata.setCenterText1(stateChar[arg0]);
            pieChardata.setCenterText1Color(colorData[arg0]);
            pieChardata.setCenterText1FontSize(10);
            pieChardata.setCenterText2( "（" + calPercent(arg0) + ")");
            pieChardata.setCenterText2Color(colorData[arg0]);
            pieChardata.setCenterText2FontSize(10);
            }
    };
    private String calPercent(int i) {
        String result = "";
        int sum = 0;
        for (int i1 = 0; i1 < data.length; i1++) {
            sum += data[i1];
        }
        result = String.format("%.2f", (float) data[i] * 100 / sum) + "%";
        return result;
    }

    //柱状图
    private void setHistoryChart(List<Float> columnDatas, int columnColor, ColumnChartView charView) {
        week = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            week.add(getWeekDays(i));
        }
        // 使用的 7列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = 7;
        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();
        //子列数据集合
        List<SubcolumnValue> values;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            //遍历每一列的每一个子列
            for (int j = 0; j < numSubcolumns; ++j) {
                //为每一柱图添加颜色和数值
                float f = columnDatas.get(i);
                values.add(new SubcolumnValue(f, columnColor));
            }
            //创建Column对象
            Column column = new Column(values);
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(0);
            column.setFormatter(chartValueFormatter);
            //是否有数据标注
            column.setHasLabels(true);
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(week.get(i)));
        }
        //创建一个带有之前圆柱对象column集合的ColumnChartData
        datas = new ColumnChartData(columns);
        datas.setValueLabelTextSize(8);
        datas.setValueLabelBackgroundColor(Color.parseColor("#00000000"));
//        data.setValueLabelTypeface(Typeface.DEFAULT);// 设置数据文字样式
        datas.setValueLabelBackgroundEnabled(true);
        datas.setValueLabelBackgroundAuto(false);
        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("金额");//轴名称
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(Color.parseColor("#000000"));//颜色
        axisX.hasLines();
        axisX.setTextColor(Color.parseColor("#000000"));
        axisX.setValues(axisValues);
        axisX.setTextSize(10);
        axisX.setHasSeparationLine(false);
        //把X轴Y轴数据设置到ColumnChartData 对象中
        datas.setAxisXBottom(axisX);
        datas.setAxisYLeft(axisY);
        //给表填充数据，显示出来
        charView.setInteractive(false);
        charView.setColumnChartData(datas);
    }

    private String getWeekDays(int days) {
        if (days == 0) {
            return getString(R.string.today);
        }
        String[] weekDays = getResources().getStringArray(R.array.cms_week);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        Date monday = cal.getTime();
        cal.setTime(monday);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
