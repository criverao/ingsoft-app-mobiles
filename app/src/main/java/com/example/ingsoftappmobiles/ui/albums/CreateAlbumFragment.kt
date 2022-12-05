package com.example.ingsoftappmobiles.ui.albums


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.ingsoftappmobiles.database.dao.VinylRoomDatabase
import com.example.ingsoftappmobiles.databinding.AlbumCreateBinding
import com.example.ingsoftappmobiles.models.Album
import com.example.ingsoftappmobiles.repositories.AlbumsRepository
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class CreateAlbumFragment : Fragment() {

    //private var _binding: FragmentAlbumListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    //private lateinit var recyclerView: RecyclerView
    //private lateinit var viewModel: AlbumsViewModel
    //private var viewModelAdapter: AlbumsAdapter? = null

    private  lateinit var albumsRepository:AlbumsRepository
    private var _binding: AlbumCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumCreateBinding.inflate(inflater, container, false)

        //viewModelAdapter = AlbumsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //recyclerView = binding.
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //recyclerView.adapter = viewModelAdapter
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val adapterGenre = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, arrayListOf("Classical", "Salsa", "Rock", "Folk")
        )
        _binding?.txtAlbumGenre?.setAdapter(adapterGenre)

        val adapterDisc = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, arrayListOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        )
        _binding?.txtAlbumDisc?.setAdapter(adapterDisc)


        _binding?.albumCreateButton?.setOnClickListener {

            val nameTxt : TextInputEditText? = _binding?.txtAlbumName
            val imageTxt : TextInputEditText? = _binding?.txtAlbumImage
            val dateTxt : DatePicker? = _binding?.txtAlbumDate
            val descTxt : TextInputEditText? = _binding?.txtAlbumDesc
            val discTxt : AutoCompleteTextView? = _binding?.txtAlbumDisc
            val genreTxt : AutoCompleteTextView? = _binding?.txtAlbumGenre

            val year = dateTxt?.year
            val month = dateTxt?.month
            val day = dateTxt?.dayOfMonth

            val calendar:Calendar = Calendar.getInstance()
            if (year != null) {
                if (month != null) {
                    if (day != null) {
                        calendar.set(year, month, day)
                    }
                }
            }

            val format = SimpleDateFormat("yyyy-MM-dd")
            val strDate:String = format.format(calendar.time)
            strDate.plus("T00:00:00-05:00")


            val album = Album(0, nameTxt?.text.toString(), imageTxt?.text.toString(), strDate,
                descTxt?.text.toString(),genreTxt?.text.toString(),discTxt?.text.toString(),
                year.toString(), " ")

            albumsRepository = AlbumsRepository(
                activity.application,
                VinylRoomDatabase.getDatabase(activity.applicationContext).albumsDao()
            )
            albumsRepository.createAlbum(album,{
                Toast.makeText(context, "El album fue creado con éxito", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this@CreateAlbumFragment).navigateUp()
            },{
                Toast.makeText(context, "Ocurrio un error creando el album", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this@CreateAlbumFragment).navigateUp()
            })

        }

        _binding?.albumCancelButton?.setOnClickListener {
            NavHostFragment.findNavController(this@CreateAlbumFragment).navigateUp()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@CreateAlbumFragment).navigateUp()
            }
        }



        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}