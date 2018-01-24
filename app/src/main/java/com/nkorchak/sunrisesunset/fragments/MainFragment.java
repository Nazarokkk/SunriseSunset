package com.nkorchak.sunrisesunset.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.nkorchak.sunrisesunset.PlacesAutoCompleteAdapter;
import com.nkorchak.sunrisesunset.R;
import com.nkorchak.sunrisesunset.interfaces.IMainPresenter;
import com.nkorchak.sunrisesunset.models.SunRiseSunSetResponse;
import com.nkorchak.sunrisesunset.presenters.MainPresenter;
import com.nkorchak.sunrisesunset.views.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class MainFragment extends BaseFragment<IMainPresenter> implements MainView, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private PlacesAutoCompleteAdapter placesAdapter;
    private GoogleApiClient mGoogleApiClient;

    private TextView cityNameTextView;
    private TextView daylengthTextView;
    private TextView sunRiseTextView;
    private TextView sunSetTextView;

    private LocationManager locationManager;
    private String provider;
    private double lat;
    private double lon;
    private String address;

    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        presenter = new MainPresenter(this);

        getCurrentLocation();

        connectToGoogleAPI();
        initUI(view);

        return view;
    }

    private void getCurrentLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ensurePermissions(LOCATION_PERMISSIONS);
        } else {

            Location location = locationManager.getLastKnownLocation(provider);
            onLocationChanged(location);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasPermissionsGranted(LOCATION_PERMISSIONS)) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(provider, 400, 1, (android.location.LocationListener) this);
            Location location = locationManager.getLastKnownLocation(provider);

            onLocationChanged(location);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            lat = location.getLatitude();
            lon = location.getLongitude();
            presenter.getSunRiseSunSet(lat, lon);

            List<Address> addresses;
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lat, lon, 1);
                address = addresses.get(0).getLocality();

            } catch (Exception e) {
                showSnackBar("Location not available");
            }
        } else {
            showSnackBar("Location not available");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        showSnackBar("Connection to google service suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showSnackBar("Connection to google service failed");
    }

    private void connectToGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    private void initUI(View view) {
        AutoCompleteTextView cityAutoCompView = view.findViewById(R.id.autoCompleteTextView);

        placesAdapter = new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1,
                mGoogleApiClient, null, null);
        cityAutoCompView.setOnItemClickListener(mAutocompleteClickListener);
        cityAutoCompView.setAdapter(placesAdapter);

        cityNameTextView = view.findViewById(R.id.tv_city_name);
        daylengthTextView = view.findViewById(R.id.tv_day_length);
        sunRiseTextView = view.findViewById(R.id.tv_sunrise);
        sunSetTextView = view.findViewById(R.id.tv_sunset);
    }

    AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlacesAutoCompleteAdapter.PlaceAutocomplete item = placesAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    final ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallbacks<PlaceBuffer>() {
        @Override
        public void onSuccess(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                showSnackBar("Place query did not complete");
                return;
            }

            address = places.get(0).getAddress().toString();
            lat = places.get(0).getLatLng().latitude;
            lon = places.get(0).getLatLng().longitude;

            presenter.getSunRiseSunSet(lat, lon);
        }

        @Override
        public void onFailure(Status status) {
            showSnackBar("Error!");
        }
    };

    private void ensurePermissions(String... permissions) {
        ArrayList<String> deniedPermissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getActivity(), permission))
                deniedPermissionList.add(permission);
        }

        if (!deniedPermissionList.isEmpty())
            ActivityCompat.requestPermissions(getActivity(), deniedPermissionList.toArray(new String[0]), 0);
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateUi(SunRiseSunSetResponse sunRiseSunSetResponse) {
        sunRiseTextView.setText(sunRiseSunSetResponse.getResults().getSunrise());
        sunSetTextView.setText(sunRiseSunSetResponse.getResults().getSunset());
        daylengthTextView.setText(String.format("Day length: %s", sunRiseSunSetResponse.getResults().getDayLength()));
        cityNameTextView.setText(address);
    }
}
