package com.example.ingsoftappmobiles.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ingsoftappmobiles.models.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson

class AlbumServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL= "https://vinyls-back-group23.herokuapp.com/"
        private var instance: AlbumServiceAdapter? = null
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

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            { response ->
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
                        releaseYear = item.getString("releaseDate"),
                        excerpt = item.getString("description"))
                    list.add(i, album) // se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getAlbum(albumId:Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val item = JSONObject(response)
                Log.d("Response", item.toString())
                val album = AlbumDetail(
                    albumId = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    recordLabel = item.getString("recordLabel"),
                    releaseDate = item.getString("releaseDate"),
                    genre = item.getString("genre"),
                    description = item.getString("description"),
                    tracks = mutableListOf(),
                    comments = mutableListOf(),
                )
                loadTracks(album, item)
                loadComments(album, item)
                cont.resume(album)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    private fun loadTracks(album: AlbumDetail, item:JSONObject){
        val tracksJson = item.getJSONArray("tracks")

        for (i in 0 until tracksJson.length()) {
            val item = tracksJson.getJSONObject(i)
            album.tracks.add(i,
                Track(
                    Id = item.getInt("id"),
                    name = item.getString("name"),
                    duration = item.getString("duration")
                ))
        }

    }

    private fun loadComments(album: AlbumDetail, item:JSONObject){
        val tracksJson = item.getJSONArray("comments")

        for (i in 0 until tracksJson.length()) {
            val item = tracksJson.getJSONObject(i)
            album.comments.add(i,
                Comment(
                    Id = item.getInt("id"),
                    description = item.getString("description"),
                    rating = item.getString("rating")
                ))
        }

    }

    fun postAlbum(album:Album, onComplete:(resp:Album)->Unit, onError: (error:VolleyError)->Unit) {
        val gson = Gson()

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
            { response ->
                onComplete(gson.fromJson(response.toString(), Album::class.java))
            },
            {
                onError(it)
            }))
    }

    fun postTrack(track: Track, onComplete:(resp:Track)->Unit, onError: (error:VolleyError)->Unit) {
        val gson = Gson()

        val postParams = mapOf(
            "name" to track.name,
            "duration" to track.duration
        )
        Log.d("TAG", JSONObject(postParams).toString())
        requestQueue.add(postRequest("albums/" + track.Id + "/tracks",
            JSONObject(postParams),
            { response ->
                onComplete(gson.fromJson(response.toString(), Track::class.java))
            },
            {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener, errorListener)
    }

    private fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL +path, body, responseListener, errorListener)
    }


}
