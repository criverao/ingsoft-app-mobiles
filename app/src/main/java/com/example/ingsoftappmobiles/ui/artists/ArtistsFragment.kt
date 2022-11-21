package com.example.ingsoftappmobiles.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.viewmodels.ArtistViewModel
import com.example.ingsoftappmobiles.databinding.FragmentArtistsBinding
import com.example.ingsoftappmobiles.ui.adapters.ArtistsAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArtistsFragment : Fragment() {
    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistViewModel
    private var viewModelAdapter: ArtistsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        recyclerViewBand = binding.bandsRv
//        recyclerViewBand.layoutManager = LinearLayoutManager(context)
//        recyclerViewBand.adapter = bandViewModelAdapter

        recyclerView = binding.artistsRv
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_artists)
        viewModel = ViewModelProvider(this, ArtistViewModel.Factory(activity.application)).get(ArtistViewModel::class.java)
        viewModel.artists.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.artists = this
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