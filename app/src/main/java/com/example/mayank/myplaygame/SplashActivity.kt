package com.example.mayank.myplaygame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.daimajia.androidanimations.library.Techniques
import com.viksaa.sssplash.lib.activity.AwesomeSplash
import com.viksaa.sssplash.lib.cnst.Flags
import com.viksaa.sssplash.lib.model.ConfigSplash

class SplashActivity : AwesomeSplash() {

    private val TAG = SplashActivity::class.java.simpleName


    override fun initSplash(configSplash: ConfigSplash) {
        if (!isTaskRoot) {
            val intent = intent
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                Log.w(TAG, "Main Activity is not the root.  Finishing Main Activity instead of launching.")
                finish()
                return
            }

            //Customize Circular Reveal
            configSplash.backgroundColor = R.color.colorPrimary //any color you want form colors.xml
            configSplash.animCircularRevealDuration = 1000 //int ms
            configSplash.revealFlagX = Flags.REVEAL_RIGHT  //or Flags.REVEAL_LEFT
            configSplash.revealFlagY = Flags.REVEAL_BOTTOM //or Flags.REVEAL_TOP

            //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

            //Customize Logo
            configSplash.logoSplash = R.drawable.cglogo //or any other drawable
            configSplash.animLogoSplashDuration = 2000 //int ms
            configSplash.animLogoSplashTechnique = Techniques.FadeIn //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


            //Customize Path
            // configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
            configSplash.originalHeight = 400 //in relation to your svg (path) resource
            configSplash.originalWidth = 400 //in relation to your svg (path) resource
            configSplash.animPathStrokeDrawingDuration = 2000
            configSplash.pathSplashStrokeSize = 3 //I advise value be <5
            configSplash.pathSplashStrokeColor = R.color.colorAccent //any color you want form colors.xml
            configSplash.animPathFillingDuration = 2000
            configSplash.pathSplashFillColor = R.color.white //path object filling color


            //Customize Title
            configSplash.titleSplash = resources.getString(R.string.alchemy_games)
            configSplash.titleTextColor = R.color.white
            configSplash.titleTextSize = 20f //float value
            configSplash.animTitleDuration = 0
            configSplash.animTitleTechnique = Techniques.FadeIn
            configSplash.titleFont = "fonts/grobold.ttf" //provide string to your font located in assets/fonts/
        }
    }

    override fun animationsFinished() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
