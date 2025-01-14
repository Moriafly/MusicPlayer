package sakuraba.saki.player.music.ui.audioDetail.lyricView.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sakuraba.saki.player.music.R
import sakuraba.saki.player.music.util.LyricUtil.timeStr
import sakuraba.saki.player.music.util.ViewHolderUtil.bindHolder

class RecyclerViewAdapterUtil(recyclerView: RecyclerView) {

    private class RecyclerViewHolder(view: View): ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.text_view_title)
        val textViewSummary: TextView = view.findViewById(R.id.text_view_summary)
    }

    val lyricList = arrayListOf<String>()
    val timeList = arrayListOf<Long>()

    private inner class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_lyric_view_recycler_view, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) = holder.bindHolder {
            textViewTitle.text = timeList[position].timeStr
            textViewSummary.text = lyricList[position]
        }

        override fun getItemCount() = lyricList.size
    }

    private val adapter = RecyclerViewAdapter()

    init {
        recyclerView.adapter = adapter
    }

    @Suppress("NotifyDatasetChanged")
    fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

}