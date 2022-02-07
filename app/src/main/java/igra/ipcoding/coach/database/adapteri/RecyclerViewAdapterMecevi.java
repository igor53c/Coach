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
import igra.ipcoding.coach.database.entity.Mec;


public class RecyclerViewAdapterMecevi extends RecyclerView.Adapter<RecyclerViewAdapterMecevi.MyViewHolder>{

    List<Mec> mecList;
    Context context;
    String klub;

    public RecyclerViewAdapterMecevi(Context context, String klub) {
        this.context = context;
        this.klub = klub;
    }

    public void setMecList(List<Mec> mecList) {
        this.mecList = mecList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterMecevi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mec, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterMecevi.MyViewHolder holder, int position) {
        String domacin = this.mecList.get(position).getDomacin();
        String gost = this.mecList.get(position).getGost();
        holder.tv_domacinMec.setText(domacin);
        holder.tv_gostMec.setText(gost);
        holder.tv_domacinGolMec.setText(String.valueOf(this.mecList.get(position).getGolDomacin()));
        holder.tv_gostGolMec.setText(String.valueOf(this.mecList.get(position).getGolGost()));
        if(domacin.matches(klub) || gost.matches(klub)) {
            holder.itemView.setBackgroundColor(Color.argb(100,200,200,200));
        }
        holder.parentLayout.setOnClickListener(v -> {
        });

    }

    @Override
    public int getItemCount() {
        if(mecList == null || mecList.size() == 0) {
            return 0;
        } else {
            return this.mecList.size();
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_domacinMec;
        TextView tv_gostMec;
        TextView tv_domacinGolMec;
        TextView tv_gostGolMec;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_domacinMec = itemView.findViewById(R.id.tv_domacinMec);
            tv_gostMec = itemView.findViewById(R.id.tv_gostMec);
            tv_domacinGolMec = itemView.findViewById(R.id.tv_domacinGolMec);
            tv_gostGolMec = itemView.findViewById(R.id.tv_gostGolMec);
            parentLayout = itemView.findViewById(R.id.mecLayout);
        }
    }
}
