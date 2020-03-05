package cl.reign.hackernews.services

import cl.reign.hackernews.services.model.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {

    @GET("search_by_date")
    fun search(@Query("query") query: String): Observable<News>
}