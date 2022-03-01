package org.helmo.memories.view.adapter;

import android.Manifest;
import android.app.Activity;
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

import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.presenters.MemoryPresenter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.R;
import org.helmo.memories.model.Memory;
import org.helmo.memories.view.fragments.MemoryFragment;
import org.helmo.memories.view.fragments.MemoryListFragment;

import java.io.File;
import java.util.List;

public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.ViewHolder> {

    MemoryListPresenter memoryListPresenter;
    private final MainActivity context;
    MemoryListFragment.ISelectMemory callBacks;

    public MemoryListAdapter(MainActivity context, MemoryListPresenter memoryListPresenter, MemoryListFragment.ISelectMemory callBacks) {
        this.context = context;
        this.memoryListPresenter = memoryListPresenter;
        this.callBacks = callBacks;
    }


    // Boite pour ranger tous les composants à controller
    public static class ViewHolder extends RecyclerView.ViewHolder implements MemoryListPresenter.IMemoryItemScreen {
        Activity context;
        MemoryListFragment.ISelectMemory callbacks;
        private ImageView memoryImage;
        private TextView memoryTitle;
        private TextView memoryDescription;
        private TextView memoryDate;
        private int id;
        ViewHolder(View view, Activity context, MemoryListFragment.ISelectMemory callbacks) {
            super(view);
            this.context = context;
            this.callbacks = callbacks;
            this.memoryImage = view.findViewById(R.id.memoryImage);
            this.memoryTitle = view.findViewById(R.id.memoryTitle);
            this.memoryDescription = view.findViewById(R.id.memoryDescription);
            this.memoryDate = view.findViewById(R.id.memoryDate);

            view.setOnClickListener(v -> {
                callbacks.onSelectedMemory(id);
            });
        }

        @Override
        public void showMemory(int id, String title, String description, String imagePath, String date) {
            this.id = id;

            memoryTitle.setText(title);
            memoryDescription.setText(description);
            memoryDate.setText(date);

            if(imagePath != null) {
                File imgFile = new File(imagePath); // Récupérer le chemin de l'image
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
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memory, parent, false);
        return new ViewHolder(view, context, callBacks);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            memoryListPresenter.showMemoryOn(holder, position);
    }

    @Override
    public int getItemCount() {
        if(memoryListPresenter == null) {
            return 0;
        } else {
            return memoryListPresenter.getItemCount();
        }
    }

    public Context getContext() {
        return context;
    }
}
