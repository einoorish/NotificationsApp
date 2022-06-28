package com.example.notificationsapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.notificationsapp.R
import com.example.notificationsapp.notification.NotificationBuilder
import com.example.notificationsapp.databinding.FragmentMainBinding
import com.example.notificationsapp.util.CounterListener


class BaseFragment : Fragment(R.layout.fragment_main), CounterListener {

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }

    private lateinit var binding: FragmentMainBinding

    private val viewModel = MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        binding.incButton.setOnClickListener{onIncClick()}
        binding.decButton.setOnClickListener{onDecClick()}

        binding.btn.setOnClickListener {
            NotificationBuilder().showNotification(requireContext(), binding.itemCounterValue.text.toString().toInt())
        }

        viewModel.getCount.observe(viewLifecycleOwner) {
            binding.itemCounterValue.text = it.toString()

            if(it == 0)
                binding.decButton.visibility = View.GONE
            else
                binding.decButton.visibility = View.VISIBLE
        }
    }

    override fun onIncClick() {
        viewModel.incCount()
        parentFragmentManager.commit {
            replace<BaseFragment>(R.id.container)
            addToBackStack(null)
        }
    }

    override fun onDecClick() {
        if(binding.itemCounterValue.text.toString().toInt() > 0){
            viewModel.decCount()
            dispatcher.onBackPressed()
        }
    }
}
