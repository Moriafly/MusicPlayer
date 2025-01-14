package sakuraba.saki.player.music.ui.home.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import lib.github1552980358.ktExtension.android.content.intent
import lib.github1552980358.ktExtension.android.graphics.toBitmap
import sakuraba.saki.player.music.AudioDetailActivity
import sakuraba.saki.player.music.R
import sakuraba.saki.player.music.service.util.AudioInfo
import sakuraba.saki.player.music.util.MainActivityInterface
import sakuraba.saki.player.music.util.Constants.EXTRAS_DATA
import sakuraba.saki.player.music.util.ViewHolderUtil.bindHolder

class RecyclerViewAdapterUtil(private val fragment: Fragment, private val activityInterface: MainActivityInterface, selection: (pos: Int) -> Unit) {
    
    private class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image_view)
        val title: TextView = view.findViewById(R.id.text_view_title)
        val summary: TextView = view.findViewById(R.id.text_view_summary)
        val background: RelativeLayout = view.findViewById(R.id.relative_layout_root)
    }
    
    private inner class RecyclerViewAdapter(var audioInfoList: List<AudioInfo>, private val selection: (pos: Int) -> Unit): RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_home_recycler_view, parent, false))
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindHolder {
            val audioInfo = audioInfoList[position]
            audioInfo.apply {
                title.text = audioTitle
                @Suppress("SetTextI18n")
                summary.text = "$audioArtist - $audioAlbum"
                image.setImageBitmap(
                    activityInterface.audioBitmapMap[audioId]
                        ?: activityInterface.bitmapMap[audioAlbumId]
                        ?: ContextCompat.getDrawable(background.context, R.drawable.ic_music)?.toBitmap()
                )
            }
            background.setOnClickListener { selection(position) }
            background.setOnLongClickListener {
                image.transitionName = "${audioInfo.audioId}_image"
                fragment.startActivity(
                    intent(fragment.requireContext(), AudioDetailActivity::class.java) { putExtra(EXTRAS_DATA, audioInfo) },
                    ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.requireActivity(), image, image.transitionName).toBundle()
                )
                return@setOnLongClickListener true
            }
        }
        
        override fun getItemCount() = audioInfoList.size
        
    }
    
    private val adapter = RecyclerViewAdapter(activityInterface.audioInfoList, selection)
    
    fun setAdapterToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    @Suppress("NotifyDataSetChanged")
    fun notifyDataSetChanged() {
        adapter.audioInfoList = activityInterface.audioInfoList
        adapter.notifyDataSetChanged()
    }
    
}