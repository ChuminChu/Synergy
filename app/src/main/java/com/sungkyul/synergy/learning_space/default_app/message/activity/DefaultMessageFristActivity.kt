package com.sungkyul.synergy.learning_space.default_app.message.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sungkyul.synergy.databinding.ActivityDefaultMessageBinding
import com.sungkyul.synergy.courses.default_app.message.DefaultMessageCourse2
import com.sungkyul.synergy.home.activity.MainActivity

import com.sungkyul.synergy.learning_space.default_app.message.adapter.MessageAdapter
import com.sungkyul.synergy.learning_space.default_app.message.adapter.MessageData
import com.sungkyul.synergy.learning_space.default_app.message.adapter.MyMessageData
import com.sungkyul.synergy.utils.AnimUtils
import com.sungkyul.synergy.utils.DateTimeUtils
import java.time.LocalDateTime

class DefaultMessageFristActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDefaultMessageBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            binding.eduScreen.course = DefaultMessageCourse2(binding.eduScreen)


            binding.eduScreen.setOnFinishedCourseListener {
                // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳이다.

                // DefaultAppActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            // 교육을 시작한다.
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

        val dateTime1 = LocalDateTime.of(2023, 12, 1, 0, 0)
        val dateTime2 = LocalDateTime.of(2023, 12, 2, 7, 42)
        val dateTime3 = LocalDateTime.of(2023, 12, 3, 15, 36)
        val now = LocalDateTime.now()

        val messageArray = ArrayList<MessageData>()


        val messages = binding.messages
        messages.layoutManager = LinearLayoutManager(binding.root.context)
        messages.adapter = MessageAdapter(messageArray)

        // 버튼의 터치 애니메이션을 초기화한다.
        AnimUtils.initTouchButtonAnimation(binding.goToTopMenuButton)
        AnimUtils.initTouchButtonAnimation(binding.callButton)
        AnimUtils.initTouchButtonAnimation(binding.searchButton)
        AnimUtils.initTouchButtonAnimation(binding.conversationSettingsButton)
        AnimUtils.initTouchButtonAnimation(binding.imageButton)
        AnimUtils.initTouchButtonAnimation(binding.cameraButton)
        AnimUtils.initTouchButtonAnimation(binding.plusButton)
        AnimUtils.initTouchButtonAnimation(binding.expandButton)
        AnimUtils.initTouchButtonAnimation(binding.emojiButton)
        AnimUtils.initTouchButtonAnimation(binding.recordButton)
        AnimUtils.initTouchButtonAnimation(binding.sendButton)

        // 버튼의 터치 리스너를 설정한다.
        binding.goToTopMenuButton.setOnTouchListener(onTouchGoToTopMenuButtonListener)
        binding.callButton.setOnTouchListener(onTouchCallButtonListener)
        binding.searchButton.setOnTouchListener(onTouchSearchButtonListener)
        binding.conversationSettingsButton.setOnTouchListener(onTouchConversationSettingsButtonListener)
        binding.imageButton.setOnTouchListener(onTouchImageButtonListener)
        binding.cameraButton.setOnTouchListener(onTouchCameraButtonListener)
        binding.plusButton.setOnTouchListener(onTouchPlusButtonListener)
        binding.expandButton.setOnTouchListener(onTouchExpandButtonListener)
        binding.emojiButton.setOnTouchListener(onTouchEmojiButtonListener)
        binding.recordButton.setOnTouchListener(onTouchRecordButtonListener)
        binding.sendButton.setOnTouchListener(onTouchSendButtonListener)

        binding.messageEditText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                binding.eduScreen.onAction("click_message_edit_text")
            }
        }
        binding.sendButton.setOnTouchListener { view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AnimUtils.startTouchDownButtonAnimation(this, view)
                }
                MotionEvent.ACTION_UP -> {
                    AnimUtils.startTouchUpButtonAnimation(this, view)

                    if(binding.eduScreen.onAction("click_send_button")) {
                        val adapter = messages.adapter as MessageAdapter

                        if (binding.messageEditText.text.toString().isNotEmpty()) {
                            messageArray.add(
                                MyMessageData(
                                    binding.messageEditText.text.toString(),
                                    "${now.format(DateTimeUtils.dateFormatter)} ${
                                        DateTimeUtils.getKoreanDayOfWeek(
                                            now
                                        )
                                    }",
                                    "${DateTimeUtils.getKoreanPeriod(now)} ${
                                        now.format(
                                            DateTimeUtils.timeFormatter
                                        )
                                    }"
                                )
                            )

                            // 아이템을 추가한다.
                            adapter.notifyItemRangeInserted(
                                messageArray.size - 1,
                                messageArray.size
                            )

                            binding.messageEditText.text.clear()
                        }

                        // 키보드를 숨긴다.
                        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(
                            binding.messageEditText.windowToken,
                            0
                        )
                    }

                    view.performClick()
                }
            }
            true
        }

        //메뉴 돌아가는 버튼
        binding.goToTopMenuButton.setOnTouchListener { view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AnimUtils.startTouchDownButtonAnimation(this, view)

                }
                MotionEvent.ACTION_UP -> {
                    AnimUtils.startTouchUpButtonAnimation(this, view)

                    if(binding.eduScreen.onAction("menu_button")) {
                        val intent = Intent(this, DefaultMessageChattingActivity::class.java)
                        intent.putExtra("from", "menu_button")
                        startActivity(intent)
                    }
                }
            }
            true
        }
    }

    private val onTouchGoToTopMenuButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()

            }
        }
        true
    }


    private val onTouchCallButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchSearchButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchConversationSettingsButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchImageButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchCameraButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchPlusButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchExpandButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchEmojiButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchRecordButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }

    private val onTouchSendButtonListener = View.OnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimUtils.startTouchDownButtonAnimation(this, view)
            }
            MotionEvent.ACTION_UP -> {
                AnimUtils.startTouchUpButtonAnimation(this, view)
                view.performClick()
            }
        }
        true
    }
}
