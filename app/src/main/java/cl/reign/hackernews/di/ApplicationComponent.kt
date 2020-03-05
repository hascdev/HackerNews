package cl.reign.hackernews.di

import cl.reign.hackernews.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NewsModule::class, NewsApiServicesModule::class])
interface ApplicationComponent {

    fun inject(target: MainActivity)
}