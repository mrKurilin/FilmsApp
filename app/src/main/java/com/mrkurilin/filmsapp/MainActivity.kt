package com.mrkurilin.filmsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container_view,
            SignInFragment()
        ).commit()
    }
}