package com.example.ingsoftappmobiles.ui.artists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.databinding.ArtistDetailFragmentBinding
import com.example.ingsoftappmobiles.ui.adapters.ArtistDetailAdapter
import com.example.ingsoftappmobiles.viewmodels.ArtistDetailViewModel

class ArtistDetailFragment : Fragment() {

    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistDetailViewModel
    private var viewModelAdapter: ArtistDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.ArtistDetailRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        //activity.actionBar?.title = getString(R.string.title_artist_detail_fragment)
        val args: ArtistDetailFragmentArgs by navArgs()
        Log.d("Args", args.artistId.toString())
        Log.d("Args", args.tipo)

        viewModel = ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application, args.artistId, args.tipo))[ArtistDetailViewModel::class.java]
        viewModel.artist.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.artist = this
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
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