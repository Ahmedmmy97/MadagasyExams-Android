package com.a33y.jo.madgasyexams;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.List;

/**
 * Created by ahmed on 23/3/2018.
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> implements ListItemClickListener,DataHelper.DataChangedListener {
    List<Subject> items;
    Context c;
    Subject s;
    Term t;
    int type;
    LinearLayout loading;
    View currentView;
    ListItemClickListener listItemClickListener;
    private static final int Subject_Items = 0;
    boolean isAns ;
    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    // The native app install ad view type.
    private static final int Files = 1;
    public Adapter(Term t,List<Subject> items, Context c, int type) {
        this.items = items;
        this.c = c;
        this.t=t;
        this.type =type;
    }
    public Adapter(Subject s, Context c, int type,boolean isAns, LinearLayout loading) {
        this.s = s;
        this.c = c;
        this.isAns =isAns;
        this.type =type;
        this.loading=loading;
        DataHelper.setDataChangedListener(this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(type == Subject_Items)
            v = LayoutInflater.from(c).inflate(R.layout.item_1,parent,false);
        else
            v = LayoutInflater.from(c).inflate(R.layout.item,parent,false);

        currentView =v ;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setListItemClickListener(this);
       //holder.title.setText(subjects.get(position).getTitle());
       //holder.icon.setImageResource(subjects.get(position).getImageRes());
        if(type == Subject_Items) {
            holder.choice.setText((items.get(position)).getTitle());
        }
        else {
            if(isAns)
                holder.choice.setText(s.fileNames_ans.get(position));
            else
                holder.choice.setText(s.fileNames.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(type == Subject_Items)

        return items.size();
        if(!isAns)
            return s.getFileNames()!=null?s.getFileNames().size():0;
        else
            return s.getFileNames_ans()!=null?s.getFileNames_ans().size():0;
    }

    @Override
    public void onClick(int pos) {
        if(type == Subject_Items) {
            Bundle b = new Bundle();
            b.putSerializable("subject", items.get(pos));
            b.putSerializable("term",t);
            Intent intent = new Intent(c, SubjectFilesActivity.class);
            intent.putExtra("subject", b);
            c.startActivity(intent);
            if(listItemClickListener!=null)
                listItemClickListener.onClick(pos);
        }else {
            if (!CheckFile(s, pos)){
                if(Connectivity.isNetworkAvailable(c)) {
                    loading.setVisibility(View.VISIBLE);
                    DataHelper.DownloadRemFile(s, pos,isAns, c,loading);
                }else {
                    Snackbar bar = Snackbar.make(currentView, "Il n'y a pas de connexion internet!", Snackbar.LENGTH_LONG);
                    bar.getView().setBackgroundColor(c.getResources().getColor(R.color.colorPrimary));
                    bar.show();
                }
            }else {
                loading.setVisibility(View.GONE);
                Intent intent = new Intent(c,PdfViewer.class);
                intent.putExtra("subject",s);
                intent.putExtra("pos",file_pos);
                c.startActivity(intent);
            }

        }
    }
    int file_pos;
    boolean CheckFile(Subject s,int pos){
        for(File f : s.getFiles()) {
            if (f.getName().equals(isAns?s.getFileNames_ans().get(pos):s.getFileNames().get(pos))) {
                file_pos = s.getFiles().indexOf(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDataAdded() {

    }

    @Override
    public void onFileDownloaded(int pos,LinearLayout loading) {
        loading.setVisibility(View.GONE);
        Intent intent = new Intent(c,PdfViewer.class);
        intent.putExtra("subject",s);
        intent.putExtra("pos",pos);
        c.startActivity(intent);
    }

    @Override
    public void onFileDownloadedfailed() {

    }
}

 class ViewHolder extends RecyclerView.ViewHolder{
     //TextView title;
     ImageView icon;
     Button choice;
     private ListItemClickListener listItemClickListener;

     public void setListItemClickListener(ListItemClickListener listItemClickListener) {
         this.listItemClickListener = listItemClickListener;
     }

     public ViewHolder(View itemView) {
         super(itemView);
         //title = itemView.findViewById(R.id.title);
         //icon = itemView.findViewById(R.id.icon);
         choice = itemView.findViewById(R.id.button);
         choice.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(listItemClickListener!=null)
                     listItemClickListener.onClick(getAdapterPosition());
             }
         });
     }

 }
