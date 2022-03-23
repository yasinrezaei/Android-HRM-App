package com.example.aseman.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Objects.Need;
import com.example.aseman.R;

import java.util.ArrayList;

public class UserImportantNeedAdaptor extends RecyclerView.Adapter<UserImportantNeedAdaptor.Holder> {
    private ArrayList<Need> needs;
    private Context context;

    public UserImportantNeedAdaptor(ArrayList<Need> needs, Context context) {
        this.needs = needs;
        this.context = context;
    }

    @NonNull
    @Override
    public UserImportantNeedAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_important_need_item,parent,false);
        return new UserImportantNeedAdaptor.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserImportantNeedAdaptor.Holder holder, int position) {
        Need need=needs.get(position);
        holder.text.setText(need.getText());
    }

    @Override
    public int getItemCount() {
        return needs.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView text;

        public Holder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.importan_need_text);
        }
    }
}
