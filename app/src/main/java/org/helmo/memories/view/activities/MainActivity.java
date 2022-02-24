package org.helmo.memories.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.helmo.memories.R;
import org.helmo.memories.view.fragments.AddMemoryFragment;
import org.helmo.memories.view.fragments.MemoryListFragment;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Injecter le fragment dans notre boite (fragment_container)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new MemoryListFragment(this));
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public void onAddBtnClicked(View view) {
        add_btn = view.findViewById(R.id.add_btn); // Permet de rendre invisible le bouton +
        add_btn.setVisibility(View.GONE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new AddMemoryFragment(this));
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        add_btn.setVisibility(View.VISIBLE); // Permet de rendre à nouveau visible le bouton + lors du retour en arrière
        super.onBackPressed();
    }
}