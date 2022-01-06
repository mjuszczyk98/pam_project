package com.mjuszczyk241379.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mjuszczyk241379.project.data.AppDatabase
import com.mjuszczyk241379.project.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): PasswordFragment {
            return PasswordFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}