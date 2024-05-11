package com.sungkyul.synergy.edu_courses

import android.view.Gravity
import com.sungkyul.synergy.utils.DisplayUtils
import com.sungkyul.synergy.utils.HandGestures
import com.sungkyul.synergy.utils.edu.EduCourse
import com.sungkyul.synergy.utils.edu.EduData
import com.sungkyul.synergy.utils.edu.EduHand
import com.sungkyul.synergy.utils.edu.EduScreen

data class DefaultPhoneCourse2(val eduScreen: EduScreen): EduCourse {
    override val eduDataList = ArrayList<EduData>()
    override val width = DisplayUtils.pxToDp(eduScreen.context, eduScreen.width.toFloat())
    override val height = DisplayUtils.pxToDp(eduScreen.context, eduScreen.height.toFloat())

    // 교육 코스를 만든다.
    init {
        eduDataList.add(EduData().apply {
            dialog.contentGravity = Gravity.CENTER
            dialog.top = 500.0f
            dialog.bottom = 100.0f
            dialog.start = 50.0f
            dialog.end = 50.0f
            cover.boxLeft = 150.0f
            cover.boxTop = 730.0f
            cover.boxRight = width-150.0f
            cover.boxBottom = height
            arrow.endTo = EduScreen.BOX
            dialog.visibility = true
            cover.visibility = true
            cover.boxVisibility = true
            cover.boxStrokeVisibility = true
            arrow.visibility = true
            dialog.contentText = "이 부분을 통해<br>방금 전화를 건<br>통화 목록을<br>확인할 수 있어요."
        })

        eduDataList.add(EduData().apply {
            dialog.contentText = "최근기록을 눌러주세요."
        })

        eduDataList.add(EduData().apply {
            dialog.visibility = false
            cover.visibility = false
            cover.boxVisibility = false
            cover.boxStrokeVisibility = false
            arrow.visibility = false
            action.id = "click_recent_history_button"
            hands.add(
                EduHand(
                    id = "tap",
                    x = 250.0f,
                    y = 670.0f,
                    rotation = 225.0f,
                    gesture = HandGestures.Companion::tapGesture
                )
            )
        })

        eduDataList.add(EduData().apply {
            dialog.top = 300.0f
            dialog.bottom = 300.0f
            dialog.start = 50.0f
            dialog.end = 50.0f
            dialog.visibility = true
            cover.visibility = true
            arrow.endTo = EduScreen.DIALOG
            dialog.contentText = "최근기록을 보면<br>전화 성공/실패 여부나<br>영상 통화 여부 등을<br>확인할 수 있어요."
        })

        eduDataList.add(EduData().apply {
            dialog.duration = 750
            arrow.duration = 750
            dialog.top = 500.0f
            dialog.bottom = 100.0f
            dialog.start = 50.0f
            dialog.end = 50.0f
            cover.boxLeft = width-120.0f
            cover.boxTop = 730.0f
            cover.boxRight = width-20.0f
            cover.boxBottom = height
            arrow.endTo = EduScreen.BOX
            dialog.visibility = true
            cover.visibility = true
            cover.boxVisibility = true
            cover.boxStrokeVisibility = true
            arrow.visibility = true
            dialog.contentText = "이 부분은<br>전화번호를 저장해서<br>편리하게 전화를<br>걸 수 있어요."
        })

        eduDataList.add(EduData().apply {
            dialog.contentText = "연락처를 눌러주세요."
        })

        eduDataList.add(EduData().apply {
            dialog.visibility = false
            cover.visibility = false
            cover.boxVisibility = false
            cover.boxStrokeVisibility = false
            arrow.visibility = false
            action.id = "click_contact_button"
            hands.add(
                EduHand(
                    id = "tap",
                    x = 250.0f,
                    y = 670.0f,
                    rotation = 225.0f,
                    gesture = HandGestures.Companion::tapGesture
                )
            )
        })
    }
}
