package org.helmo.memories.view.adapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.R;
import org.helmo.memories.model.Memory;

import java.io.File;
import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {

    private final MainActivity context;
    private final List<Memory> memoryList;
    private final int layoutId;

    public MemoryAdapter(MainActivity context, List<Memory> memoryList, int layoutId) {
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

            File imgFile = new File(currentMemory.getImagePath()); // Récupérer le chemin de l'image
            // Vérifier les droits d'accès au fichier
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
            // Vérifier que le fichier existe toujours
            if(imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                Glide.with(context).load(imageUri).into(memoryImage); // Remplacer l'image par défaut par le fichier trouvé
            }
        }

    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }
}
