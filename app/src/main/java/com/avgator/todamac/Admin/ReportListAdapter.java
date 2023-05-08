package com.avgator.todamac.Admin;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.Customer.Report;
import com.avgator.todamac.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.MyViewHolder>{
    Context context;

    ArrayList<Report> list;

    public ReportListAdapter(Context context, ArrayList<Report> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReportListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.report_item,parent,false);
        return new ReportListAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ReportListAdapter.MyViewHolder holder, int position) {

        Report report =  list.get(position);
        holder.reportId.setText(report.getReporterId());
        holder.driverName.setText(report.getDriverName());
        holder.reportDate.setText(report.getDate());
        holder.reportPlateNumber.setText(report.getPlateNumber());
        holder.reportType.setText(report.getReportType());

        if (report.getMediaUrl() != null) {
            String mediaUrl = report.getMediaUrl();
            if (mediaUrl.endsWith(".mp4")) {
                holder.imageView.setVisibility(View.GONE);
                holder.playerView.setVisibility(View.VISIBLE);

                VideoView videoView = holder.playerView.findViewById(R.id.report_video);

                MediaController mediaController = new MediaController(context);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(mediaUrl));

                videoView.requestFocus();

                // Bind the ExoPlayer to the PlayerView
            } else {
                holder.playerView.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(mediaUrl)
                        .into(holder.imageView);
            }
        } else {
            holder.playerView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }
        holder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView reportId, driverName, reportDate, reportPlateNumber, reportType;
        ImageView imageView;
        VideoView playerView;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reportId = itemView.findViewById(R.id.reporter_id);
            driverName = itemView.findViewById(R.id.driver_name);
            reportDate = itemView.findViewById(R.id.report_date);
            reportPlateNumber = itemView.findViewById(R.id.report_plate_number);
            reportType = itemView.findViewById(R.id.report_type);
            imageView = itemView.findViewById(R.id.report_image);
            playerView = itemView.findViewById(R.id.report_video);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }




}