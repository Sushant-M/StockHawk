package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LineGraphActivity extends AppCompatActivity {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.change) TextView change;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.graph) ImageView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String sym = intent.getStringExtra("Symbol");
        String ch = intent.getStringExtra("Percent_change");
        String pr = intent.getStringExtra("Bid_price");
        change.setText(ch);
        price.setText(pr);
        name.setText(sym);

        Context context = getApplicationContext();

        String link = "http://chart.finance.yahoo.com/z?s="+sym+"&t=6m&q=l&l=on&z=s&p=m50,m200";

        Picasso.with(context).load(link).into(graph);

    }

}
