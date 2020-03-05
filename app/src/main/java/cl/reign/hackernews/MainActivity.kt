package cl.reign.hackernews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import cl.reign.hackernews.di.HackerNewsApplication
import cl.reign.hackernews.helper.SwipeToDeleteCallback
import cl.reign.hackernews.news.NewsMVP
import cl.reign.hackernews.news.NewsViewModel
import cl.reign.hackernews.news.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), NewsMVP.View {

    @Inject
    lateinit var presenter: NewsMVP.Presenter

    lateinit var adapter: NewsListAdapter

    val news: ArrayList<NewsViewModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as HackerNewsApplication).component.inject(this)

        adapter = NewsListAdapter(presenter, news)

        recycler_view_news.layoutManager = LinearLayoutManager(this)
        recycler_view_news.adapter = adapter
        recycler_view_news.itemAnimator = DefaultItemAnimator()
        recycler_view_news.setHasFixedSize(true)

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                presenter.removeItemData(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_news)

        refresh.setOnRefreshListener(OnRefreshListener {
            news.clear()
            adapter.notifyDataSetChanged()
            presenter.loadData()
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.loadData()
    }

    override fun onStop() {
        super.onStop()
        presenter.rxUnsuscribe()
        news.clear()
        adapter.notifyDataSetChanged()
    }

    override fun updateData(newsViewModel: NewsViewModel) {
        news.add(newsViewModel)
        adapter.notifyItemInserted(news.size - 1)
    }

    override fun updateRemoveData(position: Int) {
        news.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun goToDetail(position: Int) {
        Log.i("MainActivity", news.get(position).storyUrl.toString())
        val intent = Intent(this, StoryActivity::class.java)
        intent.putExtra("storyUrl", news.get(position).storyUrl.toString())
        startActivity(intent)
    }

    override fun cancelRefreshDialog() {
        refresh.setRefreshing(false)
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
