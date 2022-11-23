package sparespark.worldwide.news.ui.articlelist.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import sparespark.worldwide.news.data.db.dao.ArticleEntry
import sparespark.worldwide.news.ui.TAG
import java.util.*

class ArticleListAdapter(
    var itemList: ArrayList<ArticleEntry>,
    private val listener: (ArticleEntry) -> Unit
) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>(), Filterable {

    private var filteredList: ArrayList<ArticleEntry>? = ArrayList(itemList)

    inner class ArticleViewHolder(val binding: sparespark.worldwide.news.databinding.ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = sparespark.worldwide.news.databinding.ItemArticleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = itemList[position]
        with(holder) {
            binding.article = item
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getFilter(): Filter = filter

    private val filter: Filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ArticleEntry> = ArrayList()

            if (constraint.isEmpty()) {
                filteredList.addAll(this@ArticleListAdapter.filteredList!!)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in this@ArticleListAdapter.filteredList!!) {
                    if (item.title?.toLowerCase()?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    } else if (item.description?.toLowerCase()?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            try {
                /*
                *
                * Update later..
                * */
                if (results != null) {
                    itemList.clear()
                    itemList.addAll(results.values as Collection<ArticleEntry>)
                    notifyDataSetChanged()
                }
            } catch (ex: Exception) {
                Log.d(TAG, "publishResults: ${ex.message}")
            }
        }
    }
}