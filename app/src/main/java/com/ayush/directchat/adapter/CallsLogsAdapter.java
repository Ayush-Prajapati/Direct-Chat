package com.ayush.directchat.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush.directchat.R;
import com.ayush.directchat.model.CallsModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CallsLogsAdapter extends RecyclerView.Adapter<CallsLogsAdapter.Holder> {

    private Context context;
    private ArrayList<CallsModel> callsModelArrayList;
    private OnClick mListener;


    public interface OnClick{
        void OnClick(int position);
        void OnClickFabClick(int position);
    }

    public void setOnItemClickListener(OnClick listener){
        mListener = listener;
    }

    public CallsLogsAdapter(Context context, ArrayList<CallsModel> callsModelArrayList) {
        this.context = context;
        this.callsModelArrayList = callsModelArrayList;
    }

    @NonNull
    @Override
    public CallsLogsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calls_logs_item,parent,false);
        return new CallsLogsAdapter.Holder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CallsLogsAdapter.Holder holder, int position) {

        CallsModel callsModel = callsModelArrayList.get(position);

        if (callsModel.getName() == null){
            holder.textViewName.setText("Unknown");
        }else {
            holder.textViewName.setText(callsModel.getName());
        }

        if (callsModel.getType() != null){
            if (callsModel.getType().equalsIgnoreCase("OUTGOING")){
                holder.imageViewType.setImageDrawable(context.getResources().getDrawable(R.drawable.outgoing));
            }else if (callsModel.getType().equalsIgnoreCase("INCOMING")){
                holder.imageViewType.setImageDrawable(context.getResources().getDrawable(R.drawable.incoming));
            }else if (callsModel.getType().equalsIgnoreCase("MISSED")){
                holder.imageViewType.setImageDrawable(context.getResources().getDrawable(R.drawable.missed_call));
            }
        }

        holder.textViewNumber.setText(callsModel.getNumber());
        holder.textViewDuration.setText(callsModel.getDuration());

    }

    @Override
    public int getItemCount() {
        return callsModelArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewNumber,textViewDuration;
        FloatingActionButton floatingActionButton;
        ImageView imageViewType;


        public Holder(@NonNull View itemView, final OnClick mListener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            imageViewType = itemView.findViewById(R.id.ImageViewType);
            floatingActionButton = itemView.findViewById(R.id.fab);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.OnClick(position);
                        }
                    }
                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.OnClickFabClick(position);
                        }
                    }
                }
            });
        }
    }

}
