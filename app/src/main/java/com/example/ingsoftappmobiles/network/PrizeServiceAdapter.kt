package com.example.ingsoftappmobiles.network
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ingsoftappmobiles.models.*
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PrizeServiceAdapter constructor(context: Context) {
    companion object{
            const val BASE_URL= "https://vinyls-back-group23.herokuapp.com/"
        private var instance: PrizeServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: PrizeServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getPrize(prizeId:Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("prizes/$prizeId",
            { response ->
                val item = JSONObject(response)
                Log.d("Response", item.toString())
                val prize = Prize(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    organization = item.getString("organization"),
                    description = item.getString("description"),
                    premiationDate = ""
                )
                cont.resume(prize)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
}