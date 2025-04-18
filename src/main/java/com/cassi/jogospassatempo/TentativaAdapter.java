package com.cassi.jogospassatempo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TentativaAdapter extends RecyclerView.Adapter<TentativaAdapter.ViewHolder> {

    Context context;
    private final List<String> tentativas;

    public TentativaAdapter(Context context, List<String> tentativas) {
        this.context = context;
        this.tentativas = tentativas;
    }

    @NonNull
    @Override
    public TentativaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.tentativa_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTentativa.setText(tentativas.get(position));
    }

    @Override
    public int getItemCount() {
        return tentativas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTentativa;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTentativa = itemView.findViewById(R.id.tentativa);
        }
    }
}
