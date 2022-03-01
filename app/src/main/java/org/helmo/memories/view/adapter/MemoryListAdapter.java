package org.helmo.memories.view.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.R;
import org.helmo.memories.model.Memory;
import org.helmo.memories.view.fragments.MemoryFragment;

import java.io.File;
import java.util.List;

public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.ViewHolder> {

    private final MainActivity context;
    private final List<Memory> memoryList;
    private final int layoutId;

    public MemoryListAdapter(MainActivity context, List<Memory> memoryList, int layoutId) {
        this.context = context;
        this.memoryList = memoryList;
        this.layoutId = layoutId;
    }


    // Boite pour ranger tous les composants à controller
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView memoryImage;
        TextView memoryTitle;
        TextView memoryDescription;
        TextView memoryDate;
        ViewHolder(View view) {
            super(view);
            this.memoryImage = view.findViewById(R.id.memoryImage);
            this.memoryTitle = view.findViewById(R.id.memoryTitle);
            this.memoryDescription = view.findViewById(R.id.memoryDescription);
            this.memoryDate = view.findViewById(R.id.memoryDate);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Récupérer l'objet mémory courant
        Memory currentMemory = memoryList.get(position);
        // Récupérer les view de l'item
        ImageView memoryImage = holder.memoryImage;
        TextView memoryTitle = holder.memoryTitle;
        TextView memoryDescription = holder.memoryDescription;
        TextView memoryDate = holder.memoryDate;
        if(memoryImage != null && memoryTitle != null && memoryDescription != null && memoryDate != null) {
            memoryTitle.setText(currentMemory.getTitle());
            memoryDescription.setText(currentMemory.getDescription());
            memoryDate.setText(currentMemory.getDate());

            if(currentMemory.getImagePath() != null) {
                File imgFile = new File(currentMemory.getImagePath()); // Récupérer le chemin de l'image
                // Vérifier les droits d'accès au fichier
                while(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
                // Vérifier que le fichier existe toujours
                if(imgFile.exists()) {
                    Uri imageUri = Uri.fromFile(imgFile);
                    Glide.with(context).load(imageUri).into(memoryImage); // Remplacer l'image par défaut par le fichier trouvé
                }
            }


            // Interraction lors du clic sur un souvenir
            holder.itemView.setOnClickListener(view -> {
                // Afficher le fragmen du souvenir
                displayMemory(currentMemory);
            });
        }
    }

    private void displayMemory(Memory currentMemory) {
        MemoryFragment memoryFragment = new MemoryFragment(context, currentMemory);
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, memoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public Context getContext() {
        return context;
    }
}
