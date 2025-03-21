package com.sungkyul.synergy.learning_space.default_app.gallery.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.FragmentDefaultGalleryStoryBinding
import com.sungkyul.synergy.learning_space.default_app.gallery.adapter.GalleryStoryAdapter
import com.sungkyul.synergy.learning_space.default_app.gallery.adapter.GalleryStoryData
import com.sungkyul.synergy.utils.edu.EduListener

class DefaultGalleryStoryFragment(private val eduListener: EduListener) : Fragment() {
    private lateinit var binding: FragmentDefaultGalleryStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultGalleryStoryBinding.inflate(inflater, container, false)

        // recyclerview에 들어갈 데이터 셋을 만든다.
        val dataSet = ArrayList<GalleryStoryData>()
        for(i in 1..10) {
            dataSet.add(
                GalleryStoryData(
                    R.drawable.todo_rect,
                    "2028-05-01"
                )
            )
        }

        val recyclerview = binding.recyclerview
        recyclerview.layoutManager = GridLayoutManager(binding.root.context, 2)
        recyclerview.adapter = GalleryStoryAdapter(dataSet)

        return binding.root
    }
}