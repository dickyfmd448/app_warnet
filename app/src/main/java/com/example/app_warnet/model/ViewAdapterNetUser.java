package com.example.app_warnet.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_warnet.R;
import com.example.app_warnet.user.UserFindActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapterNetUser extends RecyclerView.Adapter<ViewAdapterNetUser.viewHolder> {

    private ArrayList<ViewModelNetUser> arrayList;
    private String UserID;
    private Context context;

    public ViewAdapterNetUser(ArrayList<ViewModelNetUser> arrayList, String UserID, Context context){
        this.arrayList = arrayList;
        this.UserID = UserID;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapterNetUser.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item_net_user, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapterNetUser.viewHolder holder, int position) {
        ViewModelNetUser modelNetUser = arrayList.get(position);
        holder.Nama_Warnet.setText(modelNetUser.NetName);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), WarnetDetailUser.class);
                i.putExtra("UserID", UserID);
                i.putExtra("WarnetID", modelNetUser.WarnetID);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(List<ViewModelNetUser> results) {
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView Nama_Warnet;
        private CardView cardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Nama_Warnet      = itemView.findViewById(R.id.namawarnetuser);
            cardView        = itemView.findViewById(R.id.carddatawarnetuser);

        }
    }
}
