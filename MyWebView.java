package kt.kotlin.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;



public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
        @Override
    public boolean canGoBack() {
        // 在这里添加你的逻辑，控制是否可以返回上一页
        return super.canGoBack();
    }

    @Override
    public void goBack() {
        // 在这里添加你的逻辑，处理返回上一页的操作
        super.goBack();
    }
}