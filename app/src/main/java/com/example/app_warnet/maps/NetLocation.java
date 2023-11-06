package com.example.app_warnet.maps;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.app_warnet.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.app_warnet.databinding.ActivityNetLocationBinding;

public class NetLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityNetLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNetLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //get latlong for corners for specified place
        LatLng one = new LatLng(-6.244110834861888,    106.61992895790624);
        LatLng two = new LatLng(-6.3686456863392324,   106.83452585935126);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);

        // Venom iCafe
        LatLng venom = new LatLng(-6.325303950260215 , 106.68422923775397);
        mMap.addMarker(new MarkerOptions().position(venom).title("Venom iCafe"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(venom));

        // Big Gaming
//        LatLng biggaming = new LatLng(-6.348686177879016, 106.73636357935217);
//        mMap.addMarker(new MarkerOptions().position(biggaming).title("Big Gaming"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(biggaming));

        // OzLand 2 Net
        LatLng ozlandnet = new LatLng(-6.327684311812984, 106.70761942807376);
        mMap.addMarker(new MarkerOptions().position(ozlandnet).title("OzLand 2 Net"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ozlandnet));

        // Bit Net eSports Bambu Apus
        LatLng bestnet = new LatLng(-6.3199671297663995, 106.7362763814436);
        mMap.addMarker(new MarkerOptions().position(bestnet).title("Bit Net Setia Budi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bestnet));

        // Bit Net eSports Setia Budi
        LatLng bitnet = new LatLng(-6.349630626179234,  106.74043594800087);
        mMap.addMarker(new MarkerOptions().position(bitnet).title("Bit Net Setiabudi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bitnet));

        // Warnet Karun
        LatLng karun = new LatLng(-6.323373444200788,  106.73726428205198);
        mMap.addMarker(new MarkerOptions().position(karun).title("Warnet Karun"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karun));

        // SMARTLINK.NET
        LatLng smartlink = new LatLng(-6.336945455755325,   106.73892617006697);
        mMap.addMarker(new MarkerOptions().position(smartlink).title("SMARTLINK.NET"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(smartlink));

        // Asyura Net
//        LatLng asyura = new LatLng(-6.356736128466338,   106.76278710194258);
//        mMap.addMarker(new MarkerOptions().position(asyura).title("Asyura Net"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(asyura));

        // YANNOS NET
        LatLng yannos = new LatLng(-6.358602128044379,    106.73183440948011);
        mMap.addMarker(new MarkerOptions().position(yannos).title("YANNOS NET"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yannos));

        // Pratama Net
        LatLng pratama = new LatLng(-6.3450921396182025,    106.7368018601701);
        mMap.addMarker(new MarkerOptions().position(pratama).title("Pratama Net"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pratama));

        // Gallerianz Net
        LatLng gallerianz = new LatLng(-6.334100350821228,    106.70968045235192);
        mMap.addMarker(new MarkerOptions().position(gallerianz).title("Gallerianz Net"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gallerianz));

    }
}