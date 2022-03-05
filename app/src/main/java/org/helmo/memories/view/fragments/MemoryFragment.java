package org.helmo.memories.view.fragments;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.helmo.memories.BuildConfig;
import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.presenters.MemoryPresenter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.view.viewmodel.MemoryViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

public class MemoryFragment extends Fragment implements MemoryPresenter.IMemoryScreen {

    View view;
    TextView titleView;
    TextView descriptionView;
    TextView dateView;
    ImageView imageView;
    MapView mapView;

    ImageButton deleteBtn;
    ImageButton favBtn;
    ImageButton shareBtn;

    MainActivity context;
    private int memoryId;

    String path;

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
            memoryPresenter.setFavorite(memoryId);
            memoryPresenter.loadMemory(memoryId);
        });
        shareBtn = view.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(view ->{
            shareImage();
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
    public void showEntireMemory(String title, String description, String imagePath, String date, double lattitude, double longitude, boolean favorit) {
        titleView.setText(title);
        descriptionView.setText(description);
        dateView.setText(date);
        path = imagePath;
        if(imagePath != null) {
            File imgFile = new File(imagePath);
            if(imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                Glide.with(context).load(imageUri).into(imageView); // Remplacer l'image par défaut par le fichier trouvé
            }
        }

        if (favorit){
            favBtn.setImageResource(R.drawable.ic_star);
        }else{
            favBtn.setImageResource(R.drawable.ic_unstar);
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

    private void shareImage() {

        Uri imageUri = Uri.parse(path);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hello");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }


}