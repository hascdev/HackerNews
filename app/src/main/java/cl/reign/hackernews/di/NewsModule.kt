package cl.reign.hackernews.di

import cl.reign.hackernews.data.NewsRepository
import cl.reign.hackernews.data.Repository
import cl.reign.hackernews.news.NewsMVP
import cl.reign.hackernews.news.NewsModel
import cl.reign.hackernews.news.NewsPresenter
import cl.reign.hackernews.services.NewsApiServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsModule {

    @Provides
    fun provideNewsPresenter(newsModel: NewsMVP.Model): NewsMVP.Presenter {
        return NewsPresenter(newsModel)
    }

    @Provides
    fun provideNewsModel(repository: Repository): NewsMVP.Model {
        return NewsModel(repository)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(newsApiServices: NewsApiServices): Repository {
        return NewsRepository(newsApiServices)
    }
}