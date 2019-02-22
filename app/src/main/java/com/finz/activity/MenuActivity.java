package com.finz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.finz.R;

public class MenuActivity extends AppCompatActivity {
    private Button btnDiposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnDiposition = findViewById(R.id.button_disposition);

        btnDiposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DispositionMoneyActivity.class));
            }
        });
    }


}
