package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LineGraphActivity extends AppCompatActivity {

    @BindView(R.id.Symbol)TextView Symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String sym = intent.getStringExtra("Symbol");
        Symbol.setText(sym);
    }
}