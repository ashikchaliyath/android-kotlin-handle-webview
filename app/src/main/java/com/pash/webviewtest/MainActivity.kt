package com.pash.webviewtest

import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var myWebView: WebView
    lateinit var myButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        myWebView = findViewById(R.id.mywebview) as WebView
        myButton = findViewById(R.id.mybutton) as Button

        myButton.setOnClickListener(){
            myWebView.loadUrl("javascript:(function() {" +
                    "changeData(); "+
                    "})()");
        }

        val data_ = "<html>\n" +
                "    <head></head>\n" +
                "    \n" +
                "    <body>\n" +
                "    \n" +
                "      <h2 id=\"abc\" >JavaScript Alert</h2>\n" +
                "    \n" +
                "      <button onclick=\"myFunction()\">Try it</button>\n" +
                "    \n" +
                "      <script>\n" +
                "        function myFunction() {\n" +
                "          Android.showToast('my message from web');\n" +
                "        }\n" +
                "        function changeData() {\n" +
                "          document.getElementById(\"abc\").innerHTML = \"New text!\";\n" +
                "        }\n" +

                "      </script>\n" +
                "    </body>\n" +
                "    \n" +
                "    </html>\n"

        val encodedHtml: String = Base64.encodeToString(data_.toByteArray(), Base64.NO_PADDING)
        myWebView.loadData(encodedHtml, "text/html", "base64");

        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")
        myWebView.settings.javaScriptEnabled = true


    }
    
    class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }
}