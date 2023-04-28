package kt.kotlin.activity

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class MyWebView : WebView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
    }

}