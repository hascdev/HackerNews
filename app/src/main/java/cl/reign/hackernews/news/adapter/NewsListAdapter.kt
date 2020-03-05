package cl.reign.hackernews.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import cl.reign.hackernews.R
import cl.reign.hackernews.news.NewsMVP
import cl.reign.hackernews.news.NewsViewModel
import kotlinx.android.synthetic.main.news_list_item.view.*


class NewsListAdapter(private val presenter: NewsMVP.Presenter, private val list: List<NewsViewModel>) : RecyclerView.Adapter<NewsListAdapter.NewsListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItemViewHolder {
        val listView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return NewsListItemViewHolder(listView)
    }

    override fun onBindViewHolder(holder: NewsListItemViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener(View.OnClickListener { presenter.onItemClick(position) })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewsListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsViewModel: NewsViewModel) {
            //containerView.
            itemView.title.text = newsViewModel.title
            itemView.description.text = "${newsViewModel.author} - ${newsViewModel.createdAt}"
        }

    }
}
