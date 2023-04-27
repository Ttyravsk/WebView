package jar.github.activity

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.util.Log
import android.content.Context
import android.widget.Toast
import com.itsaky.androidide.logsender.LogSender
import android.content.Context
import android.os.Bundle
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        val myWebView = MyWebView(this)
        webViewContainer.addView(myWebView)
        
        // 从assets目录加载HTML文件
        val html = assets.open("test.html").bufferedReader().use { it.readText() }

        // 设置WebView不跳转浏览器
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })

        // 加载HTML内容
        webView.loadData(html, "text/html", "UTF-8")
    }
}