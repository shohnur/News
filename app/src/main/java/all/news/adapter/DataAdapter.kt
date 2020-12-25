package all.news.adapter

import all.news.R
import all.news.models.Article
import all.news.utils.onNewsSelected
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class DataAdapter : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface OnPageEnded {
        fun onPageEnded(size: Int)
    }

    var onPageEnded: OnPageEnded? = null

    var data = arrayListOf<Article>()
        set(value) {
            if (field.size == 0) {
                field.addAll(value)
                notifyDataSetChanged()
            } else {
                val n = field.size
                field.clear()
                field.addAll(value)
                notifyItemRangeInserted(n, value.size - n)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
        if (position == itemCount - 2)
            onPageEnded?.onPageEnded(itemCount)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data: Article) {
            itemView.title.text = data.title

            var time = ""
            for (i in 0..9)
                time += data.publishedAt[i]
            time += " "
            for (i in 11..15) {
                time += data.publishedAt[i]
            }

            itemView.time.text = time

            if (data.urlToImage == null) {
                itemView.image.visibility = View.GONE
            } else {
                Glide.with(itemView.image.context).load(data.urlToImage).into(itemView.image)
            }

            itemView.setOnClickListener {
                onNewsSelected?.onNewsSelected(data)
            }
        }
    }
}