package com.example.shadedetector

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    inner class myClass:AsyncTask<Void, Void, String>()
    {
        var text =""
        override fun doInBackground(vararg params: Void?): String {
            val buildUri1= Uri.parse("https://api.meaningcloud.com/sentiment-2.1").buildUpon()
                .appendQueryParameter("key", "323e6aea1e4aa5d2c2cafa70ab037aa3")
                .appendQueryParameter("lang", "en")
                .appendQueryParameter("txt",text)
                .build()
            var toReturn=""
            toReturn= URL(buildUri1.toString()).readText()
            return toReturn
        }

        override fun onPostExecute(result: String?)
        {
            super.onPostExecute(result)
            findViewById<ProgressBar>(R.id.progressBar1).visibility = View.GONE
            var myJson = JSONObject(result)
            var score_tag = myJson.getString("score_tag")
            var irony : String = myJson.getString("irony")
            var confidence : String = myJson.getString("confidence")
            scoreTag.text = score_tag.toString()

            if(scoreTag.text == "P+") textView2.text = "P+: strong positive statement"
            if(scoreTag.text == "P") textView2.text = "positive statement"
            if(scoreTag.text == "NEU") textView2.text = "NEU: neutral statement"
            if(scoreTag.text == "N+") textView2.text = "N+: strong negative statement"
            if(scoreTag.text == "N") textView2.text = "N: negative statement"
            if(scoreTag.text == "NONE") textView2.text = "NONE: without sentiment"

            /*
               P+: strong positive
               P: positive
               NEU: neutral
               N: negative
               N+: strong negative
               NONE: without sentiment
             */

            ironyText.text = irony.toString()
            confidenceText.text = confidence.toString()
            textView.text = editText.text
        }

    }
    fun doRequest(view: View)
    {
        if(editText.text.isNotEmpty())
        {
            var i = myClass()
            i.text = editText.text.toString()
            findViewById<ProgressBar>(R.id.progressBar1).visibility = View.VISIBLE
            i.execute()
        }
    }


}
