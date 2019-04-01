package com.demo.currencyconvertor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.currencyconvertor.R;
import com.demo.currencyconvertor.model.ExchangeRate;

import java.util.ArrayList;

/* Custom adapter to load currency code and rate on card*/
public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ExchangViewHolder> {

    private ArrayList<ExchangeRate> dataList;

    public ExchangeAdapter(ArrayList<ExchangeRate> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ExchangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new ExchangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExchangViewHolder holder, int position) {
        holder.txtNoticeTitle.setText(dataList.get(position).getSourceCurrency());
        holder.txtNoticeBrief.setText(dataList.get(position).getTargetCurrency());
        holder.txtNoticeFilePath.setText(dataList.get(position).getRate().toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ExchangViewHolder extends RecyclerView.ViewHolder {

        TextView txtNoticeTitle, txtNoticeBrief, txtNoticeFilePath;

        ExchangViewHolder(View itemView) {
            super(itemView);
            txtNoticeTitle =  itemView.findViewById(R.id.txt_source);
            txtNoticeBrief =  itemView.findViewById(R.id.txt_target);
            txtNoticeFilePath =  itemView.findViewById(R.id.txt_rate);
        }
    }
}