package jar.github.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.content.Intent;
import java.io.FileInputStream;
import android.util.Log;
import android.content.Context;
import java.io.FileOutputStream;

public class DataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    //读取内置data目录下文件
    public String readDataFile(String fileName) {
        String res = "";
        try {
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();
 
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "readDataFile Error!" + e.getMessage());
        }
        return res;
    }
 
    //写入内置data目录下文件
    private void writeDataFile(String fileName, String content) {
        try {
            FileOutputStream fut = openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND);
            byte[] bytes = content.getBytes();
            fut.write(bytes);
            fut.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "writeDataFile Error!" + e.getMessage());
        }
    }
}