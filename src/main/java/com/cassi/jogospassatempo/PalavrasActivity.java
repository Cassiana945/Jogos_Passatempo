package com.cassi.jogospassatempo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PalavrasActivity extends AppCompatActivity {

    ImageView btnVoltar, btnOk, btnSortear;
    EditText textPalavra;
    TextView labelSorteada;
    ArrayList <String> palavras = new ArrayList<>();
    Random random = new Random();
    MediaPlayer player;
    Animation zoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palavras);

        btnVoltar = findViewById(R.id.voltar);
        btnOk = findViewById(R.id.ok);
        btnSortear = findViewById(R.id.sortear);
        textPalavra = findViewById(R.id.palavra);
        labelSorteada = findViewById(R.id.sorteada);
        player = MediaPlayer.create(PalavrasActivity.this, R.raw.sortear_som);
        zoom = AnimationUtils.loadAnimation(PalavrasActivity.this, R.anim.zoom_in_out);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String palavra = textPalavra.getText().toString();
                if(palavra.isEmpty()) {
                    Toast.makeText(PalavrasActivity.this, "Digite uma palavra", Toast.LENGTH_SHORT).show();
                    return;
                }
                palavras.add(palavra);
                textPalavra.setText("");
            }
        });

        btnSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                String sorteada = palavras.get(random.nextInt(palavras.size()));
                labelSorteada.setText(sorteada);
                labelSorteada.setVisibility(View.VISIBLE);
                labelSorteada.startAnimation(zoom);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PalavrasActivity.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
