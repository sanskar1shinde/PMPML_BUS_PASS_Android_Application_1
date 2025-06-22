package com.example.finalsemproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminViewPassessAdapter extends RecyclerView.Adapter<AdminViewPassessAdapter.MyViewHolderAdmin>{

    Context context;
    ArrayList<PMPMLTable> list;

    public  AdminViewPassessAdapter(Context context, ArrayList<PMPMLTable> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public AdminViewPassessAdapter.MyViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item1,parent,false);

        return new MyViewHolderAdmin(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewPassessAdapter.MyViewHolderAdmin holder, int position) {
        PMPMLTable user=list.get(position);
        int age= Integer.parseInt(String.valueOf(user.getAge()));
        int passAmount=Integer.parseInt(String.valueOf(user.getPassAmount()));
        holder.Id.setText((user.getAadharNumber()).substring(user.getAadharNumber().length()-4));
        holder.name.setText(user.getName());
        holder.phoneNumber.setText(user.getMobileNumber());
        holder.gender.setText(user.getGender());
        holder.busRoute.setText(user.getBusRoute());
        holder.passType.setText(user.getPassType());
        holder.startDate.setText(user.getStartDate());
        holder.endDate.setText(user.getEndDate());
        holder.age.setText(String.valueOf(age));
        holder.passAmount.setText(String.valueOf(passAmount));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderAdmin extends RecyclerView.ViewHolder{
        TextView Id,name,phoneNumber,gender,busRoute,passType,startDate,endDate,age,passAmount;
        public MyViewHolderAdmin(@NonNull View itemView) {
            super(itemView);

            Id=itemView.findViewById(R.id.tviditem1);
            name=itemView.findViewById(R.id.tvnameitem1);
            phoneNumber=itemView.findViewById(R.id.tvphonenumberitem1);
            gender=itemView.findViewById(R.id.tvgenderitem1);
            busRoute=itemView.findViewById(R.id.tvbusRouteitem1);
            passType=itemView.findViewById(R.id.tvpassTypeitem1);
            startDate=itemView.findViewById(R.id.tvstartDateitem1);
            endDate=itemView.findViewById(R.id.tvendDateitem1);
            age=itemView.findViewById(R.id.tvageitem1);
            passAmount=itemView.findViewById(R.id.tvpassAmountitem1);

        }
    }
}
