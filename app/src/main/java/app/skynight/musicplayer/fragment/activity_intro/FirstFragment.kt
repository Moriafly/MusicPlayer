package app.skynight.musicplayer.fragment.activity_intro

import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.skynight.musicplayer.R
import app.skynight.musicplayer.util.UnitUtil.Companion.getPx

/**
 * @FILE:   FirstFragment
 * @AUTHOR: 1552980358
 * @DATE:   22 Jul 2019
 * @TIME:   10:53 AM
 **/

class FirstFragment : Fragment() {
    /*
        fun createView(): View {
            return
        }
    */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return RelativeLayout(context!!).apply {
            //fitsSystemWindows = true
            background =
                ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL

                addView(TextView(context).apply {
                    setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    text = "欢迎来到"
                    textSize = getPx(12f)
                }, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                })
                addView(TextView(context).apply {
                    text = "~  狸歌  ~"
                    textSize = getPx(25f)
                    setTextColor(ContextCompat.getColor(context, android.R.color.white))
                }, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                })
                addView(TextView(context).apply {
                    text = "为您呈现"
                    textSize = getPx(12f)
                    paint.flags = Paint.UNDERLINE_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, android.R.color.white))
                }, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                })
            }, RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                addRule(RelativeLayout.CENTER_VERTICAL)
                addRule(RelativeLayout.CENTER_HORIZONTAL)
            })
        }

        //return inflater.inflate(R.layout.fragment_first, container, false)
    }
/*
    override fun onDetach() {
        val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
        childFragmentManager.isAccessible = true
        childFragmentManager.set(this, null)
        super.onDetach()
    }
    */
}