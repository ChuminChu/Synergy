package com.sungkyul.synergy.learning_space.kakaotalk.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivityKakaoProfileDetailBinding
import com.sungkyul.synergy.courses.kakotalk.KakaoProfileCourse
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.learning_space.kakaotalk.data.profileItem

/** 카카오톡 프로필 디테일 액티비티 */
class KakaoProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKakaoProfileDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getSerializableExtra("profile_detail") as? profileItem

        receivedData?.let {
            // 받은 데이터로부터 필요한 정보 추출
            val name = receivedData.name
            val message = receivedData.message

            // 이름과 메시지를 보여줄 TextView 찾기
            val nameTextView = findViewById<TextView>(R.id.profileDetail_name)
            val messageTextView = findViewById<TextView>(R.id.profileDetail_message)

            // TextView에 이름과 메시지 설정
            nameTextView.text = "$name"
            messageTextView.text = "$message"
        }

        binding.startChattingLL.setOnClickListener {
            if(binding.eduScreen.onAction("click_chat11_button")) {
                val intent = Intent(this, KakaoChattingActivity::class.java)
                startActivity(intent)
            }
        }

        // 교육 추가
        binding.eduScreen.post {
            binding.eduScreen.course = KakaoProfileCourse(binding.eduScreen)

            binding.eduScreen.start(this)
        }

        // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // DefaultAppActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        })
    }
}
