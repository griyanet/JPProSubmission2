package com.example.jpsubmission2.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jpsubmission2.databinding.FragmentSplashPagerBinding
import com.example.jpsubmission2.splash.screen.screen1
import com.example.jpsubmission2.splash.screen.screen2
import com.example.jpsubmission2.splash.screen.screen3

class SplashPagerFragment : Fragment() {
    private var _binding: FragmentSplashPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashPagerBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf<Fragment>(
            screen1(),
            screen2(),
            screen3()
        )

        val adapter = SplashAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.splashPager.adapter = adapter
        return binding.root
    }
}