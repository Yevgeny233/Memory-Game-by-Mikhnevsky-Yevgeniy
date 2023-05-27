package com.example.memorygamebymikhnevskyyevgeniy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.memorygamebymikhnevskyyevgeniy.R
import com.example.memorygamebymikhnevskyyevgeniy.database.FireBase
import com.example.memorygamebymikhnevskyyevgeniy.databinding.FragmentCheckBinding
import com.example.memorygamebymikhnevskyyevgeniy.domain.FireBaseViewModel
import com.example.memorygamebymikhnevskyyevgeniy.network.NetworkConnection

class CheckFragment : Fragment() {
    private lateinit var binding: FragmentCheckBinding
    private lateinit var viewModel: FireBaseViewModel
    private lateinit var networkConnection: NetworkConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckBinding.inflate(inflater, container, false)
        networkConnection = NetworkConnection(this.requireContext())
        viewModel = FireBaseViewModel(FireBase.instance)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            when (isConnected) {
                true -> {
                    binding.imageDisconnect.visibility = View.GONE
                    observingDB()
                }
                false -> binding.imageDisconnect.visibility = View.VISIBLE

            }
        })

    }

    private fun observingDB() {
        viewModel.valueBooleanFB.observe(viewLifecycleOwner, Observer {
            Log.w("TAG", "viewModel.valueBooleanFB = $it")
            if (it == false) {
                findNavController().navigate(R.id.memoryGameFragment)
            } else {
                findNavController().navigate(R.id.webViewFragment)
            }
        })
    }
}