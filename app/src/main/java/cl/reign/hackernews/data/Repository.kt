package cl.reign.hackernews.data

import cl.reign.hackernews.services.model.Hit
import io.reactivex.Observable

interface Repository {

    fun getHits(): Observable<Hit>

    fun getHitsFromNetwork(): Observable<Hit>

    fun getHitsFromCache(): Observable<Hit>

    fun removeHit(index: Int)
}