package com.mjuszczyk241379.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjuszczyk241379.project.R
import com.mjuszczyk241379.project.databinding.FragmentSynchroniseBinding

class SynchroniseFragment : Fragment() {
    private var _binding: FragmentSynchroniseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSynchroniseBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SynchroniseFragment {
            return SynchroniseFragment()
        }
    }
}