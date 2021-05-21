package com.example.jpsubmission2.splash.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jpsubmission2.R
import com.example.jpsubmission2.databinding.FragmentScreen3Binding

class screen3 : Fragment() {
    private var _binding: FragmentScreen3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScreen3Binding.inflate(inflater, container, false)

        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.action_splashPagerFragment_to_movies)
            onBoardingFinish()
        }

        return binding.root
    }

    private fun onBoardingFinish() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", false)
        editor.apply()
    }
}