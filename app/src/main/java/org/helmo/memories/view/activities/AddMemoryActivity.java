package org.helmo.memories.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.helmo.memories.R;

public class AddMemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
    }

    public void onAddMemoryBtnClick(View view) {
        //TODO
        finish();
    }
}