package com.example.ingsoftappmobiles.ui.albums


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.ingsoftappmobiles.R
import com.example.ingsoftappmobiles.databinding.AlbumTrackCreateBinding
import com.example.ingsoftappmobiles.models.Track
import com.example.ingsoftappmobiles.repositories.AlbumDetailRepository
import com.google.android.material.textfield.TextInputEditText


class CreateTrackFragment : Fragment() {
    private var pickerMinutes: NumberPicker? = null
    private var pickerSeconds: NumberPicker? = null
    private var _binding: AlbumTrackCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumsRepository:AlbumDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumTrackCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val args: CreateTrackFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())

        pickerMinutes = getView()?.findViewById(R.id.numberPicker_track_minutes)
        pickerMinutes?.minValue = 0
        pickerMinutes?.maxValue = 60

        pickerSeconds = getView()?.findViewById(R.id.numberPicker_track_seconds)
        pickerSeconds?.minValue = 0
        pickerSeconds?.maxValue = 60

        _binding?.trackCreateButton?.setOnClickListener {

            val nameTxt : EditText? = _binding?.textTrackName
            val minutesTxt : NumberPicker? = _binding?.numberPickerTrackMinutes
            val secondsTxt : NumberPicker? = _binding?.numberPickerTrackSeconds

            val durationTxt = minutesTxt?.value.toString() + ":" + secondsTxt?.value.toString()

            val track = Track(args.albumId, nameTxt?.text.toString(), durationTxt)

            albumsRepository = AlbumDetailRepository(activity.application)
            albumsRepository.createTrack(track, {
                Toast.makeText(context, "Canción creada con éxito", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this@CreateTrackFragment).navigateUp()
            },{
                Toast.makeText(context, "Ocurrio un error creando la canción", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this@CreateTrackFragment).navigateUp()
            })

        }

        _binding?.trackCancelButton?.setOnClickListener {
            NavHostFragment.findNavController(this@CreateTrackFragment).navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}