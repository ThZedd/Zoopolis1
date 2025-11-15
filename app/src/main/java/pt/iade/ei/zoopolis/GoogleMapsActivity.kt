package pt.iade.ei.zoopolis

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import pt.iade.ei.zoopolis.retrofit.AEDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.AnimalsDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.EnclosureDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.Result
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.viewmodel.AEDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.AnimalDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.EnclosureDTOViewModel

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<AnimalDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimalDTOViewModel(AnimalsDTORepositoryImplementation(RetrofitInstance.api))
                        as T
            }
        }
    })
    private val AEDTOViewModel by viewModels<AEDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AEDTOViewModel(
                    AEDTORepositoryImplementation(RetrofitInstance.api)
                )
                        as T
            }
        }
    })
    private val EnclosureDTOViewModel by viewModels<EnclosureDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EnclosureDTOViewModel(
                    EnclosureDTORepositoryImplementation(RetrofitInstance.api)
                )
                        as T
            }
        }
    })
    private lateinit var mMap: GoogleMap
    private val fixedLocation = LatLng(38.743250393006164, -9.169193186278884) // Sua localização fixa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animalId = intent.getIntExtra("animal_id", -1) // Obtém o ID do animal
        setContentView(R.layout.google_maps_activity)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this) // Passa 'this' como o callback
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Adiciona o marcador na localização fixa
        mMap.addMarker(
            MarkerOptions()
                .position(fixedLocation)
                .title("Your Location") // Título do marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )

        // Centraliza a câmera na localização fixa com o zoom de 18
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(fixedLocation, 18f)
        mMap.moveCamera(cameraUpdate)

        val animalId = intent.getIntExtra("animal_id", -1) // Obtém o ID do animal
        if (animalId != -1) {
            AEDTOViewModel.getAEByAnimalId(animalId) // Fetch AE data for the given animalId
            AEDTOViewModel.aeByAnimalId.observe(this) { result ->
                when (result) {
                    is Result.Sucess -> {
                        result.data?.let { aeList ->
                            for (ae in aeList) {
                                val enclosureId = ae.enclosure?.id // Acessa o id do Enclosure
                                if (enclosureId != null) {
                                    // Busca o EnclosureDTO usando o EnclosureId
                                    EnclosureDTOViewModel.loadEnclosureById(enclosureId)
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        Log.e("GoogleMapsActivity", "Erro ao carregar AE: ${result.message}")
                    }
                }
            }

            // Observa o EnclosureDTOViewModel para adicionar marcadores no mapa
            EnclosureDTOViewModel.enclosureById.observe(this) { result ->
                when (result) {
                    is Result.Sucess -> {
                        result.data?.let { enclosure ->
                            val enclosureLatLng = LatLng(enclosure.latitude!!, enclosure.longitude!!)
                            mMap.addMarker(
                                MarkerOptions().position(enclosureLatLng).title(enclosure.name)
                            )
                            drawCustomPath(fixedLocation, enclosureLatLng)
                        }
                    }
                    is Result.Error -> {
                        Log.e("GoogleMapsActivity", "Erro ao carregar Enclosure: ${result.message}")
                    }
                }
            }
        }

        try {
            // Carrega o estilo do JSON
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            )
            if (!success) {
                Log.e("GoogleMapsActivity", "Falha ao aplicar o estilo do mapa.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("GoogleMapsActivity", "Erro: Estilo não encontrado.", e)
        }
    }

    private fun drawCustomPath(origin: LatLng, destination: LatLng) {
        // Lista de pontos para o caminho personalizado
        val animalId = intent.getIntExtra("animal_id", -1)
        if (animalId == 1) {
            val path1 = listOf(
                origin, // Ponto inicial
                LatLng(38.743400, -9.169500), // Ponto intermediário 1
                LatLng(38.743500, -9.169800), // Ponto intermediário 2
                LatLng(38.743600, -9.169950),
                LatLng(38.743946181471976, -9.170792901435634),
                LatLng(38.7442641633238, -9.171281063441064),
                destination // Ponto final
            )


            val polylineOptions = PolylineOptions()
                .addAll(path1) // Adiciona os pontos ao caminho
                .color(Color.RED) // Define a cor como vermelha
                .width(25f) // Define a largura da linha

            mMap.addPolyline(polylineOptions) // Adiciona a polilinha ao mapa
        } else if (animalId == 2 ) {
            val path2 = listOf(
                origin, // Ponto inicial
                LatLng(38.74372861412589, -9.170052611801031), // Ponto intermediário 1
                LatLng(38.74394618149531, -9.169757568852043), // Ponto intermediário 2
                LatLng(38.744439889552446, -9.170288646198607),
                LatLng(38.74522646821875, -9.17037447688088),
                destination // Ponto final
            )
            val polylineOptions = PolylineOptions()
                .addAll(path2) // Adiciona os pontos ao caminho
                .color(Color.RED) // Define a cor como vermelha
                .width(25f) // Define a largura da linha

            mMap.addPolyline(polylineOptions) // Adiciona a polilinha ao mapa
        } else if (animalId == 3 || animalId == 4) {
            val path3 = listOf(
                origin, // Ponto inicial
                LatLng(38.74372861412589, -9.170052611801031), // Ponto intermediário 1
                LatLng(38.74290854663222, -9.171361529705694), // Ponto intermediário 2
                LatLng(38.74318887688594, -9.171731674522997),
                destination // Ponto final
            )
            val polylineOptions = PolylineOptions()
                .addAll(path3) // Adiciona os pontos ao caminho
                .color(Color.RED) // Define a cor como vermelha
                .width(25f) // Define a largura da linha

            mMap.addPolyline(polylineOptions) // Adiciona a polilinha ao mapa
        } else {
            val path4 = listOf(
                origin, // Ponto inicial
                LatLng(38.74372861412589, -9.170052611801031), // Ponto intermediário 1
                LatLng(38.743628198227434, -9.170213544351617), // Ponto intermediário 2
                LatLng(38.74370769417716, -9.170690977521762),
                LatLng(38.7436210822867, -9.171193817662719),
                LatLng(38.7436034907311, -9.17141485272141),
                destination // Ponto final
            )
            val polylineOptions = PolylineOptions()
                .addAll(path4) // Adiciona os pontos ao caminho
                .color(Color.RED) // Define a cor como vermelha
                .width(25f) // Define a largura da linha

            mMap.addPolyline(polylineOptions) // Adiciona a polilinha ao mapa
        }
    }
}
