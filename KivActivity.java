package kt.kotlin.activity;

import android.app.ListActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KivActivity extends AppCompatActivity {
//1、定义对象
     ListView listView;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list);
         //2、绑定控件
         listView=(ListView) findViewById(R.id.list_view);
         //3、准备数据
         String[] data={"菠萝","芒果","石榴","葡萄", "苹果", "橙子", "西瓜","柠檬","青枣","梨子","栗子", "香菇", "鲜奶", "牛奶","鲜虾","罗非鱼","草鱼","野生鱼", "野生猴子", "小萝莉", "野生萝莉", "荔枝", "龙眼", "香蕉", "榴莲", "鲍鱼", "扇贝", "鲍鱼出汁", "韭菜花", "菜花", "萝莉粉避"};
         //4、创建适配器 连接数据源和控件的桥梁
         //参数 1：当前的上下文环境
         //参数 2：当前列表项所加载的布局文件
         //(android.R.layout.simple_list_item_1)这里的布局文件是Android内置的，里面只有一个textview控件用来显示简单的文本内容
         //参数 3：数据源
         ArrayAdapter<String> adapter=new ArrayAdapter<>(KivActivity.this,android.R.layout.simple_list_item_1,data);
         //5、将适配器加载到控件中
         listView.setAdapter(adapter);
         //6、为列表中选中的项添加单击响应事件
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
{
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
         String result=((TextView)view).getText().toString();
         Toast.makeText(KivActivity.this,"您选择的是："+result,Toast.LENGTH_LONG).show();
         }
     });
   }
 }