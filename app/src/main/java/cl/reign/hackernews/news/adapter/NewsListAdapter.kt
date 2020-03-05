package cl.reign.hackernews.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.reign.hackernews.R
import cl.reign.hackernews.news.NewsMVP
import cl.reign.hackernews.news.NewsViewModel
import kotlinx.android.synthetic.main.news_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*


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

        private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())

        fun bind(newsViewModel: NewsViewModel) {

            itemView.title.text = newsViewModel.title
            itemView.description.text = "${newsViewModel.author} - ${timelapse(newsViewModel.createdAt)}"
        }

        fun timelapse(createdAt: String?): String {

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
            val millis = sdf.parse(createdAt).time

            val currentDate: Calendar = Calendar.getInstance()

            val diff: Long = currentDate.timeInMillis - millis
            val diffSeconds = diff / 1000
            println("diffSeconds: $diffSeconds")
            val diffMinutes = diffSeconds / 60
            println("diffMinutes: $diffMinutes")
            if (diffMinutes == 0L) {
                return diffSeconds.toString() + "s"
            } else {
                if (diffMinutes <= 60) {
                    return diffMinutes.toString() + "m"
                } else {
                    val diffHours = diffMinutes / 60
                    if (diffHours <= 24) {
                        return diffHours.toString() + "h"
                    } else {
                        return (diffHours / 24).toString() + "d"
                    }
                }
            }
        }

    }
}
