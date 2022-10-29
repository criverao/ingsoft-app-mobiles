package com.example.ingsoftappmobiles.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ingsoftappmobiles.databinding.FragmentAlbumsBinding
import com.example.ingsoftappmobiles.viewmodels.AlbumsViewModel

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val albumsViewModel =
            ViewModelProvider(this).get(AlbumsViewModel::class.java)

        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAlbums
        albumsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}