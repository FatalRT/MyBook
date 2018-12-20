package com.example.taoch.mybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomActivity extends AppCompatActivity implements OnItemSelectedListener {
    public String[] name={"购物1","购物2","购物3"};
    public String[] billm={"123","2324","123"};
    public String[] date = { "2018/12/15", "2018/12/14", "2018/12/18"};
    List<Map<String,Object>> listItems;
    Map<String, Object> map;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incom);


        ListView listView = (ListView) findViewById(R.id.list_view);
        listItems=new ArrayList<Map<String, Object>>();
        for(int i=0;i<name.length;i++)
        {
            map=new HashMap<String, Object>();
            map.put("billname", name[i]);
            map.put("billm", billm[i]);
            map.put("billdate", date[i]);
            //把列表项加进列表集合
            listItems.add(map);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.bill_item, new String[]{"billname", "billm", "billdate"}, new int[]{R.id.billname, R.id.billm, R.id.billdate});
        listView.setAdapter(simpleAdapter);
        //长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
               AlertDialog.Builder builder=new AlertDialog.Builder(IncomActivity.this);
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
        //type监听器
        spinner =(Spinner) findViewById(R.id.type);//设置数据源
        spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(IncomActivity.this,  BillActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
        return true;
    }
/*
* spinner监听器
* */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String typestr= (String) spinner.getSelectedItem();
        Toast.makeText(getBaseContext(), "您选择的收入类型为："+typestr, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    /*
     * spinner监听器
     * */
}
