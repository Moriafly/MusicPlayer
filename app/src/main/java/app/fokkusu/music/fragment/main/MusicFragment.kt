package app.fokkusu.music.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.fokkusu.music.R
import app.fokkusu.music.service.PlayService
import kotlinx.android.synthetic.main.fragment_music.listMusicView

/**
 * @File    : MusicFragment
 * @Author  : 1552980358
 * @Date    : 6 Oct 2019
 * @TIME    : 8:58 AM
 **/

class MusicFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listMusicView.setUpAdapterWithMusicList(PlayService.musicList, 0)
    }
}