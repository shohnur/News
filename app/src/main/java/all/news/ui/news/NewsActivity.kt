package all.news.ui.news

import all.news.R
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbarNews)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val url = intent.getStringExtra("url")
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
    }
}
