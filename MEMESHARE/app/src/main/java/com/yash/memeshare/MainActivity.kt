package com.yash.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var currentimgurl:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apimeme()
    }

    fun apimeme()
    {
        // Instantiate the RequestQueue.
        loading.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                currentimgurl = response.getString("url")
                Glide.with(this).load(currentimgurl).listener(object :RequestListener<Drawable>
                {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading.visibility=View.GONE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        loading.visibility=View.GONE
                        return false
                    }
                }).into(memeimage)
            },
            Response.ErrorListener {
                Toast.makeText(this,"something went wrong !",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun sharememe(view: View)
    {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"HEY, checkout this cool meme,it's so funny ! $currentimgurl ")
        val chose=Intent.createChooser(intent,"Share Using :")
        startActivity(chose)

    }
    fun nextmeme(view: View)
    {
        apimeme()

    }

}