package com.example.ingsoftappmobiles.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.models.Band
import com.example.ingsoftappmobiles.models.Musician
import com.example.ingsoftappmobiles.ui.adapters.BandsAdapter
import com.example.ingsoftappmobiles.ui.adapters.MusiciansAdapter
import com.example.ingsoftappmobiles.viewmodels.ArtistViewModel
import com.example.ingsoftappmobiles.databinding.FragmentArtistsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArtistsFragment : Fragment() {
    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewBand: RecyclerView
    private lateinit var recyclerViewMusician: RecyclerView
    private lateinit var viewModel: ArtistViewModel
    private var bandViewModelAdapter: BandsAdapter? = null
    private var musicianViewModelAdapter: MusiciansAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val view = binding.root
        bandViewModelAdapter = BandsAdapter()
        musicianViewModelAdapter = MusiciansAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        recyclerViewBand = binding.bandsRv
//        recyclerViewBand.layoutManager = LinearLayoutManager(context)
//        recyclerViewBand.adapter = bandViewModelAdapter

        recyclerViewMusician = binding.musiciansRv
        recyclerViewMusician.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerViewMusician.adapter = musicianViewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_artists)
        viewModel = ViewModelProvider(this, ArtistViewModel.Factory(activity.application)).get(ArtistViewModel::class.java)
        viewModel.bands.observe(viewLifecycleOwner, Observer<List<Band>> {
            it.apply {
                bandViewModelAdapter!!.bands = this
            }
        })

        viewModel.musicians.observe(viewLifecycleOwner, Observer<List<Musician>> {
            it.apply {
                musicianViewModelAdapter!!.musicians = this
            }
        })


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