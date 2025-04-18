package com.cassi.jogospassatempo;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Random;

public class PPTActivity extends AppCompatActivity {

    ImageView imagem, btnPedra, btnPapel, btnTesoura, btnVoltar;
    TextView text;
    MediaPlayer playerVictory, playerDefeat;
    Animation zoom;

    int vitoriaUsuario = 0;
    int vitoriaApp = 0;
    int rodadas = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt);

        imagem = findViewById(R.id.imagem);
        btnPedra = findViewById(R.id.pedra);
        btnPapel = findViewById(R.id.papel);
        btnTesoura = findViewById(R.id.tesoura);
        btnVoltar = findViewById(R.id.voltar);
        text = findViewById(R.id.label2);
        playerVictory = MediaPlayer.create(PPTActivity.this, R.raw.vitoria_som);
        playerDefeat = MediaPlayer.create(PPTActivity.this, R.raw.gameover_som);
        zoom = AnimationUtils.loadAnimation(PPTActivity.this, R.anim.zoom_in_out);


        btnPedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolhaUsuario("pedra");
            }
        });

        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolhaUsuario("papel");
            }
        });

        btnTesoura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolhaUsuario("tesoura");
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPTActivity.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


    public void escolhaUsuario(String escolhaUsuario) {
        int numero = new Random().nextInt(3);
        String[] opcoes = {"pedra", "papel", "tesoura"};
        String escolhaApp = opcoes[numero];

        switch (escolhaApp) {
            case "pedra":
                imagem.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imagem.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imagem.setImageResource(R.drawable.tesoura);
                break;
        }


        if ((escolhaApp.equals("tesoura") && escolhaUsuario.equals("papel")) ||
                (escolhaApp.equals("papel") && escolhaUsuario.equals("pedra")) ||
                (escolhaApp.equals("pedra") && escolhaUsuario.equals("tesoura"))) {
            text.setText("Voce Perdeu!");
            vitoriaApp++;

        } else if ((escolhaUsuario.equals("tesoura") && escolhaApp.equals("papel")) ||
                (escolhaUsuario.equals("papel") && escolhaApp.equals("pedra")) ||
                (escolhaUsuario.equals("pedra") && escolhaApp.equals("tesoura"))) {
            text.setText("Voce Ganhou!");
            vitoriaUsuario++;

        } else {
            text.setText("Empate!");
        }

        rodadas++;

        if (vitoriaUsuario == 2 || vitoriaApp == 2) {
            if (vitoriaUsuario == 2) {
                playerVictory.start();
                text.setText("Voce Venceu a Partida! :)");
                text.setTextColor(0xFF4D8034);
                text.setTypeface(ResourcesCompat.getFont(PPTActivity.this, R.font.moul_regular));
                text.startAnimation(zoom);
            } else {
                playerDefeat.start();
                text.setText("Voce Perdeu a Partida! :(");
                text.setTextColor(0xFF4D8034);
                text.setTypeface(ResourcesCompat.getFont(PPTActivity.this, R.font.moul_regular));
                text.startAnimation(zoom);
            }
            vitoriaUsuario = 0;
            vitoriaApp = 0;
            rodadas = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerVictory != null) {
            playerVictory.release();
            playerVictory = null;
        }
        if (playerDefeat != null) {
            playerDefeat.release();
            playerDefeat = null;
        }
    }

   
}
