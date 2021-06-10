package com.sam.actonline.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        val zoomLevel = 15f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actvn, zoomLevel))


        //Add marker and custom it
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f",
            actvn.latitude,
            actvn.longitude
        )

        //Add overlay
        val overlaySize = 100f
        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.img_web))
            .position(actvn, overlaySize)
        mMap.addGroundOverlay(androidOverlay)

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_web)
        val scaled = Bitmap.createScaledBitmap(bitmap, 120, 120, false)

        mMap.addMarker(
            MarkerOptions()
                .position(actvn)
                .title("Học viện Kỹ thuật mật mã")
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(scaled))
        )
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