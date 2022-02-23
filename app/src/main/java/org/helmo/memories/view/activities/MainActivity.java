package org.helmo.memories.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.helmo.memories.R;
import org.helmo.memories.view.fragments.MemoryListFragment;

public class MainActivity extends AppCompatActivity {

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
        Intent addMemoryActivity = new Intent(getApplicationContext(), AddMemoryActivity.class);
        startActivity(addMemoryActivity);
    }

}