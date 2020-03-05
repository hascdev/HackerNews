package cl.reign.hackernews.data

import cl.reign.hackernews.services.NewsApiServices
import cl.reign.hackernews.services.model.Hit
import cl.reign.hackernews.services.model.News
import io.reactivex.Observable
import io.reactivex.functions.Function

class NewsRepository(val newsApiServices: NewsApiServices): Repository {

    val hits: ArrayList<Hit> = ArrayList()
    val CACHE_LIFETIME = 20 * 1000 // Cache 20 seg.

    var lastTimestamp = System.currentTimeMillis()

    override fun removeHit(index: Int) {
        hits.removeAt(index)
    }

    override fun getHits(): Observable<Hit> {
        return getHitsFromCache().switchIfEmpty(getHitsFromNetwork())
    }

    override fun getHitsFromNetwork(): Observable<Hit> {
        val androidNewsObservable: Observable<News> = newsApiServices.search("android")
        return androidNewsObservable
                    .concatMap(Function<News, Observable<Hit>> { news ->
                        Observable.fromIterable(news.hits)
                    })
                    .filter { hit ->
                        hit.storyTitle != null && hit.storyTitle.isNotEmpty() &&
                        hit.storyUrl != null && hit.storyUrl.toString().isNotEmpty()
                    }
                    .doOnNext{ hit ->
                        hits.add(hit)
                    }
    }

    override fun getHitsFromCache(): Observable<Hit> {
        /*
        return if (isUpdated()) {
            Observable.fromIterable(hits)
        } else {
            lastTimestamp = System.currentTimeMillis()
            hits.clear()
            Observable.empty<Hit>()
        }*/
        return Observable.fromIterable(hits)
    }

    private fun isUpdated(): Boolean {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME
    }
}