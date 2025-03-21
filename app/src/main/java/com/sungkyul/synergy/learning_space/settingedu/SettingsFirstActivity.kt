package com.sungkyul.synergy.learning_space.settingedu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.courses.screen_layout.ScreenFirstCourse
import com.sungkyul.synergy.courses.settings.SettingsFirstCourse
import com.sungkyul.synergy.databinding.ActivitySettingsFirstBinding
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.learning_space.screen_layout.ScreenLockActivity

class SettingsFirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            binding.eduScreen.course = SettingsFirstCourse(binding.eduScreen)

            binding.iconImage.visibility = LinearLayout.INVISIBLE
            binding.sebookSmile.visibility = ImageView.VISIBLE
            binding.eduScreen.setOnNextListener {num->
                if(num==1) {
                    binding.iconImage.visibility = LinearLayout.VISIBLE
                    binding.sebookSmile.visibility = ImageView.INVISIBLE
                }
            }
            binding.eduScreen.setOnFinishedCourseListener {
                // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳이다.
                val intent = Intent(binding.root.context, SettingMainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            // 교육을 시작한다.
            binding.eduScreen.start(this)
        }

        // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // MainActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        })
    }
}
