package org.helmo.memories.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.helmo.memories.R;
import org.helmo.memories.model.Memory;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.view.adapter.MemoryListAdapter;

import java.io.File;


public class MemoryFragment extends Fragment {

    View view;
    private final MainActivity context;
    private MemoryListAdapter adapter;
    private final Memory currentMemory;


    //TODO instancier un presenter pour le reste des boutons

    public MemoryFragment(MainActivity context, Memory currentMemory) {
        this.context = context;
        this.currentMemory = currentMemory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memory_fragment, container, false);
        setupComponents();
        return view;
    }

    private void setupComponents() {
        // Récupérer les composants
        ImageView memoryImage = view.findViewById(R.id.imageMemory);
        TextView memoryTitle = view.findViewById(R.id.titleMemory);
        TextView memoryDescription = view.findViewById(R.id.descriptionMemory);
        TextView memoryDate = view.findViewById(R.id.dateMemory);

        // Actualiser le titre du souvenir
        memoryTitle.setText(currentMemory.getTitle());
        //TODO faire de même pour le reste

        // Actualiser l'image du souvenir
        if (currentMemory.getImagePath() != null) {
            File imgFile = new File(currentMemory.getImagePath()); // Récupérer le chemin de l'image
            // Vérifier que le fichier existe toujours
            if (imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                Glide.with(context).load(imageUri).into(memoryImage); // Remplacer l'image par défaut par le fichier trouvé
            }
        }
    }
}