package com.example.ingsoftappmobiles.ui.albums

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumDetailFragmentBinding
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.models.AlbumDetail
import com.example.ingsoftappmobiles.ui.adapters.AlbumDetailAdapter
import com.example.ingsoftappmobiles.viewmodels.AlbumDetailViewModel

class AlbumDetailFragment : Fragment() {

    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.AlbumDetailRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_album_detail_fragment)
        //val args: AlbumDetailFragmentArgs by navArgs()
        //Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, 100)).get(AlbumDetailViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.album = this
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}