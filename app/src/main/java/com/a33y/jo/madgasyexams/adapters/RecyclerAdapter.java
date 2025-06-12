package com.a33y.jo.madgasyexams.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a33y.jo.madgasyexams.helpers.ListItemClickListener;
import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.models.Exam;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    List<Exam> files;
    ListItemClickListener listItemClickListener;

    public RecyclerAdapter(Context context, List<Exam> files) {
        this.context = context;
        this.files = files;
    }

    public RecyclerAdapter(Context context, List<Exam> files, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.files = files;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.file_item, viewGroup, false);

        return new RecyclerViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int i) {
        viewHolder.bind_data(files.get(i).getName(), i);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView title;
        TextView date;
        TextView uploader;
        String id;
        Context c;
        int pos;

        public RecyclerViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            this.c = c;
            assign_views(itemView);
            itemView.setOnClickListener(this);
        }

        void assign_views(View v) {
            icon = v.findViewById(R.id.icon);
            title = v.findViewById(R.id.title);
            date = v.findViewById(R.id.date);
            uploader = v.findViewById(R.id.uploader);

        }

        public void bind_data(String file, int pos) {

            title.setText(file);
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            if (listItemClickListener != null)
                listItemClickListener.onClick(pos);
        }
    }
}
