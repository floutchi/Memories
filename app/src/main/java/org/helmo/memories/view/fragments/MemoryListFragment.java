package org.helmo.memories.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.view.activities.MainActivity;
import org.helmo.memories.R;
import org.helmo.memories.model.Memory;
import org.helmo.memories.view.adapter.MemoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemoryListFragment extends Fragment implements MemoryListPresenter.IMemoryListScreen {

    private final MainActivity context;
    List<Memory> memoryList = new ArrayList<>();

    public MemoryListFragment(MainActivity context) {
        this.context = context;
    }


    public interface ISelectedMemory {
        void onSelectedMemory(int id);
    }


    public static MemoryListFragment newInstance() {
        MemoryListFragment fragment = new MemoryListFragment(null); //TODO
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //memoryList.add(new Memory("jsp", "jsp", "", "", false));

        // Récupérer le recycler view
        RecyclerView horizontalRecyclerView = view.findViewById(R.id.recycler_view);
        horizontalRecyclerView.setAdapter(new MemoryAdapter(context, memoryList, R.id.recycler_view));
        return view;
    }

    @Override
    public void refreshView() {

    }
}
