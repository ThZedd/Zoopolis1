package pt.iade.ei.zoopolis

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONArray
import pt.iade.ei.zoopolis.models.Coordinate
import pt.iade.ei.zoopolis.models.RouteRequest
import pt.iade.ei.zoopolis.retrofit.AEDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.EnclosureDTORepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.IaRepositoryImplementation
import pt.iade.ei.zoopolis.retrofit.Result
import pt.iade.ei.zoopolis.retrofit.RetrofitInstance
import pt.iade.ei.zoopolis.viewmodel.AEDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.EnclosureDTOViewModel
import pt.iade.ei.zoopolis.viewmodel.IaViewModel
import java.io.InputStream

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener {

    private val iaViewModel by viewModels<IaViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return IaViewModel(IaRepositoryImplementation(RetrofitInstance.iaApi)) as T
            }
        }
    })

    private val aeViewModel by viewModels<AEDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AEDTOViewModel(AEDTORepositoryImplementation(RetrofitInstance.api)) as T
            }
        }
    })

    private val enclosureViewModel by viewModels<EnclosureDTOViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EnclosureDTOViewModel(EnclosureDTORepositoryImplementation(RetrofitInstance.api)) as T
            }
        }
    })

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var destinationLatLng: LatLng? = null
    private val lisbonZooLocation = LatLng(38.744, -9.172)
    private var isChoosingOrigin = false
    private var originMarker: Marker? = null
    private var currentRoutePolyline: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.google_maps_activity)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observeRoute()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.uiSettings.apply {
            isMapToolbarEnabled = false
            isCompassEnabled = false
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
        }

        val zooBounds = LatLngBounds(
            LatLng(38.740000, -9.178000),
            LatLng(38.749000, -9.165000)
        )
        mMap.setLatLngBoundsForCameraTarget(zooBounds)

        mMap.setMinZoomPreference(15.0f)
        mMap.setMaxZoomPreference(20.0f)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(lisbonZooLocation, 17f)
        mMap.moveCamera(cameraUpdate)

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))
        mMap.setOnInfoWindowClickListener(this)
        mMap.setOnMapClickListener(this)

        addMarkersFromJson()

        val animalId = intent.getIntExtra("animal_id", -1)
        if (animalId != -1) {
            handleAutomaticRoute(animalId)
        }
    }

    private fun handleAutomaticRoute(animalId: Int) {
        aeViewModel.getAEByAnimalId(animalId)
        aeViewModel.aeByAnimalId.observe(this) { result ->
            if (result is Result.Success) {
                val enclosureId = result.data?.firstOrNull()?.enclosure?.id
                if (enclosureId != null) {
                    enclosureViewModel.loadEnclosureById(enclosureId)
                }
            }
        }

        enclosureViewModel.enclosureById.observe(this) { result ->
            if (result is Result.Success) {
                val enclosure = result.data
                if (enclosure?.latitude != null && enclosure.longitude != null) {
                    destinationLatLng = LatLng(enclosure.latitude, enclosure.longitude)
                    showOriginSelectionDialog()
                } else {
                    Toast.makeText(this, "Coordenadas do animal não encontradas", Toast.LENGTH_SHORT).show()
                }
            } else if (result is Result.Error) {
                Toast.makeText(this, "Erro ao carregar dados do recinto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        destinationLatLng = marker.position
        showOriginSelectionDialog()
    }

    override fun onMapClick(latLng: LatLng) {
        if (isChoosingOrigin) {
            isChoosingOrigin = false

            originMarker?.remove()

            originMarker = mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Ponto de Partida")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )

            val origin = latLng
            val destination = destinationLatLng
            if (destination != null) {
                getRouteFromServer(origin, destination)
            }
        }
    }

    private fun showOriginSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ponto de Partida")
        builder.setMessage("Escolha o ponto de partida da rota.")
        builder.setPositiveButton("Localização Atual") { _, _ ->
            requestLocationAndDrawRoute()
        }
        builder.setNegativeButton("Escolher no Mapa") { _, _ ->
            isChoosingOrigin = true
            Toast.makeText(this, "Toque no mapa para escolher a origem", Toast.LENGTH_SHORT).show()
        }
        builder.create().show()
    }

    private fun requestLocationAndDrawRoute() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val originLatLng = LatLng(location.latitude, location.longitude)
                        val destination = destinationLatLng
                        if (destination != null) {
                            getRouteFromServer(originLatLng, destination)
                        }
                    } else {
                        Toast.makeText(this, "Não foi possível obter a localização atual", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestLocationAndDrawRoute()
        }
    }

    private fun getRouteFromServer(origin: LatLng, destination: LatLng) {
        val request = RouteRequest(
            paragens = listOf(
                Coordinate(origin.latitude, origin.longitude),
                Coordinate(destination.latitude, destination.longitude)
            )
        )
        iaViewModel.getRoute(request)
    }

    private fun observeRoute() {
        lifecycleScope.launch {
            iaViewModel.route.collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        drawRouteOnMap(result.data?.rota ?: emptyList())
                    }
                    is Result.Error -> {
                        Toast.makeText(this@GoogleMapsActivity, "Erro ao obter a rota", Toast.LENGTH_SHORT).show()
                        Log.e("GoogleMapsActivity", "Error getting route", result.exception)
                    }
                    is Result.Loading -> {
                        Log.d("GoogleMapsActivity", "A carregar rota...")
                    }
                    null -> {}
                }
            }
        }
    }

    private fun drawRouteOnMap(path: List<Coordinate>) {
        currentRoutePolyline?.remove()

        if (path.isEmpty()) {
            Toast.makeText(this, "Não foi possível encontrar uma rota", Toast.LENGTH_SHORT).show()
            return
        }

        val polylineOptions = PolylineOptions()
            .color(Color.RED)
            .width(20f)
            .geodesic(true)

        path.forEach { coordinate ->
            polylineOptions.add(LatLng(coordinate.lat, coordinate.lng))
        }

        currentRoutePolyline = mMap.addPolyline(polylineOptions)
    }

    private fun addMarkersFromJson() {
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.locais)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val lat = jsonObject.getDouble("lat")
                val lng = jsonObject.getDouble("lng")
                val nome = jsonObject.getString("nome")
                val categoria = jsonObject.optString("categoria", "OUTRO")

                val iconResId = when (categoria) {
                    "ANIMAL" -> R.drawable.ic_animal
                    "COMIDA" -> R.drawable.ic_food
                    "TRANSPORTE" -> R.drawable.ic_transport
                    "SERVICO" -> R.drawable.ic_point
                    "CIDADE" -> R.drawable.ic_point
                    else -> R.drawable.ic_point
                }

                val customIcon = getResizedIcon(this, iconResId, 100, 100)

                val latLng = LatLng(lat, lng)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(nome)
                        .snippet("Deseja criar uma rota?")
                        .icon(customIcon)
                )
            }
        } catch (e: Exception) {
            Log.e("GoogleMapsActivity", "Erro ao ler locais.json", e)
        }
    }

    private fun getResizedIcon(context: Context, drawableId: Int, width: Int, height: Int): BitmapDescriptor? {
        val drawable = ContextCompat.getDrawable(context, drawableId) ?: return null
        drawable.setBounds(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
