package org.helmo.memories.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Delete;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.presenters.MemoryPresenter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.view.viewmodel.MemoryViewModel;

import java.io.File;

public class MemoryFragment extends Fragment implements MemoryPresenter.IMemoryScreen {

    View view;
    TextView titleView;
    TextView descriptionView;
    TextView dateView;
    ImageView imageView;
    MapView mapView;

    ImageButton deleteBtn;
    ImageButton favBtn;

    MainActivity context;
    private int memoryId;

    private MemoryPresenter memoryPresenter;
    private MemoryListPresenter memoryListPresenter;

    public MemoryFragment(MainActivity context, int memoryId) {
        this.context = context;
        this.memoryId = memoryId;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memory_fragment, container, false);
        titleView = view.findViewById(R.id.titleMemory);
        descriptionView = view.findViewById(R.id.descriptionMemory);
        dateView = view.findViewById(R.id.dateMemory);
        imageView = view.findViewById(R.id.imageMemory);

        deleteBtn = view.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(view -> {
           memoryPresenter.deleteMemory(memoryId);
           context.onBackPressed();
        });
        favBtn = view.findViewById(R.id.favoriteBtn);
        favBtn.setOnClickListener(view -> {
            //TODO
        });


        MemoryViewModel memoryViewModel = new ViewModelProvider(requireActivity()).get(MemoryViewModel.class);
        if(memoryViewModel.getMemoryId() == 0 || memoryId != memoryViewModel.getMemoryId()) {
            memoryViewModel.setMemoryId(memoryId);
        } else {
            memoryId = memoryViewModel.getMemoryId();
        }
        memoryPresenter = new MemoryPresenter(this, memoryListPresenter);
        memoryPresenter.loadMemory(memoryId);

        return view;
    }

    @Override
    public void showEntireMemory(String title, String description, String imagePath, String date, double lattitude, double longitude) {
        titleView.setText(title);
        descriptionView.setText(description);
        dateView.setText(date);

        if(imagePath != null) {
            File imgFile = new File(imagePath);
            if(imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                Glide.with(context).load(imageUri).into(imageView); // Remplacer l'image par défaut par le fichier trouvé
            }
        }

        if(lattitude != 0.0 && longitude != 0.0) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapMemory);

            supportMapFragment.getMapAsync(googleMap -> {
                LatLng pos = new LatLng(lattitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(pos));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.0f));
            });
        }
    }
}