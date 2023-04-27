package jar.github.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.content.Intent;
import java.io.FileInputStream;
import android.util.Log;
import android.content.Context;
import java.io.FileOutputStream;


public class APPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //  操作将用户引导至一个系统设置页面，在该页面上，用户可以为您的应用启用以下选项：授予所有文件的管理权限。
    
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || Environment.isExternalStorageManager()) {

              } else {
                  Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                  startActivity(intent);
              }
        
      String dataDir = "/storage/emulated/0/your_app_data_directory";
File appDataDir = new File(dataDir);
if (!appDataDir.exists()) {
    appDataDir.mkdirs();
}
}
    
}
