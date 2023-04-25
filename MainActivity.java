package jar.github.activity;
 
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import com.itsaky.androidide.logsender.LogSender;
import jar.github.activity.databinding.ActivityMainBinding;
import com.itsaky.androidide.logsender.LogSender;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import android.net.http.HttpResponseCache;
import android.webkit.HttpAuthHandler;
import android.media.JetPlayer;
import java.util.jar.JarFile;
import android.webkit.WebChromeClient;

public class MainActivity extends Activity { 
     
    private ActivityMainBinding binding;
    
    //必要参数
    WebView mWebview;
    WebSettings mWebSettings;
    
        //必要参数，少了就会出现空指针异常等情况
        TextView beginLoading,endLoading,loading,mtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mWebview = (WebView) findViewById(R.id.webView1);
           
       endLoading = (TextView) findViewById(R.id.text_endLoading);
        
       Context context =( getApplicationContext() );
        WebView webView = new WebView(context);
       WebSettings webSettings = webView.getSettings();
       webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        
            //缩放        
            //设置可以支持缩放              
            mWebview.getSettings().setSupportZoom(true);        
            //设置true,才能让Webivew支持<meta>标签的viewport属性
            mWebview.getSettings().setUseWideViewPort(true);        
            //设置出现缩放工具        
            mWebview.getSettings().setBuiltInZoomControls(true);        
            //设置隐藏缩放控件        
            mWebview.getSettings().setDisplayZoomControls(false);        
            //最小缩放等级        
            mWebview.setInitialScale(364);
            
            //设置隐藏滑动条
            final  WebView mWebView = (mWebview) = mWebview ;
            //false = 不显示滑动条
            mWebView.setVerticalScrollBarEnabled(false);  
            mWebView.setHorizontalScrollBarEnabled(false);
            
            //此方法仅用于注释
            WebView mwebView = (mWebView);

            // 加载apk包中的html页面
            mwebView.loadUrl("file:///android_asset/r.html");
            
            
            //设置WebViewClient类
            mWebview.setWebViewClient(new WebViewClient() {
            
        //设置WebView不跳转浏览器，在WebView加载网页//新版本
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // 返回false，意味着请求过程中，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                // 返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://baidu.com/
                // 加载Url，使网页在WebView中打开，在这里可以进行重构url
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

        //销毁Webview
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
}
        
