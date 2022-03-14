package org.helmo.memories.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.helmo.memories.R;
import org.helmo.memories.presenters.MemoryListPresenter;
import org.helmo.memories.view.fragments.AddMemoryFragment;
import org.helmo.memories.view.fragments.MemoryFragment;
import org.helmo.memories.view.fragments.MemoryListFragment;

public class MainActivity extends AppCompatActivity implements MemoryListFragment.ISelectMemory {

    FloatingActionButton add_btn;
    SearchView sreachView;

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
        sreachView.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // Démarre le fragment d'ajout d'un souvenir
        transaction.replace(R.id.fragment_container, addMemoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 2) {
            add_btn.setVisibility(View.VISIBLE); // Permet de rendre à nouveau visible le bouton + lors du retour en arrière
            sreachView.setVisibility(View.VISIBLE); // Permet de rendre à nouveau visible le bouton + lors du retour en arrière
        }
        if (count == 1){
            super.onBackPressed();
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public void onSelectedMemory(int memoryId) {
        MemoryFragment memoryFragment = new MemoryFragment(this, memoryId);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, memoryFragment).addToBackStack(null).commit();
        add_btn.setVisibility(View.GONE);
        sreachView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_sreach);
        sreachView = (SearchView) menuItem.getActionView();
        MainActivity context = this;
        sreachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                memoryListPresenter.filterMemory(s.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



}