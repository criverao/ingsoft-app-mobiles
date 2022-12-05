package com.example.ingsoftappmobiles.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ingsoftappmobiles.models.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorServiceAdapter constructor(context: Context){

    companion object {
        const val BASE_URL= "https://vinyls-back-group23.herokuapp.com/"
        var instance: CollectorServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(
                        collectorId = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email"))
                    )
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollector(collectorId:Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("collectors/$collectorId",
            { response ->
                val item = JSONObject(response)
                Log.d("Response", item.toString())
                val collector = CollectorDetail(
                    collectorId = item.getInt("id"),
                    name = item.getString("name"),
                    telephone = item.getString("telephone"),
                    email = item.getString("email"),
                    albums = mutableListOf(),
                    musicians = mutableListOf()
                )
                loadAlbums(collector, item)
                loadMusicians(collector, item)
                cont.resume(collector)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    private fun loadMusicians(collector: CollectorDetail, item:JSONObject){
        val tracksJson = item.getJSONArray("favoritePerformers")

        for (i in 0 until tracksJson.length()) {
            val item = tracksJson.getJSONObject(i)
            collector.musicians.add(i,
                Musician(
                    Id = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    birthDate = ""
                ))
        }

    }

    private fun loadAlbums(collector: CollectorDetail, item:JSONObject){
        val collectorAlbumsJson = item.getJSONArray("collectorAlbums")

        for (i in 0 until collectorAlbumsJson.length()) {
            val item = collectorAlbumsJson.getJSONObject(i)
            collector.albums.add(i,
                CollectorAlbum(
                    collectorAlbumId = item.getInt("id"),
                    price = item.getString("price"),
                    status = item.getString("status"),
                    albumName = "",
                    albumGenre = "",
                    albumCover = ""
                ))
        }

    }

    suspend fun getCollectorAlbum(collectorId:Int, albumId:Int) = suspendCoroutine{ cont->
        requestQueue.add(getRequest("collectors/$collectorId/albums/$albumId/",
            { response ->
                val resp = JSONArray(response)
                val item = resp.getJSONObject(0)
                Log.d("Response", item.toString())
                val subitem = item.getJSONObject("album")
                val collectorAlbum = CollectorAlbum(
                    collectorAlbumId = item.getInt("id"),
                    price = item.getString("price"),
                    status = item.getString("status"),
                    albumName = subitem.getString("name"),
                    albumGenre = subitem.getString("genre"),
                    albumCover = subitem.getString("cover")
                )
                cont.resume(collectorAlbum)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener, errorListener)
    }
}