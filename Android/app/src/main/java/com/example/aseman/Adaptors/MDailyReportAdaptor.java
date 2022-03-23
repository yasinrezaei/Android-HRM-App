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

import com.example.aseman.JalaliCal.JalaliCalendar;
import com.example.aseman.Objects.ManagerDailyReport;
import com.example.aseman.Objects.Ticket;
import com.example.aseman.R;

import java.util.ArrayList;

public class MDailyReportAdaptor extends RecyclerView.Adapter<MDailyReportAdaptor.Holder>{
    private ArrayList<ManagerDailyReport> reports;
    private Context context;
    private setOnItemClickListener setOnItemClickListener;

    public MDailyReportAdaptor(ArrayList<ManagerDailyReport> reports, Context context) {
        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.manager_daily_report_recycler_item,parent,false);
        return new MDailyReportAdaptor.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MDailyReportAdaptor.Holder holder, int position) {
        ManagerDailyReport report=reports.get(position);
        holder.date.setText(dateCleaner(report.getDate()));
        holder.text.setText(report.getText());

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

    private String dateCleaner(String date) {
        String cleanDate="";
        for(int i=0;i<date.length();i++){
            if (date.charAt(i) == 'T') {
                break;
            }
            cleanDate+=date.charAt(i);

        }
        return convertToJalali(cleanDate);
    }

    private String convertToJalali(String cleanDate) {
        String[] splited = cleanDate.split("-", 3);




        JalaliCalendar jalaliCalendar=new JalaliCalendar();
        JalaliCalendar.YearMonthDate jalali_yearMonthDate=new JalaliCalendar.YearMonthDate(Integer.valueOf(splited[0]),Integer.valueOf(splited[1]),Integer.valueOf(splited[2]));
        JalaliCalendar.YearMonthDate converted_date= JalaliCalendar.gregorianToJalali(jalali_yearMonthDate);
        return converted_date.toString();
    }


    @Override
    public int getItemCount() {
        return reports.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView date;
        TextView text;
        ImageButton showMoreBtn;
        LinearLayout linearLayout;
        CardView cardView;


        public Holder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.mdr_report_date);
            text=itemView.findViewById(R.id.mdr_txt_more_text);
            showMoreBtn=itemView.findViewById(R.id.mdr_btn_more);
            linearLayout=itemView.findViewById(R.id.mdr_more_detail_linear);
            cardView=itemView.findViewById(R.id.mdr_card);

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

    public void onItemClickListener(MDailyReportAdaptor.setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener=setOnItemClickListener;
    }
}
