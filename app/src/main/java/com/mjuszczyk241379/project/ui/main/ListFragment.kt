package com.mjuszczyk241379.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjuszczyk241379.project.R
import com.mjuszczyk241379.project.data.Password
import com.mjuszczyk241379.project.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageViewModel: PageViewModel
    private lateinit var passwords: List<Password>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1)

        binding.recordsList.adapter = adapter

        pageViewModel.readAllPasswordBasic.observe(viewLifecycleOwner, {
            passwords = it
            adapter.clear()
            adapter.addAll(it.map { item -> item.name} )
            adapter.notifyDataSetChanged()
        })

        binding.recordsList.setOnItemClickListener { _, view, position, _ ->
            val action = ListFragmentDirections.listToDetails()
            val id = passwords[position].id
            action.selectedId = id
            Navigation.findNavController(view).navigate(action)
        }

        binding.addItemBtn.setOnClickListener {
            val action = ListFragmentDirections.listToDetails().apply {
                selectedId = 0
            }
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }
}