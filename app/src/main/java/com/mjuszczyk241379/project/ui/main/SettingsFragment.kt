package com.mjuszczyk241379.project.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mjuszczyk241379.project.MainActivity
import com.mjuszczyk241379.project.R
import com.mjuszczyk241379.project.data.Code
import com.mjuszczyk241379.project.data.Password
import com.mjuszczyk241379.project.databinding.ActivityLoginBinding
import com.mjuszczyk241379.project.databinding.FragmentPasswordBinding
import com.mjuszczyk241379.project.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeViewModel: CodeViewModel
    private lateinit var code: Code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeViewModel = ViewModelProvider(this).get(CodeViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        codeViewModel.code.observe(viewLifecycleOwner, {
            code = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.changePassBtn.setOnClickListener {
            val oldCode = binding.oldPassText.text.toString()
            if (oldCode == code.code){
                val newCode = binding.passNewText.text.toString()
                codeViewModel.update(Code(1, newCode))
                Toast.makeText(activity, R.string.correct_code_change, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, R.string.wrong_code, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}