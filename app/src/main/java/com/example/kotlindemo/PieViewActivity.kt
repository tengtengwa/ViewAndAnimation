package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PieViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_view)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }
}
