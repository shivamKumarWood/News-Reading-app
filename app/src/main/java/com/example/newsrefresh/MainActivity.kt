package com.example.newsrefresh

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), newsItemClicked {
    private lateinit var mAdapter:newsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycle:RecyclerView=findViewById(R.id.recyclerview)
        recycle.layoutManager=LinearLayoutManager(this)
        fetchData()
        mAdapter=newsListAdapter(this)
        recycle.adapter=mAdapter
    }
    private fun fetchData(){
        val url="https://newsdata.io/api/1/news?apikey=pub_262719065142bf4c6eea6bec8159978f9ec66&q=api%20url "
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("results")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News (
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("category"),
                        newsJsonObject.getString("link"),
                        newsJsonObject.getString("image_url")

                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this, "not working", Toast.LENGTH_LONG).show()
            }

        )

            // Add the request to the RequestQueue.
            //queue.add(jsonObjectRequest)
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val intent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(this@MainActivity, Uri.parse(item.url))
    }
}