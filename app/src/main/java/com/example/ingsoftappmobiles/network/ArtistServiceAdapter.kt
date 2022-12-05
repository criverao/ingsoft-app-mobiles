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

class ArtistServiceAdapter constructor(context: Context) {
    companion object{
            const val BASE_URL= "https://vinyls-back-group23.herokuapp.com/"
        private var instance: ArtistServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ArtistServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getMusicians() = suspendCoroutine<List<Musician>>{ cont->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Musician(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"))
                    )
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getBands() = suspendCoroutine<List<Band>>{ cont->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Band>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Band(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        creationDate = item.getString("creationDate"))
                    )
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }


    /*suspend fun getMusiciansOnArtist() = suspendCoroutine<List<ArtistDetail>>{ cont->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<ArtistDetail>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, ArtistDetail(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        shortName = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        creationBrithDate = item.getString("birthDate"),
                        tipo="Solista",
                        albums=mutableListOf(),
                        prizes = mutableListOf()))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }*/

    suspend fun getMusiciansArtists() = suspendCoroutine<List<Artist>>{ cont->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artist(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        shortName = item.getString("name"),
                        image = item.getString("image"),
                        tipo="Solista"))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }

    /*suspend fun getBandsOnArtist() = suspendCoroutine<List<ArtistDetail>>{ cont->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<ArtistDetail>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, ArtistDetail(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        shortName = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        creationBrithDate = item.getString("creationDate"),
                        tipo = "Banda",
                        albums = mutableListOf(),
                        prizes = mutableListOf()))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }*/

    suspend fun getBandsArtists() = suspendCoroutine<List<Artist>>{ cont->
        requestQueue.add(getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artist(
                        Id = item.getInt("id"),
                        name = item.getString("name"),
                        shortName = item.getString("name"),
                        image = item.getString("image"),
                        tipo = "Banda"))
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getBand(bandId:Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("bands/$bandId",
            { response ->
                val item = JSONObject(response)
                Log.d("Response", item.toString())
                val artist = ArtistDetail(
                    Id = item.getInt("id"),
                    name = item.getString("name"),
                    shortName = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    creationBrithDate = item.getString("creationDate").substring(0..9),
                    tipo = "Banda",
                    mutableListOf(),
                    prizes = mutableListOf()
                )

                cargarAlbumns(artist, item)
                cargarPrizes(artist, item)

                cont.resume(artist)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getMusician(musicianId:Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("musicians/$musicianId",
            { response ->
                val item = JSONObject(response)
                Log.d("Response", item.toString())

                val artist = ArtistDetail(
                    Id = item.getInt("id"),
                    name = item.getString("name"),
                    shortName = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    creationBrithDate = item.getString("birthDate").substring(0..9),
                    tipo = "Solista",
                    albums = mutableListOf(),
                    prizes = mutableListOf()
                )

                cargarAlbumns(artist, item)
                cargarPrizes(artist, item)


                cont.resume(artist)
            },
            {
                cont.resumeWithException(it)
            }))
    }


    private fun cargarAlbumns(artist:ArtistDetail, item:JSONObject){
        val albumsJson = item.getJSONArray("albums")

        for (i in 0 until albumsJson.length()) {
            val item = albumsJson.getJSONObject(i)
            artist.albums.add(i,
                Album(
                    albumId = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    recordLabel = item.getString("recordLabel"),
                    releaseDate = item.getString("releaseDate"),
                    genre = item.getString("genre"),
                    description = item.getString("description"),
                    releaseYear = item.getString("releaseDate").substring(0..3),
                    excerpt = ""
                ))
        }

    }

    private fun cargarPrizes(artist:ArtistDetail, item:JSONObject){
        val prizesJson = item.getJSONArray("performerPrizes")

        for (i in 0 until prizesJson.length()) {
            val item = prizesJson.getJSONObject(i)
            artist.prizes.add(i,
                Prize(
                    id = item.getInt("id"),
                    name = "",
                    organization = "",
                    description = "",
                    premiationDate = item.getString("premiationDate").substring(0..3)
                ))
        }

    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
}