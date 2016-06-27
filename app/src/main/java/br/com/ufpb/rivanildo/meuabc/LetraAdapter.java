package br.com.ufpb.rivanildo.meuabc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rivanildo on 20/04/16.
 */
public class
        LetraAdapter extends RecyclerView.Adapter<LetraAdapter.LetraViewHolder> {

    private List<Letra> letras;
    private Context context;

    public LetraAdapter(Context context) {
        this.context = context;
        MeuABCApplication application = (MeuABCApplication)context.getApplicationContext();
        letras = application.getLetras();
    }

    @Override
    public LetraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_letra_adapter, parent, false);
        return new LetraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LetraViewHolder holder, final int position) {
        Letra l = letras.get(position);
        holder.texto.setText(l.getTitulo());

        holder.toque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("idx", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return letras.size();
    }

    public class LetraViewHolder extends RecyclerView.ViewHolder {
        private View toque;
        private TextView texto;

        public LetraViewHolder(View itemView) {
            super(itemView);
            toque = itemView.findViewById(R.id.toque);
            texto = (TextView)itemView.findViewById(R.id.lMenu);
        }
    }
}
