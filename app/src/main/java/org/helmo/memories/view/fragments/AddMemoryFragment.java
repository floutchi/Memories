package org.helmo.memories.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import org.helmo.memories.R;
import org.helmo.memories.utils.DateFormatter;
import org.helmo.memories.view.activities.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddMemoryFragment extends Fragment {

    protected static final String DIALOG_DATE = "DialogDate";
    View view;

    Button add_date;
    Button add_place;
    Button add_pic;
    Button take_pic;

    ImageView memory_image;
    String memoryImagePath;

    Button add_memory;
    MainActivity context;

    ActivityResultLauncher<Intent> takeImageArl;
    ActivityResultLauncher<Intent> pickImageArl;

    public AddMemoryFragment(MainActivity context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_memory_fragment, container, false);

        // Recuperer les boutons
        add_date = view.findViewById(R.id.addDateBtn);
        add_place = view.findViewById(R.id.addPlaceBtn);
        add_pic = view.findViewById(R.id.addPicBtn);
        take_pic = view.findViewById(R.id.takePicBtn);
        add_memory = view.findViewById(R.id.addMemoryBtn);

        // Récupérer l'image
        memory_image = view.findViewById(R.id.memoryImage);

        // Set onClick des boutons
        // Ajout de la date
        add_date.setOnClickListener(view -> selectDate());
        // Ajout de l'image
        pickImageArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::pickImageResult);
        takeImageArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::takePhotoResult);
        add_pic.setOnClickListener(view -> pickupImage());
        take_pic.setOnClickListener(view -> takeImage());
        // Ajout du lieu
        add_place.setOnClickListener(view -> selectPlace());

        return view;
    }


    private void selectPlace() {

    }

    private void selectDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(DateFormatter.toDate(dateFormat.format(date)));
        getParentFragmentManager().setFragmentResultListener(DatePickerFragment.RESULT_DATE, AddMemoryFragment.this, (requestKey, bundle) -> {
            Date result = (Date) bundle.getSerializable("bundleKey");
            add_date.setText(DateFormatter.fromDate(result));
        });

        datePickerFragment.show(AddMemoryFragment.this.getParentFragmentManager(), DIALOG_DATE);
    }

    private void pickupImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        pickImageArl.launch(intent);
    }

    private void takeImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takeImageArl.launch(takePicture);
    }

    private void pickImageResult(ActivityResult result) {
        Intent data = result.getData();
        // Vérifier si les données sont nulles
        if(data != null || data.getData() != null) {
            Uri selectedImage = result.getData().getData();

            // Mettre à jour l'aperçu de l'image
            memory_image.setImageURI(selectedImage);
            memory_image.setVisibility(View.VISIBLE);
        }
    }

    private void takePhotoResult(ActivityResult result) {
        Intent data = result.getData();
        if(result.getResultCode() == Activity.RESULT_OK) {
            if(data != null || data.getData() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Mettre à jour l'aperçu de l'image
                memory_image.setImageBitmap(imageBitmap);
                memory_image.setVisibility(View.VISIBLE);
                saveImage(imageBitmap); //TODO Sauvegarder l'image ??
            }
        }
    }

    private void saveImage(Bitmap imageBitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File dir = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(dir, "memory.png");

        try {
            FileOutputStream fos = new FileOutputStream(path);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}