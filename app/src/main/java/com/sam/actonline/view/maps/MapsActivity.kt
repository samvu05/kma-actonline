package com.sam.actonline.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sam.actonline.R
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityMapsBinding
import java.util.*


/**
 * Created by Dinh Sam Vu on 5/12/2021.
 */

@Suppress("DEPRECATION")
internal class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val requestPermision = 1

    override fun initView() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val actvn = LatLng(20.980911561180218, 105.79618558145081)

        enableMyLocation()
        mMap.uiSettings.isCompassEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val zoomLevel = 15f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actvn, zoomLevel))

        addPolygon()

        //Add marker and custom it
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f",
            actvn.latitude,
            actvn.longitude
        )

//        //Add overlay
//        val overlaySize = 100f
//        val androidOverlay = GroundOverlayOptions()
//            .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_chat))
//            .position(actvn, overlaySize)
//        mMap.addGroundOverlay(androidOverlay)
//
//        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_chat)
//        val scaled = Bitmap.createScaledBitmap(bitmap, 120, 120, false)
//
        mMap.addMarker(
            MarkerOptions()
                .position(actvn)
                .title("Học viện Kỹ thuật mật mã")
                .snippet(snippet)
        )
    }

    private fun addPolygon(){
        val polygon1: Polygon = mMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(20.980276555354592, 105.79530347489924),
                    LatLng(20.98043062097479, 105.79551779160244),
                    LatLng(20.98084323271539, 105.79511571070795),
                    LatLng(20.981638399483387, 105.7961364506466),
                    LatLng(20.981476086976926, 105.79635972976733),
                    LatLng(20.981989827797044, 105.79693228127262),
                    LatLng(20.98115756918844, 105.79750188125817),
                    LatLng(20.980460068010814, 105.79655860262085),
                    LatLng(20.98039751503817, 105.79662484701684),
                    LatLng(20.979730756743074, 105.7957532860484),
                    LatLng(20.980276555354592, 105.79530347489924),
                )
                .fillColor("#32FFC107".toColorInt())
                .strokeColor("#1E88E5".toColorInt())
        )
        polygon1.setTag("alpha")
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermision
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermision) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}