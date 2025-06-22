package com.example.finalsemproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewUSERSAdapter extends RecyclerView.Adapter<ViewUSERSAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;

    public ViewUSERSAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.Id.setText(user.getUserId());
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phoneNumber.setText(user.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Id, name, phoneNumber, email;

        public MyViewHolder(View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.tviddispaly);
            name = itemView.findViewById(R.id.tvnamedispaly);
            phoneNumber = itemView.findViewById(R.id.tvphonenumberdispaly);
            email = itemView.findViewById(R.id.tvemaildispaly);
        }
    }
}
