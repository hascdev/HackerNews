package cl.reign.hackernews.news

import cl.reign.hackernews.data.Repository
import io.reactivex.Observable

class NewsModel(val repository: Repository) : NewsMVP.Model {

    override fun result(): Observable<NewsViewModel> {
        return repository.getHits().map { hit -> NewsViewModel(hit.storyTitle, hit.author, hit.createdAt, hit.storyUrl) }
    }

    override fun removeHit(index: Int) {
        repository.removeHit(index)
    }

}