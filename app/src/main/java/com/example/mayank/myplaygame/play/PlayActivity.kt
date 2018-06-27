package com.example.mayank.myplaygame.play

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mayank.myplaygame.R
import com.example.mayank.myplaygame.multiplay.MultiPlayerActivity
import com.example.mayank.myplaygame.quickplay.QuickPlayActivity
import com.example.mayank.myplaygame.singleplay.SinglePlayActivity

class PlayActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = PlayActivity::class.java.simpleName

    private val CLICKABLES = intArrayOf(R.id.single_player_button, R.id.quick_play__button, R.id.multi_player_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        for (id in CLICKABLES){
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.single_player_button ->{
                val intent = Intent(this, SinglePlayActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.quick_play__button ->{
                val intent = Intent(this, QuickPlayActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.multi_player_button ->{
                val intent = Intent(this, MultiPlayerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}
