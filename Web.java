package kt.kotlin.activity;
 
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import com.itsaky.androidide.logsender.LogSender;
import kt.kotlin.activity.databinding.ActivityMainBinding;
import com.itsaky.androidide.logsender.LogSender;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import android.net.http.HttpResponseCache;
import android.webkit.HttpAuthHandler;
import android.media.JetPlayer;
import java.util.jar.JarFile;
import android.webkit.WebChromeClient;
import android.os.Environment;
import android.R.layout;

public class Web extends Activity { 
     
    private ActivityMainBinding binding;
    
    private File File;
    //必要参数
    WebView mWebview;
    WebSettings mWebSettings;
    
        //必要参数，少了就会出现空指针异常等情况
        TextView beginLoading,endLoading,loading,mtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        mWebview = findViewById(R.id.webView); 
        // 获取 WebView 对象
        
        
            //缩放        
            //设置可以支持缩放              
            mWebview.getSettings().setSupportZoom(true);        
            //设置true,才能让Webivew支持<meta>标签的viewport属性
            mWebview.getSettings().setUseWideViewPort(true);        
            //设置出现缩放工具        
            mWebview.getSettings().setBuiltInZoomControls(true);        
            //设置隐藏缩放控件        
            mWebview.getSettings().setDisplayZoomControls(false);        
            
            //设置隐藏滑动条
            final  WebView mWebView = (mWebview) = mWebview ;
            //false = 不显示滑动条
            mWebView.setVerticalScrollBarEnabled(false);  
            mWebView.setHorizontalScrollBarEnabled(false);
            
       
            // 加载apk包中的html页面
       mWebView.loadUrl("file:///android_asset/k.html");
            
        
            //设置WebViewClient类
            mWebview.setWebViewClient(new WebViewClient() {
            
        //设置WebView不跳转浏览器，在WebView加载网页 新版本
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                    WebView mwebView = (mWebview) ;
                        mwebView.loadUrl(request.getUrl().toString());
                       
                    }
                return true;
                
            
                            }
                    });
           
              }
   
             
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
        };

      //无效措施 此方法被开发者滥用 请勿模仿
    
        @Override
        protected void onDestroy() {
                if (mWebview != null) {
                        mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                        mWebview.clearHistory();

                        ((ViewGroup) mWebview.getParent()).removeView(mWebview);
                        mWebview.destroy();
                        mWebview = null;
                    }
                super.onDestroy();

            };
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // 加载菜单布局
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                if (mWebview.canGoBack()) {
                    mWebview.goBack(); // 返回上一页
                }
                return true;
            case R.id.action_forward:
                if (mWebview.canGoForward()) {
                    mWebview.goForward(); // 前进到下一页
                }
                return true;
            case R.id.action_refresh:
                mWebview.reload(); // 刷新当前页面
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}