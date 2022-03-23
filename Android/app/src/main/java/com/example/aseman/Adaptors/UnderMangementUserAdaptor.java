package com.example.aseman.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Objects.User;
import com.example.aseman.R;

import java.util.ArrayList;

public class UnderMangementUserAdaptor extends RecyclerView.Adapter<UnderMangementUserAdaptor.Holder> {

    private ArrayList<User> users;
    private Context context;
    private setOnItemClickListener setOnItemClickListener;

    public UnderMangementUserAdaptor(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UnderMangementUserAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_recycler_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnderMangementUserAdaptor.Holder holder, int position) {
        User user=users.get(position);
        holder.username.setText(user.getUsername());



    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class Holder extends RecyclerView.ViewHolder{
        TextView username;


        public Holder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(setOnItemClickListener!=null && getAdapterPosition()!=RecyclerView.NO_POSITION){
                        setOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface setOnItemClickListener {
        void onItemClick(int position);
    }
    public void onItemClickListener(setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener=setOnItemClickListener;
    }
}
