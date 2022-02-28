package org.helmo.memories.view.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.maps.model.LatLng;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.utils.DateFormatter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.view.activities.MapsActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AddMemoryFragment extends Fragment {

    protected static final String DIALOG_DATE = "DialogDate";
    View view;

    EditText add_name;
    EditText add_description;

    Button add_date;
    Button add_place;
    double longitude;
    double lattitude;
    Button add_pic;
    Button take_pic;

    ImageView memory_image;
    String memoryImagePath;

    Button add_memory;

    MainActivity context;
    MemoryListPresenter memoryListPresenter;

    ActivityResultLauncher<Intent> takeImageArl;
    ActivityResultLauncher<Intent> pickImageArl;
    ActivityResultLauncher<Intent> pickPlaceArl;

    public AddMemoryFragment(MainActivity context, MemoryListPresenter memoryListPresenter) {
        this.context = context;
        this.memoryListPresenter = memoryListPresenter;
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

        // Récupérer les EditText
        add_name = view.findViewById(R.id.addName);
        add_description = view.findViewById(R.id.addDescription);

        // Récupérer l'image
        memory_image = view.findViewById(R.id.memoryImage);

        // Set onClick des boutons
        // Ajout de la date
        add_date.setOnClickListener(view -> selectDate());
        // Ajout de l'image
        pickImageArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::pickImageResult);
        takeImageArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::takePhotoResult);
        pickPlaceArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::takePlaceResult);
        add_pic.setOnClickListener(view -> pickupImage());
        take_pic.setOnClickListener(view -> takeImage());
        // Ajout du lieu
        add_place.setOnClickListener(view -> selectPlace());


        // Bouton final d'ajout du souvenir
        add_memory.setOnClickListener(view -> addMemory());
        return view;
    }

    private void addMemory() {
        memoryListPresenter.addMemory(add_name.getText().toString(), add_description.getText().toString(), memoryImagePath, add_date.getText().toString(), this.lattitude, this.longitude);
        add_name.setText("");
        add_description.setText("");
        memoryImagePath = "";
        add_date.setText("");
        pickImageArl = null;
        context.onBackPressed();
    }


    private void selectPlace() {
        // Lance l'activité pour choisir un lieu
        Intent intent = new Intent(context, MapsActivity.class);
        pickPlaceArl.launch(intent);
    }

    private void selectDate() {
        // Lance le fragment pour choisir une date
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

        // Lance l'activité pour choisir une image
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageArl.launch(intent);
    }

    private void takeImage() {
        // Lance l'activité pour prendre une photo
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takeImageArl.launch(takePicture);
    }

    private void takePlaceResult(ActivityResult result) {
        // Résultat de l'activité de l'ajout d'un lieu
        Intent data = result.getData();

        if(result.getResultCode() == Activity.RESULT_OK) {
            LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");
            this.lattitude = latLng.latitude; // A ajouter à l'objet Memory lors de l'ajout
            this.longitude = latLng.longitude; // A ajouter à l'objet Memory lors de l'ajout
            NumberFormat formatter = new DecimalFormat("0.00000");
            add_place.setText("Coordonnées: lat " + formatter.format(lattitude) + " lon " + formatter.format(longitude));

        }
    }

    private void pickImageResult(ActivityResult result) {
        // Résultat de l'activité pour choisir une image
        Intent data = result.getData();
        // Vérifier si les données sont nulles
        if(data != null || data.getData() != null) {
            Uri selectedImage = result.getData().getData();

            // Mettre à jour l'aperçu de l'image
            memory_image.setImageURI(selectedImage);
            memory_image.setVisibility(View.VISIBLE);
            memoryImagePath = getRealPathFromURI(selectedImage); // A ajouter à l'objet Memory lors de l'ajout
        }
    }

    private void takePhotoResult(ActivityResult result) {
        // Resultat de l'activité pour prendre une photo
        Intent data = result.getData();
        if(result.getResultCode() == Activity.RESULT_OK) {
            if(data != null || data.getData() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Mettre à jour l'aperçu de l'image
                memory_image.setImageBitmap(imageBitmap);
                memory_image.setVisibility(View.VISIBLE);
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
                saveImage(imageBitmap); //TODO Sauvegarder l'image ne fonctionne pas ??
                // Ajouter le path de l'image sauvegardée dans l'objet Memory lors de l'ajout
            }
        }
    }

    /**
     * Permet de récupérer le chemin d'une image sélectionnée
     * @param contentURI URI de l'image
     * @return le chemin sous forme de string de l'image
     */
    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    /**
     * Permet de sauvegarder une image //TODO NE FONCTIONNE PAS
     * @param imageBitmap image à sauvegarder
     */
    private void saveImage(Bitmap imageBitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File dir = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(dir, UUID.randomUUID()+".png");
        System.err.println( "test : "+path.toString());
        try {
            memoryImagePath = path.toString();
            FileOutputStream fos = new FileOutputStream(path);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}