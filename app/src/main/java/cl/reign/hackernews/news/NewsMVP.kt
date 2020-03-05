package cl.reign.hackernews.news

import io.reactivex.Observable

interface NewsMVP {

    interface View {

        fun updateData(newsViewModel: NewsViewModel)

        fun updateRemoveData(position: Int)

        fun goToDetail(position: Int)

        fun cancelRefreshDialog()

        fun showToast(msg: String)
    }

    interface Presenter {

        fun loadData()

        fun removeItemData(position: Int)

        fun onItemClick(position: Int)

        fun rxUnsuscribe()

        fun setView(view: View)
    }

    interface Model {

        fun result(): Observable<NewsViewModel>

        fun removeHit(index: Int)
    }
}