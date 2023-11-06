package com.example.app_warnet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_warnet.model.UserDetail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.viewHolder>{

    private ArrayList<ViewModel> arrayList;
    private String UserID;
    private Context context;

    public ViewAdapter(ArrayList<ViewModel> arrayList, String UserID, Context context) {
        this.arrayList = arrayList;
        this.UserID = UserID;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ViewModel model = arrayList.get(position);
        holder.nameprofile.setText(model.Name);
        holder.emailprofile.setText(model.Email);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, model.UserID, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context.getApplicationContext(), UserDetail.class);
                i.putExtra("UserID", UserID);
                i.putExtra("userID", model.UserID);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView nameprofile, emailprofile;
        private CardView cardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nameprofile     = itemView.findViewById(R.id.namaprofil);
            emailprofile    = itemView.findViewById(R.id.emailprofil);
            cardView        = itemView.findViewById(R.id.carddatauser);

        }
    }
}
