package igra.paket.coach.database.adapteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.entity.Istorija;

public class RecyclerViewAdapterIstorija extends RecyclerView.Adapter<RecyclerViewAdapterIstorija.MyViewHolder> {

    List<Istorija> istorijaList;
    Context context;

    public RecyclerViewAdapterIstorija(Context context) {

        this.context = context;
    }

    public void setIstorijaList(List<Istorija> istorijaList) {
        this.istorijaList = istorijaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterIstorija.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.istorija, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterIstorija.MyViewHolder holder, int position) {
        holder.tv_godina.setText(String.valueOf(istorijaList.get(position).getGodina()));
        holder.tv_liga.setText(istorijaList.get(position).getLiga());
        holder.tv_pozicija.setText(String.valueOf(istorijaList.get(position).getPozicija()));
        holder.parentLayout.setOnClickListener(v -> {
        });

    }

    @Override
    public int getItemCount() {

        if (istorijaList == null || istorijaList.size() == 0) {
            return 0;
        } else {
            return this.istorijaList.size();
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_godina;
        TextView tv_liga;
        TextView tv_pozicija;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_godina = itemView.findViewById(R.id.tv_godina);
            tv_liga = itemView.findViewById(R.id.tv_liga);
            tv_pozicija = itemView.findViewById(R.id.tv_pozicija);
            parentLayout = itemView.findViewById(R.id.istorijaLayout);
        }
    }
}
