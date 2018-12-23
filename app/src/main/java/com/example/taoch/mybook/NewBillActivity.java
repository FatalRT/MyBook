package com.example.taoch.mybook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewBillActivity extends AppCompatActivity {

    //账目
    List<Map<String,Object>> listItems;
    Map<String, Object> map;

    //类型选择
    private Spinner spinner;
    private Spinner spinner2;
    boolean isSpinnerFirst = true ;
    boolean isSpinner2First = true ;
    private  String typestr;
    private  String typestr2;

    //日期选择器
    private Calendar cal;
    private  int  Year,Day,Month,Week;
    private TextView text_data;
    private ImageButton riqi;

    //提交按钮
    private EditText bz;
    private EditText jinge;
    private Button shouru;
    private Button zhichu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        //日期选择器
        cal = Calendar.getInstance();
        Year=cal.get(Calendar.YEAR);
        Month=cal.get(Calendar.MONTH);
        Day=cal.get(Calendar.DAY_OF_MONTH);
        text_data = (TextView)findViewById(R.id.text_data);
        riqi =(ImageButton)findViewById(R.id.riqi);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date1 = new Date(System.currentTimeMillis());
        text_data.setText(simpleDateFormat.format(date1));

        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        text_data.setText(year+"年"+(month+1)+"月"+day+"日");
                        Year = year;
                        Month=month;
                        Day = day;
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(NewBillActivity.this, DatePickerDialog.BUTTON_POSITIVE,listener,Year,Month,Day);
                dialog.show();
            }
        });


        //账目显示
        ListView listView = (ListView) findViewById(R.id.list_view);
        listItems=new ArrayList<Map<String, Object>>();
        List<bill> mybill=LitePal.select("name","text","data","year","month","money")
                                 .order("id desc")
                                 .find(bill.class);
        for (bill Bill:mybill){
            String rq;
            rq=Bill.getYear()+"年"+Bill.getMonth()+"月"+Bill.getData()+"日";
            map=new HashMap<String, Object>();
            map.put("billname", Bill.getName());
            map.put("billm", Bill.getMoney());
            map.put("billbeizhu",Bill.getText());
            map.put("billdate", rq);
            //把列表项加进列表集合
            listItems.add(map);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.bill_item, new String[]{"billname", "billm", "billdate","billbeizhu"}, new int[]{R.id.billname, R.id.billm, R.id.billdate,R.id.billbeizhu});
        listView.setAdapter(simpleAdapter);
        //长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(NewBillActivity.this);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");
                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listItems.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        simpleAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "删除列表项", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return false;
            }
        });

        //下拉菜单监听器
        spinner =(Spinner) findViewById(R.id.type);//设置数据源
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerFirst) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                  //  view.setVisibility(View.INVISIBLE) ;
                }
                isSpinnerFirst = false;
                typestr= (String) spinner.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2 =(Spinner) findViewById(R.id.type4);//设置数据源
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinner2First) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                   // view.setVisibility(View.INVISIBLE) ;
                }
                isSpinner2First = false;
                typestr2 = (String) spinner2.getSelectedItem();
//                    Toast.makeText(getBaseContext(), "您选择的支出类型为：" + typestr, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //提交按钮操作

        shouru=(Button)findViewById(R.id.shouru);
        zhichu=(Button)findViewById(R.id.zhichu);
        jinge=(EditText)findViewById(R.id.jinge);
        bz=(EditText)findViewById(R.id.beizhutext);
        shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jinge.getText().toString().equals("")){

                float m= Float.parseFloat(jinge.getText().toString());
                bill shouru=new bill();
                    shouru.setType(1);
                    shouru.setName(typestr);
                    shouru.setData(""+Day);
                    shouru.setYear(""+Year);
                    shouru.setMonth(""+Month);
                    shouru.setMoney(m);
                    shouru.setText(bz.getText().toString());
                    shouru.save();
                Toast.makeText(getBaseContext(), "添加成功", Toast.LENGTH_SHORT).show();
                Intent s = new Intent(NewBillActivity.this,BillActivity.class);
                startActivity(s);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                NewBillActivity.this.finish();
                }
                else
                {
                    Toast toast=Toast.makeText(NewBillActivity.this,"请输入金额",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        zhichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!jinge.getText().toString().equals("")){

                    float m= Float.parseFloat(jinge.getText().toString());
                    bill zhichu=new bill();
                    zhichu.setType(2);
                    zhichu.setName(typestr2);
                    zhichu.setData(""+Day);
                    zhichu.setYear(""+Year);
                    zhichu.setMonth(""+Month);
                    zhichu.setMoney(-m);
                    zhichu.setText(bz.getText().toString());
                    zhichu.save();

                    Toast.makeText(getBaseContext(), "添加成功", Toast.LENGTH_SHORT).show();
                    Intent s = new Intent(NewBillActivity.this,BillActivity.class);
                    startActivity(s);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    NewBillActivity.this.finish();
                }
                else
                {
                    Toast toast=Toast.makeText(NewBillActivity.this,"请输入金额",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(NewBillActivity.this,  BillActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
        return true;
    }


}

