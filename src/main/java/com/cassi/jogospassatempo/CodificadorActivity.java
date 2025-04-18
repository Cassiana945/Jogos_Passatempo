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

import java.util.Locale;

public class CodificadorActivity extends AppCompatActivity {

    ImageView btnOK, btnDecodificar, btnVoltar;
    TextView resultado;
    EditText textMensagem, textChave;
    MediaPlayer player;
    Animation zoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificador);

        btnOK = findViewById(R.id.ok);
        btnDecodificar = findViewById(R.id.decodificar);
        btnVoltar = findViewById(R.id.voltar);
        resultado = findViewById(R.id.resultado);
        textMensagem = findViewById(R.id.textMensagem);
        textChave = findViewById(R.id.textChave);
        player = MediaPlayer.create(CodificadorActivity.this, R.raw.codificando_som);
        zoom = AnimationUtils.loadAnimation(CodificadorActivity.this, R.anim.zoom_in_out);


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textMensagem.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CodificadorActivity.this, "Digite uma mensagem", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (textChave.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CodificadorActivity.this, "Insira a chave!", Toast.LENGTH_SHORT).show();
                    return;
                }

                player.start();
                String mens = textMensagem.getText().toString();
                int chave = Integer.parseInt(textChave.getText().toString().trim());
                StringBuilder resultadoCifrado = new StringBuilder();

                for (int i = 0; i < mens.length(); i++) {
                    char c = mens.charAt(i);

                    if (c >= 'a' && c <= 'z') {
                        char novoChar = (char) ((c - 'a' + chave) % 26 + 'a');
                        resultadoCifrado.append(novoChar);
                    } else if (c >= 'A' && c <= 'Z') {
                        char novoChar = (char) ((c - 'A' + chave) % 26 + 'A');
                        resultadoCifrado.append(novoChar);
                    } else {
                        resultadoCifrado.append(c);
                    }
                }
                resultado.setVisibility(View.VISIBLE);
                resultado.setText(resultadoCifrado.toString());
                resultado.startAnimation(zoom);
            }
        });


        btnDecodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                String mens = textMensagem.getText().toString();
                resultado.setText(mens);
                resultado.startAnimation(zoom);
            }
        });


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CodificadorActivity.this, SecondActivity.class);
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
