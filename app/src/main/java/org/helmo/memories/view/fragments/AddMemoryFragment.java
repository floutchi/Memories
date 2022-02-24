package org.helmo.memories.view.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.helmo.memories.R;

public class AddMemoryFragment extends Fragment {

    View view;

    Button add_date;
    Button add_place;
    Button add_pic;

    Button add_memory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_memory_fragment, container, false);

        // Recherche des boutons
        add_date = view.findViewById(R.id.addDateBtn);
        add_place = view.findViewById(R.id.addPlaceBtn);
        add_pic = view.findViewById(R.id.addPicBtn);
        add_memory = view.findViewById(R.id.addMemoryBtn);

        // Set onClick des boutons
        //TODO


        return view;
    }
}