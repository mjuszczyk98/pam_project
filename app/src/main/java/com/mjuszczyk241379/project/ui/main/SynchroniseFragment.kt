package com.mjuszczyk241379.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mjuszczyk241379.project.R
import com.mjuszczyk241379.project.data.Password
import com.mjuszczyk241379.project.data.SyncDate
import com.mjuszczyk241379.project.databinding.FragmentSynchroniseBinding
import java.util.*

class SynchroniseFragment : Fragment() {
    private var _binding: FragmentSynchroniseBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageViewModel: PageViewModel
    private lateinit var selectedItem: SyncDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        pageViewModel.lastSyncDate.observe(viewLifecycleOwner, {
            selectedItem = it
            if (it?.syncDate != null) {
                binding.lastUpdateText.text = Date(it.syncDate).toString()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSynchroniseBinding.inflate(inflater, container, false)

        binding.synchroniseBtn.setOnClickListener {
            pageViewModel.syncPasswords(selectedItem)

            Toast.makeText(activity, R.string.sync_confirm_message, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): SynchroniseFragment {
            return SynchroniseFragment()
        }
    }
}