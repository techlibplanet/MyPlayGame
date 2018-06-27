package com.example.mayank.myplaygame.quickplay

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mayank.myplaygame.Constants
import com.example.mayank.myplaygame.Constants.logD
import com.example.mayank.myplaygame.PlayGameApplication
import com.example.mayank.myplaygame.R
import com.example.mayank.myplaygame.dashboard.DashboardActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.games.Games
import kotlin.math.log

class QuickPlayActivity : AppCompatActivity() {

    private val TAG = QuickPlayActivity::class.java.simpleName
    private var mPlayerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_play)

        val account = getSignedInAccount()
        val playerClient = Games.getPlayersClient(this, account!!)
        playerClient.currentPlayer.addOnSuccessListener { player ->
            mPlayerId = player.playerId
            Log.d("Player ID", mPlayerId)
            Log.d("Display Name", player.displayName)
            logD(TAG, "Name : ${player.name}")


        }
    }

    private fun getSignedInAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(this)
    }
}
