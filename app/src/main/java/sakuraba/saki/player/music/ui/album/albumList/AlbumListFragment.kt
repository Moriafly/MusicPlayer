package sakuraba.saki.player.music.ui.album.albumList

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import lib.github1552980358.ktExtension.android.graphics.toBitmap
import sakuraba.saki.player.music.R
import sakuraba.saki.player.music.database.AudioDatabaseHelper
import sakuraba.saki.player.music.databinding.FragmentAlbumListBinding
import sakuraba.saki.player.music.ui.album.albumList.util.RecyclerViewAdapterUtil
import sakuraba.saki.player.music.util.Constants.EXTRAS_DATA
import sakuraba.saki.player.music.util.MediaAlbum
import java.util.concurrent.TimeUnit
import sakuraba.saki.player.music.base.BaseMainFragment
import sakuraba.saki.player.music.util.BitmapUtil.loadAlbumArtRaw
import sakuraba.saki.player.music.util.CoroutineUtil.io
import sakuraba.saki.player.music.util.CoroutineUtil.ui

class AlbumListFragment: BaseMainFragment() {
    
    private var _fragmentAlbumListBinding: FragmentAlbumListBinding? = null
    private val fragmentAlbumList get() = _fragmentAlbumListBinding!!
    private lateinit var behavior: BottomSheetBehavior<RecyclerView>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterUtil
    private lateinit var mediaAlbum: MediaAlbum
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _fragmentAlbumListBinding = FragmentAlbumListBinding.inflate(inflater)
        mediaAlbum = requireArguments().getSerializable(EXTRAS_DATA) as MediaAlbum
        fragmentAlbumList.imageView.transitionName = "${mediaAlbum.albumId}_image"
        fragmentAlbumList.textViewTitle.transitionName = "${mediaAlbum.albumId}_text"

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        return fragmentAlbumList.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        fragmentAlbumList.imageView.setImageBitmap(requireContext().loadAlbumArtRaw(mediaAlbum.albumId) ?:  ContextCompat.getDrawable(requireContext(), R.drawable.ic_music)?.toBitmap())
        fragmentAlbumList.textViewTitle.text = mediaAlbum.title
    
        behavior = BottomSheetBehavior.from(fragmentAlbumList.recyclerView)
        
        recyclerViewAdapter = RecyclerViewAdapterUtil(fragmentAlbumList.recyclerView) { pos ->
            activityInterface.onFragmentListItemClick(pos, recyclerViewAdapter.audioInfoList[pos], recyclerViewAdapter.audioInfoList)
        }
        
        io {
            AudioDatabaseHelper(requireContext()).queryAudioForMediaAlbum(recyclerViewAdapter.audioInfoList, mediaAlbum.albumId)
            ui {
                behavior.peekHeight = fragmentAlbumList.root.height - resources.getDimensionPixelSize(R.dimen.album_list_header_height)
                recyclerViewAdapter.notifyDataUpdated()
                behavior.state = STATE_COLLAPSED
            }
        }
    }
    
}