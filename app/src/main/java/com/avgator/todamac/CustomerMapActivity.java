package com.avgator.todamac;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.avgator.todamac.Customer.History;
import com.avgator.todamac.Customer.Search;
import com.avgator.todamac.databinding.ActivityDriverMapBinding;
import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomerMapActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private ActivityDriverMapBinding binding;
    Location mLastLocation, riderLocation;
    LocationRequest mLocationRequest;

    static final float END_SCALE = 0.7f;

    private FirebaseAuth mAuth;

    FusedLocationProviderClient mFusedLocationProviderClient;

    private Button mRequest;

    private SupportMapFragment mapFragment;

    private LatLng pickupLocation;
    private Boolean requestBol = false;

    private Marker pickupMarker;

    private String destination;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = "Info";

    private NavigationView navigationView;
    private RelativeLayout searchBtn;
    ImageView menuIcon;
    DrawerLayout drawerLayout;
    RelativeLayout contentView;
    private double desLatitude, desLongitude;

    private LinearLayout mDriverInfo;

    private ImageView mDriverProfileImage, mNavProfileImage;

    private TextView mDriverName, mDriverPhone, etSearchText;
    private TextView mNavName, mNavAddress, mFare;

    private RatingBar mRatingBar;

    private static final double worldRadius = 6371; // Earth's radius in kilometers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_map);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCustomer);
        mapFragment.getMapAsync(this);

        mRequest = (Button) findViewById(R.id.request);
        menuIcon = findViewById(R.id.menu_icon);
        searchBtn = findViewById(R.id.rlSearch);

        mDriverInfo = (LinearLayout) findViewById(R.id.driverInfo);

        mDriverProfileImage = (ImageView) findViewById(R.id.driverProfileImage);

        mDriverName = (TextView) findViewById(R.id.driverName);
        mDriverPhone = (TextView) findViewById(R.id.driverPhone);

        navigationView = findViewById(R.id.navigation_view);

        //Hoook for nav header
        View headerView = navigationView.getHeaderView(0);
        mNavAddress = headerView.findViewById(R.id.address);
        mNavName = headerView.findViewById(R.id.user_name);

        mNavProfileImage = headerView.findViewById(R.id.profileImage);

        etSearchText = findViewById(R.id.etSearchText);

        mFare = findViewById(R.id.fare);

        Intent intent = getIntent();

        if(intent.hasExtra("destinationName")){
            etSearchText.setText("Book now to reveal your location to the driver..");
        }

        if(mLastLocation == null && Objects.equals(riderLocation, new LatLng(0.0, 0.0))){
            mRequest.setEnabled(false);
            startLocationUpdates();
        }


        contentView = findViewById(R.id.contentView);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        mRatingBar = findViewById(R.id.ratingBar);

        String apiKey = getString(R.string.api_key);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        /*mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerMapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });*/

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMapActivity.this, Search.class);
                intent.putExtra("riderLocation", mLastLocation);
                startActivity(intent);
                return;
            }
        });

        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchBtn.setEnabled(false);

                if (requestBol) {
                    if(driverFoundID == null){

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("customerRequest");
                        GeoFire geoFire = new GeoFire(driverRef);
                        geoFire.removeLocation(userId);


                        if (pickupMarker != null) {
                            pickupMarker.remove();
                        }
                        if (mDriverMarker != null) {
                            mDriverMarker.remove();
                        }
                        mRequest.setTextColor(getResources().getColor(R.color.red));
                        mRequest.setAllCaps(false);
                        mRequest.setText("You can't book now.. If you wish to book again please close your app first..");

                    }else{
                        endRide();
                    }

                } else {
                    requestBol = true;

                    if (intent.hasExtra("destinationName") && intent.hasExtra("riderLocation") && intent.hasExtra("longitude") && intent.hasExtra("latitude")) {
                        // Get the selected destination and location from the extras

                        riderLocation = intent.getParcelableExtra("riderLocation");
                        destination = intent.getStringExtra("destinationName");
                        desLatitude = intent.getDoubleExtra("latitude", 0.0);
                        desLongitude = intent.getDoubleExtra("longitude", 0.0);

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customerRequest");
                        GeoFire geoFire = new GeoFire(ref);
                        geoFire.setLocation(userId, new GeoLocation(riderLocation.getLatitude(), riderLocation.getLongitude()));

                        pickupLocation = new LatLng(riderLocation.getLatitude(), riderLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(pickupLocation).title("Pickup Here"));
                        pickupMarker = mMap.addMarker(new MarkerOptions().position(pickupLocation).title("Pickup Here"));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pickupLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));


                        etSearchText.setText("Please wait for your driver you cant change your destination now..");
                        mRequest.setText("Getting your driver");

                        if(desLatitude == 0.0){
                            mFare.setText("Estimated fare: --");
                        }else{

                            mFare.setText("Estimated fare: "+ String.valueOf(fare(riderLocation.getLatitude(), riderLocation.getLongitude(), desLatitude, desLongitude)));

                        }

                        getClosestDrivers();

                    }else if(mLastLocation == null){

                        Toast.makeText(CustomerMapActivity.this,"Your location is not trackable please restart your device and allow the location",Toast.LENGTH_LONG);

                    }
                    else {
                        // Use the default destination and location

                        destination = "";
                        desLongitude = 0.0;
                        desLatitude = 0.0;

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customerRequest");
                        GeoFire geoFire = new GeoFire(ref);
                        geoFire.setLocation(userId, new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()));

                        pickupLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(pickupLocation).title("Pickup Here"));
                        pickupMarker = mMap.addMarker(new MarkerOptions().position(pickupLocation).title("Pickup Here"));

                        etSearchText.setText("Please wait for your driver you cant search for destination now..");
                        mRequest.setText("Getting your driver");

                        mFare.setText("Estimated fare: --");

                        getClosestDrivers();
                    }

                }

            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationDrawer();

        displayInfo();

    }

    private void displayInfo() {

        String customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerId);
        mDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null) {
                        mNavName.setText(map.get("name").toString());
                    } else if (map.get("Name") != null) {
                        mNavName.setText(map.get("Name").toString());
                    } else if (map.get("fullname") != null) {
                        mNavName.setText(map.get("fullname").toString());
                    } else if (map.get("fullName") != null) {
                        mNavName.setText(map.get("fullName").toString());
                    }

                    if (map.get("address") != null) {
                        mNavAddress.setText(map.get("address").toString());
                    }

                    if (map.get("profile_picture") != null) {
                        // Load the user's profile picture from Firebase Storage
                        String imageUrl = map.get("profile_picture").toString();
                        Glide.with(CustomerMapActivity.this)
                                .load(imageUrl)
                                .into(mNavProfileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private int radius = 1;
    private Boolean driverFound = false;
    private String driverFoundID;

    GeoQuery geoQuery;

    private void getClosestDrivers() {

        DatabaseReference driverLocation = FirebaseDatabase.getInstance().getReference().child("DriverAvailable");

        GeoFire geoFire = new GeoFire(driverLocation);
        geoQuery = geoFire.queryAtLocation(new GeoLocation(pickupLocation.latitude, pickupLocation.longitude), radius);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!driverFound && requestBol) {
                    driverFound = true;
                    driverFoundID = key;

                    DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverFoundID).child("customerRequest");
                    String customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    HashMap map = new HashMap();
                    map.put("customerRideId", customerId);
                    map.put("destination", destination);
                    map.put("destinationLat", desLatitude);
                    map.put("destinationLng", desLongitude);
                    driverRef.updateChildren(map);

                    getDriverLocation();
                    getDriverInfo();
                    getHasRideEnd();

                    etSearchText.setText("If you wish to change destination talk to your driver or the book button to cancel");
                    mRequest.setText("Getting drivers Location.. click to cancel");

                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!driverFound) {
                    radius++;
                    if (radius > 21450) {
                        radius = 1;
                    }
                    getClosestDrivers();
                    Log.d("TAG", "radius" + radius);
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void getDriverInfo() {
        mDriverInfo.setVisibility(View.VISIBLE);
        DatabaseReference mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverFoundID);
        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null) {
                        mDriverName.setText(map.get("name").toString());
                    }
                    if (map.get("phone") != null) {
                        mDriverPhone.setText(map.get("phone").toString());
                    }
                    if (map.get("profileImageUrl") != null) {
                        Glide.with(getApplication()).load(map.get("profileImageUrl").toString()).into(mDriverProfileImage);
                    }
                }


                int ratingsum = 0;
                float ratingTotal = 0;
                float ratingAvg = 0;

                for (DataSnapshot child : snapshot.child("rating").getChildren()){

                    ratingsum = ratingsum + Integer.valueOf(child.getValue().toString());
                    ratingTotal++;
                }
                if(ratingTotal != 0){
                    ratingAvg = ratingsum/ratingTotal;
                    mRatingBar.setRating(ratingAvg);
                }else{
                    mRatingBar.setRating(0);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private DatabaseReference rideHasEndedRef;
    private ValueEventListener rideHasEndedRefListener;

    private void getHasRideEnd() {
        rideHasEndedRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverFoundID).child("customerRequest");
        rideHasEndedRefListener = rideHasEndedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                } else {
                    endRide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void endRide() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        requestBol = false;
        geoQuery.removeAllListeners();
        driverLocationRef.removeEventListener(driverLocationRefListener);
        rideHasEndedRef.removeEventListener(rideHasEndedRefListener);




        if (driverFoundID != null) {

            DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverFoundID).child("customerRequest");
            driverRef.setValue(true);
            driverFoundID = null;

        }
        driverFound = false;
        radius = 1;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customerRequest");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userId);

        if (pickupMarker != null) {
            pickupMarker.remove();
        }
        if (mDriverMarker != null) {
            mDriverMarker.remove();
        }
        mRequest.setText("Book now...");

        mDriverInfo.setVisibility(View.GONE);
        mDriverName.setText("");
        mDriverPhone.setText("");
        mDriverProfileImage.setImageResource(R.drawable.profile_icon);


    }

    private Marker mDriverMarker;
    private DatabaseReference driverLocationRef;
    private ValueEventListener driverLocationRefListener;


    private void getDriverLocation() {

        driverLocationRef = FirebaseDatabase.getInstance().getReference().child("DriverWorking").child(driverFoundID).child("l");
        driverLocationRefListener = driverLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && requestBol) {
                    List<Object> map = (List<Object>) snapshot.getValue();
                    double locationLat = 0;
                    double locationLng = 0;
                    mRequest.setText("Driver Found");
                    if (map.get(0) != null) {
                        locationLat = Double.parseDouble(map.get(0).toString());
                    }
                    if (map.get(1) != null) {
                        locationLng = Double.parseDouble(map.get(1).toString());
                    }
                    LatLng driverLatLng = new LatLng(locationLat, locationLng);
                    if (mDriverMarker != null) {
                        mDriverMarker.remove();
                    }

                    Location loc1 = new Location("");
                    loc1.setLatitude(pickupLocation.latitude);
                    loc1.setLongitude(pickupLocation.longitude);


                    Location loc2 = new Location("");
                    loc2.setLatitude(driverLatLng.latitude);
                    loc2.setLongitude(driverLatLng.longitude);

                    float distance = loc1.distanceTo(loc2);

                    if(distance < 500){
                        mRequest.setEnabled(false);
                        mRequest.setText("You can't cancel now your driver is near");
                    }
                    if (distance < 100) {
                        mRequest.setText("Driver is Here..  ");
                    } else {
                        mRequest.setText("Driver Found: " + String.valueOf(distance) + " Tap to cancel request");
                    }


                    mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng).title("Your driver"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                checkLocationPermission();
            }
        }
    }

    LocationCallback mLocationCallback = new com.google.android.gms.location.LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {

                    mLastLocation = location;
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                }
            }
        }
    };

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(CustomerMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(CustomerMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                    }
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }



    //Navigation Drawer Function
    private void navigationDrawer() {

        //Navigation Hooks
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);


            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_profile:
                //startActivity(new Intent(getApplicationContext(), AllCategories.class));
                Intent intent = new Intent(CustomerMapActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_history:
                //startActivity(new Intent(getApplicationContext(), Destinations.class));
                Intent intent1 = new Intent(CustomerMapActivity.this, History.class);
                intent1.putExtra("customerOrDriver", "Customers");
                startActivity(intent1);
                break;

            case R.id.nav_messages:
                //startActivity(new Intent(getApplicationContext(), Events.class));
                break;

            case R.id.nav_notif:
                //startActivity(new Intent(getApplicationContext(), Login.class));
                break;

            case R.id.logout:
                //startActivity(new Intent(getApplicationContext(), AboutUs.class));
                FirebaseAuth.getInstance().signOut();
                Intent intent3 = new Intent(CustomerMapActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                break;

        }

        return true;
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        }
    }




    private static double fare(double startingLat, double startingLon, double destinationLat, double destinationLon) {
        double fare;

        double dLat = Math.toRadians(destinationLat - startingLat);
        double dLon = Math.toRadians(destinationLon - startingLon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(startingLat)) * Math.cos(Math.toRadians(destinationLat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = worldRadius * c;

        if (distance < 1){
            fare = 20.0;
        }else{
            fare = distance * 22;
        }

        return fare;

    }


}