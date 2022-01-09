package com.mjuszczyk241379.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjuszczyk241379.project.R
import com.mjuszczyk241379.project.data.Password
import com.mjuszczyk241379.project.databinding.FragmentDetailsBinding
import java.util.*


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageViewModel: PageViewModel
    private lateinit var selectedItem: Password
    private var closing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        val id = DetailsFragmentArgs.fromBundle(requireArguments()).let {
            it.selectedId
        }

        if (id == 0) {
            selectedItem = Password(0, "", "", "", "", null, null)
            updateDisplayData(selectedItem)
        } else {
            pageViewModel.readPasswordById(id).observe(viewLifecycleOwner, {
                selectedItem = it
                updateDisplayData(it)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.detailsBackBtn.setOnClickListener {
            closing(it)
        }

        binding.detailsSaveBtn.setOnClickListener {
            val item = Password(
                selectedItem.id,
                binding.recordName.text.toString(),
                binding.recordLogin.text.toString(),
                binding.recordPassowrd.text.toString(),
                binding.recordNote.text.toString(),
                System.currentTimeMillis(),
                selectedItem._id
            )

            if (selectedItem.id == 0) {
                pageViewModel.insert(item)
            } else {
                pageViewModel.update(item)
            }

            Toast.makeText(activity, R.string.save_confirm_message, Toast.LENGTH_SHORT).show()

            closing(it)
        }

        binding.detailsDeleteBtn.setOnClickListener {
            pageViewModel.delete(selectedItem)
            Toast.makeText(activity, R.string.delete_confirm_message, Toast.LENGTH_SHORT).show()

            closing(it)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        if (!closing) {
            closing = false
            Navigation.findNavController(requireView()).navigate(R.id.detailsToList)
        }
    }

    private fun closing(view: View) {
        Navigation.findNavController(view).navigate(R.id.detailsToList)
        closing = true
    }

    private fun updateDisplayData(password: Password){
        binding.recordName.setText(password?.name)
        binding.recordLogin.setText(password?.login)
        binding.recordPassowrd.setText(password?.password)
        binding.recordNote.setText(password?.note)
    }

    companion object {
        @JvmStatic
        fun newInstance(): DetailsFragment {
            return DetailsFragment()
        }
    }
}