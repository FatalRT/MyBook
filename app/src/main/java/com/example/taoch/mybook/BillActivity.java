package com.example.taoch.mybook;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;

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
    //饼状图
    private PieChartView pie_chart;//数据
    private PieChartData pieChardata;
    List<SliceValue> values = new ArrayList<SliceValue>();//定义数据，实际情况肯定不是这样写固定值的
    private float[] data=new float[5];
    private int[] colorData = {Color.parseColor("#ec063d"),
            Color.parseColor("#f1c704"),
            Color.parseColor("#c9c9c9"),
            Color.parseColor("#2bc208"),
            Color.parseColor("#333333")};
    private String[] stateChar = {"存款", "房产", "股票", "现金", "其他"};

    //柱状图控件
    private ColumnChartView column_chart_view;  //统计图数据
    private ColumnChartData datas;    //数据标志
    private List<String> week;//模拟数据
    private List<Float> testData;
    private  int Day;
    private Calendar cal;
    private float[] shourudata=new float[7];
    private float[] zhichudata=new float[7];

    //数据库操作
    private List<bill> billList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);


        //饼状图
        pie_chart = (PieChartView) findViewById(R.id.pie_chart);
        pie_chart.setOnValueTouchListener(selectListener);//设置点击事件监听
        setPieChartData();
        initPieChart();

        //柱状图
        cal = Calendar.getInstance();
        Day=cal.get(Calendar.DAY_OF_MONTH);
        column_chart_view = (ColumnChartView) findViewById(R.id.column_chart_view);
        setHistoryChart( Color.parseColor("#00FF00"), column_chart_view);

        //跳转按钮
        final Button income = (Button) findViewById(R.id.newbill);
        final Button expend=(Button) findViewById(R.id.show);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(BillActivity.this,NewBillActivity.class);
                startActivity(s);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                BillActivity.this.finish();
            }
        });
        expend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(BillActivity.this,NewBillActivity.class);
                startActivity(s);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                BillActivity.this.finish();
            }
        });


    }


    //饼图
    /**
     * 饼图
     * 获取数据
     */
    private void setPieChartData() {
        float a=LitePal.sum(bill.class,"money",float.class);
        data[0]=50000+a;//存款
        data[1]=100000;//房产
        data[2]=20000;//股票
        data[3]=10000;//现金
        data[4]=20000;//其他
        // "存款", "房产", "股票", "现金", "其他"
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
    private void setHistoryChart( int columnColor, ColumnChartView charView) {
        week = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            week.add(getWeekDays(i));
        }
        //最近七天收入情况
          for(int n=0;n<7;n++) {
              List<bill> shouru = LitePal.select("money")
                      .where("type = ? and data= ?", "1", String.valueOf(Day-n))
                      .find(bill.class);
              float sum=0;
              for(bill Bill:shouru)
              {
                  sum=sum+Bill.getMoney();
              }
              shourudata[n]=sum;
          }
          //最近七天支出情况
        for(int n=0;n<7;n++) {
            List<bill> zhichu = LitePal.select("money")
                    .where("type = ? and data= ?", "2", String.valueOf(Day-n))
                    .find(bill.class);
            float sum=0;
           for(bill Bill:zhichu)
           {
               sum=sum+Bill.getMoney();
           }
            zhichudata[n]=sum;
        }
        //横坐标
        testData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            testData.add(i *30 + 200f);
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
                float f =shourudata[6-i];
                float h= zhichudata[6-i];
                values.add(new SubcolumnValue(f, Color.parseColor("#00FF90")));
                values.add(new SubcolumnValue(h, Color.parseColor("#FF0000")));
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
        datas.setValueLabelBackgroundColor(Color.parseColor("#000000"));
        datas.setValueLabelBackgroundEnabled(true);
        datas.setValueLabelBackgroundAuto(false);
        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("金额(RMB)");//轴名称
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

    //今天日期获取
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

    //数据路
    private void initbill(){
        billList.clear();
        billList=LitePal.findAll(bill.class) ;
    }

}
