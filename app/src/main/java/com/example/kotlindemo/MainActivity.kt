package com.example.kotlindemo

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onTouchListener = View.OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> pressAnimation()
                MotionEvent.ACTION_UP -> normalAnimation()
                else -> return@OnTouchListener false
            }
        }
        btn_skill1.setOnTouchListener(onTouchListener)

        val animation = ScaleAnimation(1F, 0.7F, 1F, 0.7F, 0.5f, 0.5f)
        animation.duration = 200
        btn_skill2.setOnClickListener {
            btn_skill2.startAnimation(animation)
            val intent = Intent(this, PieViewActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
        }

        iv_kk.setBackgroundResource(R.drawable.iv_kk_frame_anim)
        val drawable = iv_kk.background as AnimationDrawable
        drawable.start()
    }

    private fun pressAnimation(): Boolean {
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.animation_btn_pressed)
        btn_skill1.startAnimation(pressAnim)
        return true
    }

    private fun normalAnimation(): Boolean {
        val normalAnim = AnimationUtils.loadAnimation(this, R.anim.animation_btn_normal)
        btn_skill1.startAnimation(normalAnim)
        return true
    }
}
