package org.helmo.memories.view.fragments;

import android.content.Context;
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
import org.helmo.memories.view.adapter.MemoryListAdapter;

import java.util.List;

public class MemoryListFragment extends Fragment implements MemoryListPresenter.IMemoryListScreen {

    private MemoryListPresenter memoryListPresenter;
    private final MainActivity context;
    List<Memory> memoryList;

    ISelectMemory callback;

    RecyclerView horizontalRecyclerView;

    public interface ISelectMemory {
        void onSelectedMemory(int memoryId);
    }

    public MemoryListFragment(MainActivity context) {
        this.context = context;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Récupérer le recycler view
        horizontalRecyclerView = view.findViewById(R.id.recycler_view);
        horizontalRecyclerView.setAdapter(new MemoryListAdapter(context, null, callback));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memoryListPresenter = new MemoryListPresenter(this);
        memoryListPresenter.loadMemories();
    }

    @Override
    public void refreshView(List<Memory> memories) {
        memoryListPresenter.setMemories(memories);
        horizontalRecyclerView.setAdapter(new MemoryListAdapter(context, memoryListPresenter, callback));
    }

    @Override
    public void refreshView() {
        horizontalRecyclerView.setAdapter(new MemoryListAdapter(context, memoryListPresenter, callback));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (ISelectMemory) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
