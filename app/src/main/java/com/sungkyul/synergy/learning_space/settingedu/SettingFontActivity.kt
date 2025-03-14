package com.sungkyul.synergy.learning_space.settingedu

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivitySetting2FontBinding
import com.sungkyul.synergy.courses.settings.SettingsFontCourse
import com.sungkyul.synergy.learning_space.EduCompletionActivity

class SettingFontActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetting2FontBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetting2FontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            binding.eduScreen.course = SettingsFontCourse(binding.eduScreen)

            binding.eduScreen.setOnFinishedCourseListener {
                // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳이다.

                val intent = Intent(binding.root.context, EduCompletionActivity::class.java)
                intent.putExtra("course", "settings")
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

        // 뒤로 가기 버튼 클릭 시 이벤트 처리
        binding.fontBackButton.setOnClickListener {
            // 새로운 액티비티 시작
            startActivity(Intent(this, SettingDisplayActivity::class.java))
        }

        // 시크바를 초기화한다.
        binding.textsizeSeekbar.progress = 0
        binding.mainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.main_text_size_0))

        binding.textsizeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val textSize = when (progress) {
                    0 -> resources.getDimension(R.dimen.main_text_size_0)
                    1 -> resources.getDimension(R.dimen.main_text_size_1)
                    2 -> resources.getDimension(R.dimen.main_text_size_2)
                    3 -> resources.getDimension(R.dimen.main_text_size_3)
                    4 -> resources.getDimension(R.dimen.main_text_size_4)
                    5 -> resources.getDimension(R.dimen.main_text_size_5)
                    6 -> resources.getDimension(R.dimen.main_text_size_6)
                    7 -> resources.getDimension(R.dimen.main_text_size_7)
                    else -> resources.getDimension(R.dimen.main_text_size_default)
                }

                binding.mainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

                //binding.eduScreen.onAction("change_text_size_bar", progress.toString())

                //이부분이안된다고 시크바가 7일때 넘어가야하는데 건들기만해도 왜 다음으로 넘어가냐고
                if (progress == 7 && fromUser) { // 사용자 조작에 의한 경우에만 실행
                    binding.eduScreen.onAction("change_text_size_bar")

                }

            }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })



    }
}
