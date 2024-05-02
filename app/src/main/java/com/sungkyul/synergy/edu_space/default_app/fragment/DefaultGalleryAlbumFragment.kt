package com.sungkyul.synergy.edu_space.default_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.FragmentDefaultGalleryAlbumBinding
import com.sungkyul.synergy.edu_space.default_app.adapter.GalleryAlbumAdapter
import com.sungkyul.synergy.edu_space.default_app.adapter.GalleryAlbumData
import com.sungkyul.synergy.utils.edu.EduListener

class DefaultGalleryAlbumFragment(private val eduListener: EduListener) : Fragment() {
    private lateinit var binding: FragmentDefaultGalleryAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultGalleryAlbumBinding.inflate(inflater, container, false)

        val dataSet = ArrayList<GalleryAlbumData>()
        for(i in 1..10) {
            dataSet.add(
                GalleryAlbumData(
                    R.drawable.sample_screenshot1,
                    "시너지",
                    123
                )
            )
        }

        val recyclerview = binding.recyclerview
        recyclerview.layoutManager = GridLayoutManager(binding.root.context, 3)
        recyclerview.adapter = GalleryAlbumAdapter(dataSet)

        return binding.root
    }
}
