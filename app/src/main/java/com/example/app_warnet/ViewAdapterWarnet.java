package com.example.app_warnet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_warnet.model.UserDetail;
import com.example.app_warnet.model.WarnetDetail;

import java.util.ArrayList;

public class ViewAdapterWarnet extends RecyclerView.Adapter<ViewAdapterWarnet.viewHolder>{

    private ArrayList<ViewModelWarnet> arrayList;
    private String UserID;
    private Context context;

    public ViewAdapterWarnet(ArrayList<ViewModelWarnet> arrayList, String UserID, Context context){
        this.arrayList = arrayList;
        this.UserID = UserID;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item_warnet, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ViewModelWarnet modelWarnet = arrayList.get(position);
        holder.Nama_Warnet.setText(modelWarnet.NetName);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, modelWarnet.WarnetID, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context.getApplicationContext(), WarnetDetail.class);
                i.putExtra("UserID", UserID);
                i.putExtra("WarnetID", modelWarnet.WarnetID);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView Nama_Warnet;
        private CardView cardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Nama_Warnet      = itemView.findViewById(R.id.namawarnet);
            cardView        = itemView.findViewById(R.id.carddatawarnet);
        }
    }
}
