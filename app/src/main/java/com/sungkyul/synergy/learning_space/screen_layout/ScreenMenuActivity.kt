package com.sungkyul.synergy.learning_space.screen_layout

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.com.sungkyul.synergy.edu_courses.screen_layout.ScreenMenuCourse
import com.sungkyul.synergy.com.sungkyul.synergy.edu_courses.screen_layout.ScreenMenuCourse2
import com.sungkyul.synergy.com.sungkyul.synergy.edu_courses.screen_layout.ScreenMenuCourse3
import com.sungkyul.synergy.databinding.ActivityScreenMenuBinding
import com.sungkyul.synergy.learning_space.naver.activity.NaverActivity
import com.sungkyul.synergy.utils.edu.EduScreen

class ScreenMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenMenuBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            // 교육 코스를 지정한다.
            if(intent.getStringExtra("from") == "ScreenHomeActivity4") {
                binding.eduScreen.course = ScreenMenuCourse2(binding.eduScreen)
            }
            else if(intent.getStringExtra("from") == "ScreenHomeActivity5") {
                Log.i("두번째 네이버", "true")
                binding.eduScreen.course = ScreenMenuCourse3(binding.eduScreen)
            }
            else {
                binding.eduScreen.course = ScreenMenuCourse(binding.eduScreen)
            }

            // 교육 코스가 끝났을 때 발생하는 이벤트 리스너를 설정한다.
            binding.eduScreen.setOnFinishedCourseListener {
                //EduScreen.toTop(this, MainActivity::class.java)
            }

            // 교육을 시작한다.
            binding.eduScreen.start(this)
        }

        // 스마트폰의 이전 버튼을 누르면, 지정된 액티비티로 되돌아간다.
        EduScreen.navigateBackToTop(this, MainActivity::class.java)

        // 하단바 숨기기 설정
        hideSystemUI()

        binding.naverButton.setOnClickListener {
            if(binding.eduScreen.onAction("click_naver")) {

                val nintent = Intent(this, NaverActivity::class.java)

                if(intent.getStringExtra("from") == "ScreenHomeActivity5") {
                    Log.i("두 번째 네이버 진입", "true")
                    nintent.putExtra("from", "ScreenMenuActivity3")
                }

                startActivity(nintent)
            }
        }

        // 아이콘 레이아웃에 롱클릭 리스너 추가
        val playstoreIcon = findViewById<View>(R.id.playstore_icon)
        playstoreIcon.setOnLongClickListener { view ->
            if(binding.eduScreen.onAction("long_click_play_store_icon")) {
                val intent = Intent(this, ScreenMoveHomeActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    playstoreIcon,
                    "icon_transition"
                )

                // 드래그 앤 드롭 관련 데이터 설정
                val clipData = ClipData.newPlainText("icon_tag", view.tag.toString())
                val shadowBuilder = DragShadowBuilder(view)
                ViewCompat.startDragAndDrop(
                    view,
                    clipData,
                    shadowBuilder,
                    null,
                    View.DRAG_FLAG_GLOBAL
                )

                // startActivity를 호출하여 ScreenMoveHomeActivity로 전환
                startActivity(intent, options.toBundle())
            }
            true
        }

        // 투명한 뷰 터치 이벤트 처리
        findViewById<View>(R.id.transparent_view_2).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_UP -> {
                    if(binding.eduScreen.onAction("return_to_home_screen")) {
                        returnToHomeScreen()
                    }
                    true
                }
                else -> false
            }
        }

        findViewById<View>(R.id.transparent_view_3).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_UP -> {
                    if(binding.eduScreen.onAction("on_back_pressed")) {
                        onBackPressed()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    private fun returnToHomeScreen() {
        val intent = Intent(this, ScreenHomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.stay, R.anim.stay)
    }
}
