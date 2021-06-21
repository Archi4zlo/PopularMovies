package com.archi4zlo.retrofitsimple.data.remote.movie

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieApi {
    @GET("./trending/movie/week?api_key=38198add11974becf5a1b18ca287f1ab")
    @Headers("Content-Type: application/json")
    fun getQuestList(): Single<MovieListResponse>

}