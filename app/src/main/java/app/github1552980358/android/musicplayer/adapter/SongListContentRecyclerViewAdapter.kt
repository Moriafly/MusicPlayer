package app.github1552980358.android.musicplayer.adapter

import android.app.Service
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import app.github1552980358.android.musicplayer.R
import app.github1552980358.android.musicplayer.activity.SongListActivity
import app.github1552980358.android.musicplayer.base.AudioData
import app.github1552980358.android.musicplayer.base.Constant.Companion.CurrentSongList

/**
 * [SongListContentRecyclerViewAdapter]
 * @author  : 1552980328
 * @since   : 0.1
 * @date    : 2020/5/26
 * @time    : 21:32
 **/

class SongListContentRecyclerViewAdapter(
    private val activity: SongListActivity,
    private val songListTitle: String,
    list: ArrayList<AudioData>
):
    Adapter<SongListContentRecyclerViewAdapter.ViewHolder>() {
    
    private var songList = list
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            (activity.getSystemService(Service.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.view_song_list_content, parent, false)
        )
    }
    
    override fun getItemCount(): Int {
        return songList.size + 1
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (songList.isEmpty()) {
            holder.relativeLayoutRoot.visibility = View.GONE
            return
        }
        
        if (position == songList.size) {
            holder.imageButtonOpts.visibility = View.GONE
            holder.relativeLayoutRoot.isClickable = false
            return
        }
        
        // Reason refer to [app.github1552980358.android.musicplayer.adapter.ListFragmentRecyclerViewAdapter]
        // 原因请看 [app.github1552980358.android.musicplayer.adapter.ListFragmentRecyclerViewAdapter]
        holder.relativeLayoutRoot.visibility = View.VISIBLE
        
        holder.relativeLayoutRoot.setOnClickListener {
        
        }
        holder.textViewTitle.text = songList[position].title
        holder.textViewSubtitle.text = songList[position].artist
        holder.imageButtonOpts.setOnClickListener {
            activity.mediaControllerCompat.transportControls.prepareFromMediaId(
                songList[position].id,
                Bundle().apply { putString(CurrentSongList, songListTitle) }
            )
        }
        
    }
    
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val relativeLayoutRoot: RelativeLayout = view.findViewById(R.id.relativeLayoutRoot)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewSubtitle: TextView = view.findViewById(R.id.textViewSubtitle)
        val imageButtonOpts: ImageButton = view.findViewById(R.id.imageButtonOpts)
    }
    
}