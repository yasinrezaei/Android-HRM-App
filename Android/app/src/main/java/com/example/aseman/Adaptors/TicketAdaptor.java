package com.example.aseman.Adaptors;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aseman.Objects.Ticket;
import com.example.aseman.R;

import java.util.ArrayList;

public class TicketAdaptor extends RecyclerView.Adapter<TicketAdaptor.Holder> {
    private ArrayList<Ticket> tickets;
    private Context context;
    private setOnItemClickListener setOnItemClickListener;

    public TicketAdaptor(ArrayList<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ticket_recycler_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Ticket ticket=tickets.get(position);
        holder.title.setText(ticket.getTitle());
        holder.text.setText(ticket.getText());
        holder.tag.setText(ticket.getTag());

        holder.showMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.linearLayout.getVisibility()==View.GONE){

                    TransitionManager.beginDelayedTransition(holder.cardView);
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.showMoreBtn.setImageResource(R.drawable.expand_less);
                }
                else{

                    holder.linearLayout.setVisibility(View.GONE);
                    holder.showMoreBtn.setImageResource(R.drawable.expand_more);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView title;
        TextView text;
        TextView tag;
        ImageButton showMoreBtn;
        LinearLayout linearLayout;
        CardView cardView;


        public Holder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.ticket_title);
            tag=itemView.findViewById(R.id.ticket_tag);
            showMoreBtn=itemView.findViewById(R.id.btn_more);
            text=itemView.findViewById(R.id.txt_more_text);
            linearLayout=itemView.findViewById(R.id.more_detail_linear);
            cardView=itemView.findViewById(R.id.ticket_card);

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


    public interface setOnItemClickListener{
        void onItemClick(int position);
    }

    public void onItemClickListener(setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener=setOnItemClickListener;
    }
}
