package com.sungkyul.synergy.edu_space.move_edu.fragment

import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivityMoveEduBinding
import com.sungkyul.synergy.edu_space.move_edu.adapter.MoveEduAdapter
import com.sungkyul.synergy.edu_space.move_edu.data.Move

class MoveEduFragment : Fragment() {
    private var _binding: ActivityMoveEduBinding? = null
    private val binding get() = _binding!!
    private lateinit var moveAdapter: MoveEduAdapter
    private var standardSize_X = 0
    private var standardSize_Y = 0
    private var density = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMoveEduBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 화면의 기준 사이즈를 계산합니다.
        getStandardSize()

        // 텍스트 크기를 기준 사이즈를 이용해 설정합니다.
        binding.iconeduTool.textSize = (standardSize_X / 12).toFloat()
        binding.iconeduTool2.textSize = (standardSize_X / 20).toFloat()
        binding.searchEditText.textSize = (standardSize_X / 20).toFloat()

        val recyclerView: RecyclerView = binding.moveRv

        // RecyclerView에 레이아웃 매니저를 설정합니다.
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 아이콘과 검색어를 매칭하는 리스트
        val iconSearchDict = listOf(
            Pair(R.drawable.ic_move_touch, "터치"),
            Pair(R.drawable.ic_move_swipe, "스와이프"),
            Pair(R.drawable.ic_move_push, "꾹 누르기"),
            Pair(R.drawable.ic_move_drag, "드래그"),
            Pair(R.drawable.ic_move_size, "확대&축소"),
            Pair(R.drawable.ic_move_capture, "캡처"),
        )

        val moveList = ArrayList<Move>()
        for (i in iconSearchDict) {
            moveList.add(Move(i.first, i.second))
        }

        moveAdapter = MoveEduAdapter(requireContext(), moveList, standardSize_X)
        recyclerView.adapter = moveAdapter

        //검색창 기능
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val adapter = recyclerView.adapter as MoveEduAdapter

                // 모든 아이템들을 삭제한다.
                adapter.notifyItemRangeRemoved(0, moveList.size)

                // 자동 완성
                moveList.clear()
                if (s.toString().isNotEmpty()) {
                    for (i in iconSearchDict.filter { it.second.contains(s.toString()) }) {
                        moveList.add(Move(i.first, i.second))
                    }
                } else {
                    // 검색 창이 비어 있으면, 모든 아이템들을 채운다.
                    for (i in iconSearchDict) {
                        moveList.add(Move(i.first, i.second))
                    }
                }

                // 아이템들을 추가한다.
                adapter.notifyItemRangeInserted(0, moveList.size)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 디스플레이 사이즈를 반환하는 메서드
    private fun getScreenSize(): Point {
        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    // 기기의 해상도와 밀도를 기준으로 기준 사이즈를 계산하는 메서드
    private fun getStandardSize() {
        val screenSize = getScreenSize()
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        density = displayMetrics.density

        standardSize_X = (screenSize.x / density).toInt()
        standardSize_Y = (screenSize.y / density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
