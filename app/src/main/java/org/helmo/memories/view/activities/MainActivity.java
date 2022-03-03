package org.helmo.memories.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.view.fragments.AddMemoryFragment;
import org.helmo.memories.view.fragments.MemoryFragment;
import org.helmo.memories.view.fragments.MemoryListFragment;

public class MainActivity extends AppCompatActivity implements MemoryListFragment.ISelectMemory {

    FloatingActionButton add_btn;

    MemoryListFragment memoryListFragment;
    AddMemoryFragment addMemoryFragment;
    MemoryListPresenter memoryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancier les attributs
        add_btn = this.findViewById(R.id.add_btn);
        memoryListFragment = new MemoryListFragment(this);
        memoryListPresenter = new MemoryListPresenter(memoryListFragment);
        memoryListPresenter.loadMemories();
        memoryListFragment.setMemoryList(memoryListPresenter.getMemoryList());
        addMemoryFragment = new AddMemoryFragment(this, memoryListPresenter);
        // Injecter le fragment dans notre boite (fragment_container)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, memoryListFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public void onAddBtnClicked(View view) {
        // Permet de rendre invisible le bouton +
        add_btn.setVisibility(View.GONE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // Démarre le fragment d'ajout d'un souvenir
        transaction.replace(R.id.fragment_container, addMemoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        add_btn.setVisibility(View.VISIBLE); // Permet de rendre à nouveau visible le bouton + lors du retour en arrière
        super.onBackPressed();
    }

    @Override
    public void onSelectedMemory(int memoryId) {
        MemoryFragment memoryFragment = new MemoryFragment(this, memoryId);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, memoryFragment).addToBackStack(null).commit();
        add_btn.setVisibility(View.GONE);
    }
}