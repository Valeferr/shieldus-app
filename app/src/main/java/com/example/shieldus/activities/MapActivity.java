package com.example.shieldus.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;

import com.example.shieldus.R;
import com.example.shieldus.fragments.AddServiceDialogFragment;
import com.example.shieldus.models.ServiceCenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final List<ServiceCenter> serviceCenters = new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String TAG = "MapActivity";
    private static final String FILTER_ALL = "Tutti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setupNavigationDrawer();
        setToolbarTitle("Mappa Servizi");

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "SupportMapFragment non trovato!");
            finish();
            return;
        }
        setupUI();
        loadSampleData();
    }

    private void setupUI() {
        SearchView searchView = findViewById(R.id.searchView);
        AutoCompleteTextView filterView = findViewById(R.id.filterView);
        FloatingActionButton btnAddService = findViewById(R.id.btnAddService);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCentersByQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCentersByQuery(newText);
                return true;
            }
        });

        String[] filterOptions = getResources().getStringArray(R.array.service_filters);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_dropdown_item, filterOptions);
        filterView.setAdapter(filterAdapter);

        filterView.setOnItemClickListener((parent, view, position, id) -> {
            String filter = parent.getItemAtPosition(position).toString();
            filterCentersByType(filter);
        });

        btnAddService.setOnClickListener(v -> {
            AddServiceDialogFragment dialog = new AddServiceDialogFragment();
            dialog.show(getSupportFragmentManager(), "add_service");
        });
    }

    private void loadSampleData() {
        String[] rawData = getResources().getStringArray(R.array.service_centers_data);

        new Thread(() -> {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            for (String entry : rawData) {
                String[] parts = entry.split("\\|");
                if (parts.length == 4) {
                    String name = parts[0];
                    String address = parts[1];
                    String type = parts[2];
                    String phone = parts[3];

                    try {
                        List<Address> result = geocoder.getFromLocationName(address, 1);
                        if (result != null && !result.isEmpty()) {
                            Address location = result.get(0);
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            ServiceCenter center = new ServiceCenter(name, address, type, latLng, phone);
                            serviceCenters.add(center);

                            runOnUiThread(() -> {
                                if (mMap != null) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(latLng)
                                            .title(name)
                                            .snippet(address + "\n" + type + "\n" + phone));
                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.e("GeocodeError", "Errore geocoding per: " + address, e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng italy = new LatLng(41.8719, 12.5674);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(italy, 6));
        showMarkers(serviceCenters);
        mMap.setOnMarkerClickListener(marker -> {
            showCenterDetails(marker.getTitle(), Objects.requireNonNull(marker.getSnippet()));
            return false;
        });
    }

    private void showMarkers(List<ServiceCenter> centers) {
        if (mMap == null) return;
        mMap.clear();
        for (ServiceCenter center : centers) {
            String snippet = center.getAddress() + "\n" + center.getType() + "\n" + center.getPhoneNumber();
            mMap.addMarker(new MarkerOptions()
                    .position(center.getLocation())
                    .title(center.getName())
                    .snippet(snippet));
        }
    }

    private void filterCentersByQuery(String query) {
        if (mMap == null) return;
        if (query == null || query.trim().isEmpty()) {
            showMarkers(serviceCenters);
            return;
        }

        List<ServiceCenter> filtered = new ArrayList<>();
        String queryLower = query.toLowerCase();
        for (ServiceCenter center : serviceCenters) {
            if (center.getName().toLowerCase().contains(queryLower) ||
                    center.getAddress().toLowerCase().contains(queryLower)) {
                filtered.add(center);
            }
        }
        showMarkers(filtered);
    }

    private void filterCentersByType(String type) {
        if (mMap == null) return;
        if (FILTER_ALL.equalsIgnoreCase(type)) {
            showMarkers(serviceCenters);
            return;
        }
        List<ServiceCenter> filtered = new ArrayList<>();
        for (ServiceCenter center : serviceCenters) {
            if (center.getType().equalsIgnoreCase(type)) {
                filtered.add(center);
            }
        }
        showMarkers(filtered);
    }

    private void showCenterDetails(String name, String snippet) {
        String[] lines = snippet.split("\n");
        String address = lines.length > 0 ? lines[0] : "Indirizzo non disponibile";
        String type = lines.length > 1 ? lines[1] : "Tipo non specificato";
        String phone = lines.length > 2 ? lines[2] : "Numero non disponibile";

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_center_details, null);
        TextView nameText = dialogView.findViewById(R.id.textName);
        TextView addressText = dialogView.findViewById(R.id.textAddress);
        TextView phoneText = dialogView.findViewById(R.id.textPhone);
        TextView typeCenterText = dialogView.findViewById(R.id.textCenterType);
        Button btnCall = dialogView.findViewById(R.id.btnCall);
        Button btnClose = dialogView.findViewById(R.id.btnClose);

        nameText.setText(name);
        typeCenterText.setText("Servizio del centro: " + type);
        addressText.setText("Indirizzo: " + address);
        phoneText.setText("Telefono: " + phone);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        btnCall.setOnClickListener(v -> {
            if (phone.equals("Numero non disponibile")) {
                Toast.makeText(this, "Numero non disponibile", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Impossibile avviare la chiamata", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void addNewService(String name, String address, String phone, String type) {
        new Thread(() -> {
            LatLng latLng = geocodeAddress(address);
            runOnUiThread(() -> {
                if (latLng != null) {
                    ServiceCenter center = new ServiceCenter(name, address, type, latLng, phone);
                    serviceCenters.add(center);

                    if (mMap != null) {
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(name)
                                .snippet(address + "\n" + type + "\n" + phone)
                        );
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    }

                    Toast.makeText(this, "Servizio aggiunto!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Indirizzo non trovato", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private LatLng geocodeAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoding fallito", e);
        }
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_map) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}
