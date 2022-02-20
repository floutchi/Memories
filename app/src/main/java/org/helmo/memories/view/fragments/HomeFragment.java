package org.helmo.memories.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.helmo.memories.R;
import org.helmo.memories.view.MemoryAdapter;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Récupérer le recycler view
        RecyclerView horizontalRecyclerView = view.findViewById(R.id.recycler_view);
        horizontalRecyclerView.setAdapter(new MemoryAdapter());
        return view;
    }
}
