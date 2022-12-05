package com.example.ingsoftappmobiles.network

import android.content.Context
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.models.Artist
import com.example.ingsoftappmobiles.models.Collector

class CacheManager(context: Context) {
    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albumsHashMap: HashMap<Int, Album> = hashMapOf()
    fun addAlbums(albums: List<Album>){
        for (album in albums) {
            if (!albumsHashMap.containsKey(album.albumId)) {
                albumsHashMap[album.albumId] = album
            }
        }
    }
    fun getAlbums() : List<Album>{
        return if (albumsHashMap.isNotEmpty()) ArrayList(albumsHashMap.values) else listOf()
    }

    private var artistsHashMap: HashMap<Int, Artist> = hashMapOf()
    fun addArtists(artists: List<Artist>){
        for (artist in artists) {
            if (!artistsHashMap.containsKey(artist.Id)) {
                artistsHashMap[artist.Id] = artist
            }
        }
    }
    fun getArtists() : List<Artist>{
        return if (artistsHashMap.isNotEmpty()) ArrayList(artistsHashMap.values) else listOf()
    }

    private var collectorsHashMap: HashMap<Int, Collector> = hashMapOf()
    fun addCollectors(collectors: List<Collector>){
        for (collector in collectors) {
            if (!collectorsHashMap.containsKey(collector.collectorId)) {
                collectorsHashMap[collector.collectorId] = collector
            }
        }
    }
    fun getCollectors() : List<Collector>{
        return if (collectorsHashMap.isNotEmpty()) ArrayList(collectorsHashMap.values) else listOf()
    }

}