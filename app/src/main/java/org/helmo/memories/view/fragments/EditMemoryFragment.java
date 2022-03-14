package org.helmo.memories.view.fragments;

import static org.helmo.memories.view.fragments.AddMemoryFragment.DIALOG_DATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryPresenter;
import org.helmo.memories.utils.DateFormatter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.view.activities.MapsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EditMemoryFragment extends Fragment  {

    //TODO : Impossible de changer l'image ???


    View view;
    String title; String description;
    Uri imageUri;
    String date;
    double lattitude; double longitude;

    ImageView image;
    String memoryImagePath;

    EditText add_name;
    EditText add_description;

    Button add_date;
    Button add_place;
    Button add_pic;
    Button take_pic;

    Button edit_memory;

    MainActivity context;
    MemoryPresenter memoryPresenter;

    ActivityResultLauncher<Intent> takeImageArl;
    ActivityResultLauncher<Intent> pickImageArl;
    ActivityResultLauncher<Intent> pickPlaceArl;

    public EditMemoryFragment(MainActivity context, MemoryPresenter memoryPresenter, String title, String description,
                              Uri imageUri, String date,
                              double lattitude, double longitude) {

        this.context = context;
        this.memoryPresenter = memoryPresenter;
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;
        this.memoryImagePath = imageUri.getPath();
        this.date = date;
        this.lattitude = lattitude;
        this.longitude = longitude;
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
        edit_memory = view.findViewById(R.id.addMemoryBtn);

        // Récupérer les EditText
        add_name = view.findViewById(R.id.addName);
        add_description = view.findViewById(R.id.addDescription);

        // Récupérer l'image
        image = view.findViewById(R.id.memoryImage);

        // Modifier les textes
        edit_memory.setText(getResources().getString(R.string.edit_memory));
        add_name.setText(title);
        add_description.setText(description);
        if(!date.isEmpty()) {
            add_date.setText(date);
        }

        image.setVisibility(View.VISIBLE);
        Glide.with(context).load(imageUri).into(image);

        if(lattitude != 0 && longitude != 0) {
            add_place.setText("Coordonées : LAT " + lattitude + "LON " + longitude);
        }

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

        edit_memory.setOnClickListener(view -> editMemory());

        return view;
    }

    private void editMemory() {
        try {
            memoryPresenter.editMemory(add_name.getText().toString(), add_description.getText().toString(), memoryImagePath, add_date.getText().toString(), this.lattitude, this.longitude);
            context.onBackPressed();
        } catch (Exception e) {
            new AlertDialog.Builder(context)
                    .setTitle("Erreur")
                    .setMessage(e.getMessage())
                    .show();
        }
    }

    private void selectPlace() {

        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // Lance l'activité pour choisir un lieu
            Intent intent = new Intent(context, MapsActivity.class);
            pickPlaceArl.launch(intent);
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)){
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(context, permissions, 2);
            }
        }
    }

    private void selectDate() {
        // Lance le fragment pour choisir une date
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(DateFormatter.toDate(dateFormat.format(date)));
        getParentFragmentManager().setFragmentResultListener(DatePickerFragment.RESULT_DATE, EditMemoryFragment.this, (requestKey, bundle) -> {
            Date result = (Date) bundle.getSerializable("bundleKey");
            add_date.setText(DateFormatter.fromDate(result));
        });

        datePickerFragment.show(EditMemoryFragment.this.getParentFragmentManager(), DIALOG_DATE);
    }

    private void pickupImage() {

        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageArl.launch(intent);
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)){
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(context, permissions, 2);
            }
        }

    }

    private void takeImage() {
        // Lance l'activité pour prendre une photo
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takeImageArl.launch(takePicture);
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)){
                String[] permissions = {Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(context, permissions, 2);
            }
        }

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
            image.setImageURI(selectedImage);
            image.setVisibility(View.VISIBLE);
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
                image.setImageBitmap(imageBitmap);
                image.setVisibility(View.VISIBLE);
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
                saveImage(imageBitmap);
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
     * Permet de sauvegarder une image
     * @param imageBitmap image à sauvegarder
     */
    private void saveImage(Bitmap imageBitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File dir = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(dir, UUID.randomUUID()+".png");
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
