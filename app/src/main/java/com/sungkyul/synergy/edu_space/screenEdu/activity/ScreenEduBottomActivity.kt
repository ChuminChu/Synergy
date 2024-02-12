package com.sungkyul.synergy.edu_space.screenEdu.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.R

class ScreenEduBottomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_nevigation)

        // 상단바 없애기
        supportActionBar?.hide()

        // 하단바 없애기
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // 다음 버튼 클릭 시
        val nextButton = findViewById<ImageView>(R.id.screenedu_nextbtn)
        nextButton.setOnClickListener {
            // ScreenEduRightToolActivity로 이동
            val intent = Intent(this, ScreenEduLeftBottomActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }
    }
}