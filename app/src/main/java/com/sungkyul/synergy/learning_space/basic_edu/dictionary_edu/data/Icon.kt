package com.sungkyul.synergy.com.sungkyul.synergy.edu_space.basic_edu.dictionary_edu.data

/**Icon DATA (리사이클러뷰)*/
import java.io.Serializable

data class Icon(
    val iconImage: Int,
    val iconText: String,
    val iconDescription: String // iconDescription 속성 추가
) : Serializable