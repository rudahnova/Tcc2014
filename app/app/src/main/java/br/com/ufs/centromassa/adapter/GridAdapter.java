package br.com.ufs.centromassa.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.ufs.centromassa.R;

/**
 * Created by jon_j on 02/11/2015.
 */

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<String> mItems;

    public GridAdapter(List<String> mItems) {
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.iten_list_rank, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String label = mItems.get(i);
        viewHolder.textView.setText(label);
        if(i < 3){
            viewHolder.textView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tvItem);
        }
    }
}


