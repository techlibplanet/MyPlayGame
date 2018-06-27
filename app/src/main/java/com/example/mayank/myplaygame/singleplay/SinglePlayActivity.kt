package com.example.mayank.myplaygame.singleplay

import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import com.example.mayank.myplaygame.R
import kotlinx.android.synthetic.main.single_play_detail_screen.*

class SinglePlayActivity : AppCompatActivity(), SinglePlayerQuizFragment.OnFragmentInteractionListener, SinglePlayDetailFragment.OnFragmentInteractionListener, SinglePlayResultFragment.OnFragmentInteractionListener {
    
    private val TAG = SinglePlayActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_play)

        val singlePlay = SinglePlayDetailFragment()
        switchToFragment(singlePlay)

    }

    // Switch UI to the given fragment
    private fun switchToFragment(newFrag: Fragment) {
        supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, newFrag)?.commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

}
