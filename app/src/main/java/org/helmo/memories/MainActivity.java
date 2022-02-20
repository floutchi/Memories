package org.helmo.memories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.helmo.memories.view.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;

    private FloatingActionButton add_btn;
    private Button add_theme_btn;
    private Button add_souvenir_btn;

    private boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupérer les animations
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

        // Récupérer les boutons
        add_btn = findViewById(R.id.add_btn);
        add_theme_btn = findViewById(R.id.add_theme_btn);
        add_souvenir_btn = findViewById(R.id.add_souvenir_btn);

        // Injecter le fragment dans notre boite (fragment_container)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public void onAddBtnClicked(View view) {
        if(!clicked) {
            // Set Visibility
            add_theme_btn.setVisibility(View.VISIBLE);
            add_souvenir_btn.setVisibility(View.VISIBLE);

            // Set Animation
            add_btn.startAnimation(rotateOpen);
        } else {
            // Set visibility
            add_theme_btn.setVisibility(View.GONE);
            add_souvenir_btn.setVisibility(View.GONE);

            // Set Animation
            add_btn.startAnimation(rotateClose);
        }
        clicked = !clicked;

    }

    public void onAddSouvenirBtnClicked(View view) {
        //TODO
    }

    public void onAddThemeBtnClicked(View view) {
        //TODO
    }
}