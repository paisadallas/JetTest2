package com.tamayo.jettest2.data.rest

import com.tamayo.jettest2.data.model.GifsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    //https://api.giphy.com/v1/gifs/search?api_key=WeuzUN3GFSukJMOwXNlLE94HvlgmBZfV&q=cats

    @GET(ENDPOINT)
    suspend fun getGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") q: String? = null
    ): Response<GifsResponse>


    companion object{
        const val ENDPOINT = "search"
        const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        const val API_KEY = "WeuzUN3GFSukJMOwXNlLE94HvlgmBZfV"
    }
}