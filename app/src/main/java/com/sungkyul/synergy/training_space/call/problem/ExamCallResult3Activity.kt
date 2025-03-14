package com.sungkyul.synergy.training_space.call.problem

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivityExamCallResult1Binding
import com.sungkyul.synergy.databinding.ActivityExamCallResult3Binding
import com.sungkyul.synergy.databinding.ActivityPracticeCall3Binding
import com.sungkyul.synergy.databinding.FragmentDefaultPhoneContactBinding
import com.sungkyul.synergy.learning_space.default_app.phone.adapter.ContactData
import com.sungkyul.synergy.learning_space.default_app.phone.fragment.DefaultPhoneRecentHistoryFragment
import com.sungkyul.synergy.training_space.call.DefaultPhoneContact2Fragment
import com.sungkyul.synergy.training_space.call.DefaultPhoneKeypadFragment2
import com.sungkyul.synergy.training_space.call.result.CallResultActivity
import com.sungkyul.synergy.utils.GalaxyButton

class ExamCallResult3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityExamCallResult3Binding
    private lateinit var keypadFragment: Fragment
    private lateinit var recentHistoryFragment: Fragment
    private lateinit var contactFragment: Fragment

    private lateinit var timer: CountDownTimer
    private var isTimerRunning = false
    private var remainingTimeInMillis: Long = 30000
    private var pausedTimeInMillis: Long = 0 // 타이머가 일시정지된 시간
    private var success: Boolean = false // 성공 여부를 나타내는 변수 추가


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityExamCallResult3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //startTimer()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, CallResultActivity::class.java)
            startActivity(intent)
        }, 3000)


        // Fragments
        recentHistoryFragment = DefaultPhoneRecentHistoryFragment()

        // 초기 메인 레이아웃 배경 설정
        //   replaceFragment(contactFragment)
        val contacts = listOf(
            "시너지, 010-1111-1111",
            "김밥천국, 02-999-9999"
        )

        val contactListContainer = findViewById<LinearLayout>(R.id.contact_list_container)

        for (contact in contacts) {
            val textView = TextView(this)
            textView.text = contact
            textView.textSize = 18f
            textView.setPadding(8, 8, 8, 8)
            contactListContainer.addView(textView)
        }
        // 타이머 초기화
        timer = object : CountDownTimer(remainingTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                val secondsLeft = millisUntilFinished / 1000
                binding.timerTextView.text = secondsLeft.toString() // 초를 텍스트뷰에 표시
            }

            override fun onFinish() {
                binding.timerTextView.text = "0" // 타이머 종료 시 "0"으로 표시
            }
        }

        // 문제보기 클릭 시 다이얼로그 띄우기
        binding.problemText.setOnClickListener {
            showProblemDialog()
        }

        timer.start() // 액티비티가 생성되면 타이머 시작
        isTimerRunning = true
    }

    override fun onPause() {
        super.onPause()
        pausedTimeInMillis = remainingTimeInMillis
        timer.cancel() // 타이머를 취소하여 불필요한 시간 감소를 막음
        isTimerRunning = false
    }

    override fun onResume() {
        super.onResume()
        if (!isTimerRunning) {
            startTimer(pausedTimeInMillis)
        }
    }

    private fun startTimer(startTimeInMillis: Long = remainingTimeInMillis) {
        timer = object : CountDownTimer(startTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                val secondsLeft = millisUntilFinished / 1000
                findViewById<TextView>(R.id.timerTextView).text = secondsLeft.toString()
            }

            override fun onFinish() {
                if (!success) { // 성공하지 않았을 때만 실패로 저장
                    findViewById<TextView>(R.id.timerTextView).text = "0"
                    // saveResult(false) // 실패 결과 저장
                }
                // 타이머가 종료되면 자동으로 실패 처리됨
                //  returnToHomeScreen()
            }
        }

        // 문제보기 클릭 시 다이얼로그 띄우기
        findViewById<TextView>(R.id.problemText).setOnClickListener {
            showProblemDialog()
        }

        timer.start() // 액티비티가 생성되면 타이머 시작
        isTimerRunning = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showProblemDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        // 커스텀 레이아웃을 설정하기 위한 레이아웃 인플레이터
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialoglayout, null)

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        // 다이얼로그 메시지 텍스트뷰 설정
        val numberTextView = dialogView.findViewById<TextView>(R.id.dialogNumber)
        numberTextView.text = "문제 3."

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        messageTextView.text = "연락처에 다음과 같이 저장하시오.\n이름: 시너지, \n전화번호: 010-1111-1111."
        messageTextView.textSize = 20f // 글씨 크기 설정

        // 확인 버튼 설정
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기

            // ///////saveResult(true) // 문제 풀이 성공으로 표시
            // returnToHomeScreen() // 홈 화면으로 이동
        }

        alertDialog.show()

        // 다이얼로그가 나타나면 타이머 멈춤
        timer.cancel()
        isTimerRunning = false
    }

    private fun returnToHomeScreen() {
        val intent = Intent(this, ExamCallProblem2Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.stay, R.anim.stay)
    }


//    private fun saveResult(isSuccess: Boolean) {
//        val sharedPreferences =
//            getSharedPreferences("PracticeRecentlyDefaultPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean("move_app_success", isSuccess)
//        editor.apply()
//    }


}
