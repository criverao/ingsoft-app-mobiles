package com.example.ingsoftappmobiles.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ingsoftappmobiles.models.Album
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson


class AlbumServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL= "https://vinyls-back-group23.herokuapp.com/"
        var instance: AlbumServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    private val context2:Context by lazy {
        context
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val album = Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        releaseYear = item.getString("releaseDate").substring(0..3),
                        excerpt = item.getString("description").substring(0..56) + "...")
                    list.add(i, album) // se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    fun postAlbum(album:Album, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit) {
        var gson = Gson()


        val postParams = mapOf<String, Any>(
            "name" to album.name,
            "cover" to album.cover,
            "description" to album.description,
            "genre" to album.genre,
            "recordLabel" to album.recordLabel,
            "releaseDate" to album.releaseDate
        )
        Log.d("TAG", JSONObject(postParams).toString())
        requestQueue.add(postRequest("albums",
            JSONObject(postParams),
            Response.Listener<JSONObject> { response ->
                onComplete(gson.fromJson(response.toString(), Album::class.java))
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener, errorListener)
    }

    fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL +path, body, responseListener, errorListener)
    }
}
