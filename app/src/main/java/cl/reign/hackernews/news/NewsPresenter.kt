package cl.reign.hackernews.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NewsPresenter(val model: NewsMVP.Model): NewsMVP.Presenter {

    private lateinit var view: NewsMVP.View
    private lateinit var disposable: Disposable

    override fun loadData() {
        disposable = model.result()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<NewsViewModel>() {
                override fun onNext(newsViewModel: NewsViewModel) {
                    view.updateData(newsViewModel)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    view.cancelRefreshDialog()
                }

                override fun onComplete() {
                    view.cancelRefreshDialog()
                }
            })
    }

    override fun removeItemData(position: Int) {
        model.removeHit(position)
        view.updateRemoveData(position)
    }

    override fun onItemClick(position: Int) {
        view.goToDetail(position)
    }

    override fun rxUnsuscribe() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun setView(view: NewsMVP.View) {
        this.view = view
    }
}