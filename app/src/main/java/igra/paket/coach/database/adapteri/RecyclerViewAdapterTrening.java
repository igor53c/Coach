package igra.paket.coach.database.adapteri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.entity.Igrac;

public class RecyclerViewAdapterTrening extends RecyclerView.Adapter<RecyclerViewAdapterTrening.MyViewHolder>{

    List<Igrac> igracList;
    Context context;

    public RecyclerViewAdapterTrening(Context context) {

        this.context = context;
    }

    public void setIgracList(List<Igrac> igracList) {
        this.igracList = igracList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterTrening.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.trening, parent,false);

        return new RecyclerViewAdapterTrening.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTrening.MyViewHolder holder, int position) {
        holder.tv_ime.setText(igracList.get(position).getIme());
        holder.tv_pozicija.setText(igracList.get(position).getPozicija());
        holder.tv_rejting.setText(new DecimalFormat("0.0").format(igracList.get(position).getRejting()));
        holder.tv_godine.setText(String.valueOf(igracList.get(position).getGodine()));

        int motivacija = igracList.get(position).getMotivacija();
        if(motivacija > 66) {
            holder.pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            if(motivacija > 33) {
                holder.pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            } else {
                holder.pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.RED));
            }
        }
        holder.pb_motivacija.setProgress(motivacija);

        int fitnes = igracList.get(position).getSpremnost();
        if(fitnes > 66) {
            holder.pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            if(fitnes > 33) {
                holder.pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            } else {
                holder.pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.RED));
            }
        }
        holder.pb_fitnes.setProgress(fitnes);

        holder.sb_nivoTreninga.setProgress(igracList.get(position).getTrening());
        String poz = igracList.get(position).getPozicija().substring(0,1);
        switch (poz) {
            case "G":
                holder.itemView.setBackgroundColor(Color.argb(100,170,250,170));
                break;
            case "D":
                holder.itemView.setBackgroundColor(Color.argb(100,170,170,250));
                break;
            case "M":
                holder.itemView.setBackgroundColor(Color.argb(100,250,250,150));
                break;
            case "F":
                holder.itemView.setBackgroundColor(Color.argb(100,250,170,170));
                break;
        }

        holder.sb_nivoTreninga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                igracList.get(position).setTrening(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.parentLayout.setOnClickListener(v -> {
        });

    }

    @Override
    public int getItemCount() {

        if(igracList == null || igracList.size() == 0) {
            return 0;
        } else {
            return this.igracList.size();
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ime, tv_pozicija, tv_rejting, tv_godine;
        ProgressBar pb_motivacija, pb_fitnes;
        SeekBar sb_nivoTreninga;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ime = itemView.findViewById(R.id.tv_ime);
            tv_pozicija = itemView.findViewById(R.id.tv_pozicija);
            tv_rejting = itemView.findViewById(R.id.tv_rejting);
            tv_godine = itemView.findViewById(R.id.tv_godine);
            pb_motivacija = itemView.findViewById(R.id.pb_motivacija);
            pb_fitnes = itemView.findViewById(R.id.pb_fitnes);
            sb_nivoTreninga = itemView.findViewById(R.id.sb_nivoTreninga);
            parentLayout = itemView.findViewById(R.id.treningLayout);
        }
    }
}