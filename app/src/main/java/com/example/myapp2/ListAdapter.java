package com.example.myapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<RecordatorioModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<RecordatorioModel> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<RecordatorioModel> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView recordatorio;
        TextView fecha;
        ViewHolder(View view){
            super(view);
            recordatorio = itemView.findViewById(R.id.textViewRecordatorio);
            fecha = itemView.findViewById(R.id.textViewFecha);
        }

        void bindData(final RecordatorioModel item){
            recordatorio.setText(item.getTexto());
            fecha.setText(item.getFecha().toString());
        }
    }
}
