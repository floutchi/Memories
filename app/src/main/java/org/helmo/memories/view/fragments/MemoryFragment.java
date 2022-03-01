package org.helmo.memories.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryPresenter;
import org.helmo.memories.view.viewmodel.MemoryViewModel;

public class MemoryFragment extends Fragment implements MemoryPresenter.IMemoryScreen {

    View view;
    TextView titleView;
    TextView descriptionView;
    TextView dateView;
    ImageView imageView;
    MapView mapView;


    private int memoryId;

    private MemoryPresenter memoryPresenter;

    public static MemoryFragment newInstance(int memoryId) {
        MemoryFragment memoryFragment = new MemoryFragment();
        memoryFragment.memoryId = memoryId;
        return memoryFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memory_fragment, container, false);
        titleView = view.findViewById(R.id.titleMemory);
        descriptionView = view.findViewById(R.id.descriptionMemory);
        dateView = view.findViewById(R.id.dateMemory);
        imageView = view.findViewById(R.id.imageMemory);

        MemoryViewModel memoryViewModel = new ViewModelProvider(requireActivity()).get(MemoryViewModel.class);
        if(memoryViewModel.getMemoryId() == 0 || memoryId != memoryViewModel.getMemoryId()) {
            memoryViewModel.setMemoryId(memoryId);
        } else {
            memoryId = memoryViewModel.getMemoryId();
        }
        memoryPresenter = new MemoryPresenter(this);
        memoryPresenter.loadMemory(memoryId);

        return view;
    }

    @Override
    public void showEntireMemory(String title) {
        titleView.setText(title); //TODO
    }
}