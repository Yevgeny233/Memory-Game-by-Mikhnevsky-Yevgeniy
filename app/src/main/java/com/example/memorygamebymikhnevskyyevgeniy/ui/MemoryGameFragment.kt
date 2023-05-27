package com.example.memorygamebymikhnevskyyevgeniy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.memorygamebymikhnevskyyevgeniy.databinding.FragmentMemoryGameBinding
import com.example.memorygamebymikhnevskyyevgeniy.ui.adapters.GridAdapter

class MemoryGameFragment : Fragment() {
    private lateinit var binding: FragmentMemoryGameBinding
    private lateinit var gridAdapter: GridAdapter
    private lateinit var gridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMemoryGameBinding.inflate(inflater, container, false)

        gridView = binding.grid
        gridView.numColumns = 6
        gridView.isEnabled = true

        gridAdapter = GridAdapter(this.requireContext(), 6, 4)
        gridView.adapter = gridAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            gridAdapter.checkOpenCells()
            gridAdapter.openCell(position)
            if (gridAdapter.checkGameOver()) {
                Toast.makeText(
                    context, "Game over", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

