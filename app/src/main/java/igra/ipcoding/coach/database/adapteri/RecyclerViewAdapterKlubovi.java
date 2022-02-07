package igra.ipcoding.coach.database.adapteri;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.entity.Klub;


public class RecyclerViewAdapterKlubovi extends RecyclerView.Adapter<RecyclerViewAdapterKlubovi.MyViewHolder>{

    List<Klub> klubList;
    Context context;
    String klub;

    public RecyclerViewAdapterKlubovi(Context context, String klub) {
        this.klub = klub;
        this.context = context;
    }

    public void setKlubList(List<Klub> klubList) {
        this.klubList = klubList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterKlubovi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.klub, parent, false);

        return  new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int pobede = klubList.get(position).getPobede();
        int neresene = klubList.get(position).getNeresene();
        int porazi = klubList.get(position).getPorazi();
        int mecevi = pobede + neresene + porazi;
        String imeKluba = klubList.get(position).getIme();
        holder.tv_pozicijaTabela.setText(String.valueOf(klubList.get(position).getPozicija()));
        holder.tv_klubTabela.setText(imeKluba);
        holder.tv_odigraniTabela.setText(String.valueOf(mecevi));
        holder.tv_pobedeTabela.setText(String.valueOf(pobede));
        holder.tv_nereseneTabela.setText(String.valueOf(neresene));
        holder.tv_poraziTabela.setText(String.valueOf(porazi));
        holder.tv_datiTabela.setText(String.valueOf(klubList.get(position).getDatiGolovi()));
        holder.tv_primljeniTabela.setText(String.valueOf(klubList.get(position).getPrimljeniGolovi()));
        holder.tv_golRazlikaTabela.setText(String.valueOf(klubList.get(position).getGolRazlika()));
        holder.tv_poeniTabela.setText(String.valueOf(klubList.get(position).getBodovi()));

        if(position < 4) {
            holder.itemView.setBackgroundColor(Color.argb(100,100,250,100));
            if(imeKluba.matches(klub)) {
                holder.itemView.setBackgroundColor(Color.argb(250,100,250,100));
            }
        } else {
            if(position > 15) {
                holder.itemView.setBackgroundColor(Color.argb(100,250,100,100));
                if(imeKluba.matches(klub)) {
                    holder.itemView.setBackgroundColor(Color.argb(250,250,100,100));
                }
            } else {
                holder.itemView.setBackgroundColor(Color.argb(0,0,0,0));
                if(imeKluba.matches(klub)) {
                    holder.itemView.setBackgroundColor(Color.argb(50,100,100,100));
                }
            }
        }
    }

    @Override
    public int getItemCount() {

        if(klubList == null || klubList.size() == 0) {
            return 0;
        } else {
            return this.klubList.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pozicijaTabela, tv_klubTabela, tv_odigraniTabela, tv_pobedeTabela, tv_nereseneTabela, tv_poraziTabela, tv_datiTabela,
                tv_primljeniTabela, tv_golRazlikaTabela, tv_poeniTabela;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pozicijaTabela = itemView.findViewById(R.id.tv_pozicijaTabela);
            tv_klubTabela = itemView.findViewById(R.id.tv_klubTabela);
            tv_odigraniTabela = itemView.findViewById(R.id.tv_odigraniTabela);
            tv_pobedeTabela = itemView.findViewById(R.id.tv_pobedeTabela);
            tv_nereseneTabela = itemView.findViewById(R.id.tv_nereseneTabela);
            tv_poraziTabela = itemView.findViewById(R.id.tv_poraziTabela);
            tv_datiTabela = itemView.findViewById(R.id.tv_datiTabela);
            tv_primljeniTabela = itemView.findViewById(R.id.tv_primljeniTabela);
            tv_golRazlikaTabela = itemView.findViewById(R.id.tv_golRazlikaTabela);
            tv_poeniTabela = itemView.findViewById(R.id.tv_poeniTabela);
            parentLayout = itemView.findViewById(R.id.klubLayout);
        }

    }
}
