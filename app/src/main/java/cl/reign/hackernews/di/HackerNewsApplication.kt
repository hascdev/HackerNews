package cl.reign.hackernews.di

import android.app.Application

class HackerNewsApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .newsModule(NewsModule())
            .newsApiServicesModule(NewsApiServicesModule())
            .build()
    }
}