package com.cassi.jogospassatempo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SucessoActivity extends AppCompatActivity {

    ImageView btnVoltar;
    TextView textTentativas, textTempo, textTitulo;
    MediaPlayer player;
    Animation zoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);


        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Intent i = getIntent();
        int tentativas = i.getIntExtra("tentativas", 0);
        long tempo = i.getLongExtra("tempo", 0);

        btnVoltar = findViewById(R.id.voltar);
        textTentativas = findViewById(R.id.tentativas);
        textTempo = findViewById(R.id.tempo);
        textTitulo = findViewById(R.id.titulo);
        player = MediaPlayer.create(SucessoActivity.this, R.raw.vitoria_som);
        zoom = AnimationUtils.loadAnimation(SucessoActivity.this, R.anim.zoom_in_out);

        textTitulo.startAnimation(zoom);
        textTentativas.setText(String.valueOf(tentativas));
        textTempo.setText((tempo / 1000) + "s");
        player.start();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SucessoActivity.this, TermoActivity.class);
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
