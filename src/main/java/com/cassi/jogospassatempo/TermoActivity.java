package com.cassi.jogospassatempo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TermoActivity extends AppCompatActivity {

    ImageView btnVoltar, btnOk;
    EditText textNumero;
    RecyclerView recyclerView;

    private String codigoSecreto;
    private long tempoInicial;
    private int tentativas = 0;
    private final List<String> listaTentativas = new ArrayList<>();
    private TentativaAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termo);

        btnVoltar = findViewById(R.id.voltar);
        btnOk = findViewById(R.id.ok);
        textNumero = findViewById(R.id.numero);
        recyclerView = findViewById(R.id.recycler);

        codigoSecreto = String.valueOf(100 + new Random().nextInt(900));
        tempoInicial = System.currentTimeMillis();

        adapter = new TentativaAdapter(TermoActivity.this, listaTentativas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentar = textNumero.getText().toString();
                if (tentar.length() != 3) {
                    Toast.makeText(TermoActivity.this, "Digite 3 números", Toast.LENGTH_SHORT).show();
                    return;
                }

                tentativas++;
                String resultado = verificarPalpite(tentar);
                listaTentativas.add(tentar + " --▶ " + resultado);
                adapter.notifyItemInserted(listaTentativas.size() - 1);
                textNumero.setText("");

                if (tentar.equals(codigoSecreto)) {
                    long tempoFinal = System.currentTimeMillis();
                    Intent intent = new Intent(TermoActivity.this, SucessoActivity.class);
                    intent.putExtra("tempo", tempoFinal - tempoInicial);
                    intent.putExtra("tentativas", tentativas);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }



        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermoActivity.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


    private String verificarPalpite(String tentar) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char t = tentar.charAt(i);
            if (t == codigoSecreto.charAt(i)) {
                resultado.append("\n+ um valor correto");
            } else if (codigoSecreto.contains(String.valueOf(t))) {
                resultado.append("\nvalor no lugar errado");
            } else {
                resultado.append("\nvalor errado");
            }
        }
        return resultado.toString();
    }
}

