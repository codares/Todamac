package com.avgator.todamac.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.R;

import java.util.ArrayList;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationAdapterVh> implements Filterable {

    public List<DestinationModel> destinationModelList = new ArrayList<>();
    public List<DestinationModel> getDestinationModelListFilter = new ArrayList<>();
    public Context context;
    private DestinationClickListener destinationClickListener;

    public DestinationAdapter(List<DestinationModel> destinationModels,Context context, DestinationClickListener destinationClickListener){
        this.destinationModelList = destinationModels;
        this.getDestinationModelListFilter = destinationModels;
        this.context = context;
        this.destinationClickListener = destinationClickListener;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = getDestinationModelListFilter;
                    filterResults.count =getDestinationModelListFilter.size();
                }else {
                    String searchStr = constraint.toString().toLowerCase();
                    List<DestinationModel> destinationModels = new ArrayList<>();
                    for (DestinationModel destinationModel: getDestinationModelListFilter){
                        if(destinationModel.getDesName().toLowerCase().contains(searchStr)){
                            destinationModels.add(destinationModel);
                        }
                    }

                    filterResults.values = destinationModels;
                    filterResults.count = destinationModels.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                destinationModelList = (List<DestinationModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface DestinationClickListener{
        void selectedDestinations(DestinationModel destinationModel);

    }

    @NonNull
    @Override
    public DestinationAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_destinations,parent,false);
        return new DestinationAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationAdapterVh holder, int position) {

        DestinationModel destinationModel = destinationModelList.get(position);
        String desName = destinationModel.getDesName();
        String desCode = destinationModel.getDesCode();

        holder.desName.setText(desName);
        holder.desCode.setText(desCode);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationClickListener.selectedDestinations(destinationModel);
            }
        });

    }

    @Override
    public int getItemCount() {

        return destinationModelList.size();
    }

    public static class DestinationAdapterVh extends RecyclerView.ViewHolder{

        private TextView desName;
        private TextView desCode;

        public DestinationAdapterVh(@NonNull View itemView) {

            super(itemView);
            desName = itemView.findViewById(R.id.tvDesName);
            desCode = itemView.findViewById(R.id.tvDesCode);
        }
    }


}
