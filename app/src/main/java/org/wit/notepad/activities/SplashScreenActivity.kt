package org.wit.notepad.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.intentFor
import org.wit.notepad.R


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(intentFor<NotepadListActivity>())
            finish()}, 3000)
    }
}
