package com.example.greaterportharcourtcity.ui.Map

import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.greaterportharcourtcity.R
import com.example.greaterportharcourtcity.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.utils.sphericalPathLength
import org.json.JSONArray
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    //Instantiate Json files
    private val chobaJson: String = "ALUU_CHOBA.json"
    private val enekaJson: String = "ENEKA.json"
    private val gphJson: String = "GPH_RING_ROAD.json"
    private val oyigbJson: String = "IGBO_ETCHE_OYIGBO.json"
    private val m10Json: String = "M10.json"


    private var point: Marker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.clear.setOnClickListener() {
            mMap.clear()
            binding.tvDistance.text = "0.0"
        }

      //  binding.search.setOnClickListener {
        //}

        //add Alu_choba click listener
        binding.choba.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(CHOBA).title("ALUU_CHOBA")
                    .snippet("Latitude:" + CHOBA.latitude + "," + "Longitude:" + CHOBA.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(CHOBA))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((CHOBA), 10f))
            val cameraPosition = CameraPosition.Builder().target(CHOBA).zoom(14.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            //Add the Json Omagwa Json file
            val chobaPolyline = getPosition( chobaJson)
            //Add polyline
            val addPolyline = mMap.addPolyline(
                PolylineOptions()
                    .addAll(chobaPolyline!!)
                    .color(Color.MAGENTA)
                    .pattern(Arrays.asList(Dot(), Gap(10.0f)))
                    .geodesic(true)
                    .zIndex(5F)
            )

            val length =  addPolyline.sphericalPathLength
            binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }



        //Adding Eneka
        binding.eneka.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(ENEKA).title("ENEKA")
                    .snippet("Latitude:" + ENEKA.latitude + "," + "Longitude:" + ENEKA.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ENEKA))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((ENEKA), 12f))
            val cameraPosition = CameraPosition.Builder().target(ENEKA).zoom(14.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            //Add the Json eneka Json file
            val omagwaPolyline = getPosition(enekaJson)
            //Add polyline
            val addPolyline = mMap.addPolyline(
                PolylineOptions()
                    .addAll(omagwaPolyline!!)
                    .color(Color.MAGENTA)
                    .pattern(Arrays.asList(Dot(), Gap(10.0f)))
                    .geodesic(true)
                    .zIndex(5F)
            )

            val length =  addPolyline.sphericalPathLength
            binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }

        //Add ring road click listener
        binding.ringRoad.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(GPH_RING_ROAD).title("GPH_RING_ROAD")
                    .snippet("Latitude:" + GPH_RING_ROAD.latitude + "," + "Longitude:" + GPH_RING_ROAD.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(GPH_RING_ROAD))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((GPH_RING_ROAD), 8f))
            val cameraPosition = CameraPosition.Builder().target(GPH_RING_ROAD).zoom(12f).build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        Log.d("Map", "Animation finished")
                    }

                    override fun onCancel() {
                        Log.d("Map", "Animation interrupted")
                    }
                })

            //Add the Json Omagwa Json file
            val ringPolyline = getPosition(gphJson)
            //Add polyline
            val addPolyline = mMap.addPolyline(
                PolylineOptions()
                    .addAll(ringPolyline!!)
                    .color(Color.RED)
                    .pattern(Arrays.asList(Dot(), Gap(10.0f)))
                    .geodesic(true)
                    .zIndex(5F)
            )

            val length =  addPolyline.sphericalPathLength
            binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }

        //OYIGBO POLYLINE
        binding.oyigbo.setOnClickListener {

            //add the marker and move the camera
            mMap.addMarker(
                MarkerOptions().position(OYIGBO).title("IGBO_ETCHE_OYIGBO")
                    .snippet("Latitude:" + OYIGBO.latitude + "," + "Longitude:" + OYIGBO.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(OYIGBO))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((OYIGBO), 10f))
            val cameraPosition = CameraPosition.Builder().target(OYIGBO).zoom(15.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val OyigboPolyline = getPosition(oyigbJson)
            //Add polyline
            val addPolyline = mMap.addPolyline(
                PolylineOptions()
                    .addAll(OyigboPolyline!!)
                    .color(Color.RED)
                    .pattern(Arrays.asList(Dot(), Gap(10.0f)))
                    .geodesic(true)
                    .zIndex(5F)
            )

            val length =  addPolyline.sphericalPathLength
            binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }

        //M10 POLYLINE
        binding.m10.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(M10).title("M10")
                    .snippet("Latitude:" + M10.latitude + "," + "Longitude:" + M10.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(M10))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((M10), 13f))
            val cameraPosition = CameraPosition.Builder().target(M10).zoom(14.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            //Add the Json Omagwa Json file
            val M10Polyline = getPosition(m10Json)
            //Add polyline
            val addPolyline = mMap.addPolyline(
                PolylineOptions()
                    .addAll(M10Polyline!!)
                    .color(Color.RED)
                    .pattern(Arrays.asList(Dot(), Gap(10.0f)))
                    .geodesic(true)
                    .zIndex(5F)
            )

            val length =  addPolyline.sphericalPathLength
            binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }


        //Addding polygons
        binding.isiopko.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(ISIOKPO).title("ISIOPKO OMAGWA BOUNDARY")
                    .snippet("Latitude:" + ISIOKPO.latitude + "," + "Longitude:" + ISIOKPO.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ISIOKPO))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((ISIOKPO), 10f))
            val cameraPosition = CameraPosition.Builder().target(ISIOKPO).zoom(15.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val addPolygon = getPosition("NEIGHBOURHOODS AT ISIOKPO_OMAGWA.json")
            //Add polyline
            mMap.addPolygon(PolygonOptions()
                .addAll(addPolygon!!)
                .strokeColor(Color.RED)
                .strokeWidth(2f)
                .fillColor(0x33FF0000)
                .zIndex(5F))
            //val length =  addPolygon.sphericalPathLength
            //binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }

        binding.gpc.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(GPH_BOUNDARY).title("GREATER PH BOUNDARY")
                    .snippet("Latitude:" + GPH_BOUNDARY.latitude + "," + "Longitude:" + GPH_BOUNDARY.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(GPH_BOUNDARY))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((GPH_BOUNDARY), 15f))
            val cameraPosition = CameraPosition.Builder().target(GPH_BOUNDARY).zoom(10.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val IsiopkoPolygon = getPosition("GPH_BOUNDARY.json")
            //Add polyline
            mMap.addPolygon(PolygonOptions()
                .addAll(IsiopkoPolygon!!)
                .strokeColor(Color.RED)
                .zIndex(5F))


        }
        binding.commerce.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(COMMERCIAL_ZONE).title("COMMERCIAL ZONE")
                    .snippet("Latitude:" + COMMERCIAL_ZONE.latitude + "," + "Longitude:" + COMMERCIAL_ZONE.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(COMMERCIAL_ZONE))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((COMMERCIAL_ZONE), 10f))
            val cameraPosition = CameraPosition.Builder().target(COMMERCIAL_ZONE).zoom(13.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val addPolygon = getPosition("COMMERCIAL_ZONES.json")
            //Add polyline
            mMap.addPolygon(PolygonOptions()
                .addAll(addPolygon!!)
                .strokeColor(Color.RED)
                .fillColor(0x33FF0000)
                .zIndex(5F))
            //val length =  addPolygon.sphericalPathLength
            //binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }

        binding.airport.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position(AIRPORT).title("AIRPORT BOUNDARY")
                    .snippet("Latitude:" + AIRPORT.latitude + "," + "Longitude:" + AIRPORT.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(AIRPORT))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((AIRPORT), 10f))
            val cameraPosition = CameraPosition.Builder().target(AIRPORT).zoom(12.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val addPolygon = getPosition("AIRPORTS.json")
            //Add polyline
            mMap.addPolygon(PolygonOptions()
                .addAll(addPolygon!!)
                .strokeColor(Color.RED)
                .strokeWidth(2f)
                .fillColor(0x33FF0000)
                .zIndex(5F))

            //val length =  addPolygon.sphericalPathLength
            //binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }
        binding.recreation.setOnClickListener {

            //add the marker and move the camera
            point = mMap.addMarker(
                MarkerOptions().position( REACREATION_AREAS).title(" REACREATIONAL CENTERS")
                    .snippet("Latitude:" +  REACREATION_AREAS.latitude + "," + "Longitude:" +  REACREATION_AREAS.longitude)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng( REACREATION_AREAS))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(( REACREATION_AREAS), 10f))
            val cameraPosition = CameraPosition.Builder().target( REACREATION_AREAS).zoom(14.0f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            //Add the Json Omagwa Json file
            val addPolygon = getPosition("RECREATIONAL_CENTERS.json")
            //Add polyline
            mMap.addPolygon(PolygonOptions()
                .addAll(addPolygon!!)
                .strokeColor(Color.RED)
                .strokeWidth(2f)
                .fillColor(0x33FF0000)
                .zIndex(5F))

            //val length =  addPolygon.sphericalPathLength
            //binding.tvDistance.text ="%.4f".format((length/1000)) .toString()+"Km"

        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

         mMap.addMarker(
            MarkerOptions().position(GPH_RING_ROAD).title("GREATER PORT HARCOURT CITY")
                .snippet("Latitude:" + GPH_RING_ROAD.latitude + "," + "Longitude:" + GPH_RING_ROAD.longitude)
        )
        //Move the camera to the desired location

        mMap.moveCamera(CameraUpdateFactory.newLatLng(GPH_RING_ROAD))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((GPH_RING_ROAD), 12f))
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false)
        mMap.getUiSettings().isRotateGesturesEnabled = true
        mMap.getUiSettings().isCompassEnabled = true


        setPoiClick(mMap)
        setMapLongClick(mMap)
        displayBoundary(mMap)
    }

    private fun displayBoundary(map: GoogleMap) {

        map.addMarker(
            MarkerOptions().position(GPH_BOUNDARY).title("GREATER PH BOUNDARY")
                .snippet("Latitude:" + GPH_BOUNDARY.latitude + "," + "Longitude:" + GPH_BOUNDARY.longitude)
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(GPH_BOUNDARY))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom((GPH_BOUNDARY), 10f))

        //Add the Json Omagwa Json file
        val boundaryPolygon = getPosition("GPH_BOUNDARY.json")
        //Add polyline
        map.addPolygon(
            PolygonOptions()
                .addAll(boundaryPolygon!!)
                .strokeColor(Color.RED)
                .strokeWidth(3f)
                .zIndex(5F)
        )
    }
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker?.showInfoWindow()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.map_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }

        R.id.hybrid_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun getPosition(fileName: String?): MutableList<LatLng>? {

        if (fileName != null) {
            //instantiate the polylines using data model
            val polylines = java.util.ArrayList<LatLng>()

            val inputStream = application.assets.open(fileName).bufferedReader()
            var Json_string = inputStream.use {
                it.readText()
            }
            // fetching json array
            val jsonArray = JSONArray(Json_string)
            //get user data using for loop
            for (i in 0 until jsonArray.length()) {
                //create json object for fetching single user data
                val points = jsonArray.getJSONObject(i)

                val latitude = points.getDouble("latitude")
                val longitude = points.getDouble("longitude")
                // add all the variables to data model and add the model to list
                val localPoints = LatLng(latitude, longitude)

                //add the details
                polylines.add(localPoints)
            }
            return polylines
        }
        return null
    }

    companion object{
        val TAG: String = MapsActivity::class.java.getSimpleName()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
        private val REQUEST_PLACES_AUTOCOMPLETE = 4


        private val OMAGWA :LatLng= LatLng(4.92740646,6.879249024)
        // omagba destination
        //oyigbo origin
        private val OYIGBO: LatLng = LatLng(4.896715896	,7.099736791)
        //oyigbo destination
        private val  GPH_RING_ROAD: LatLng = LatLng(  4.925077415, 6.878490712)
        private val  ENEKA: LatLng = LatLng(  4.922584648, 7.031426922)

        private val  M10: LatLng = LatLng(  4.837554821, 7.124359909)
        private val CHOBA: LatLng = LatLng(  4.94943055, 6.921001954)
        private val ISIOKPO: LatLng = LatLng(  4.915100322, 6.976156182)
        private val GPH_BOUNDARY: LatLng = LatLng(  4.92537, 7.04606)
        private val ETCHE: LatLng = LatLng(  5.009701441, 7.015680995)
        private val ONNE: LatLng = LatLng(  5.077749697, 6.881155488)
        private val AIRPORT: LatLng = LatLng(     5.041792665, 6.958054951)
        private val COMMERCIAL_ZONE: LatLng = LatLng(     4.941923, 6.98853)
        private val REACREATION_AREAS: LatLng = LatLng(     4.979363362, 6.965630688)
    }

}