package com.a33y.jo.madgasyexams.adapters;

import android.app.Activity;

import android.content.Context;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.models.Subject;
import com.a33y.jo.madgasyexams.models.Term;

import java.util.List;

/**
 * Created by ahmed on 23/3/2018.
 */

public class Adapter extends BaseAdapter {
    Context ctx;
    List<Object> data;
    ViewHolder holder;

    public Adapter(Context ctx, List<Object> data) {
        this.ctx = ctx;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_layout, parent, false);
            holder = new ViewHolder(convertView, ctx);
            //holder.bind_data(Category.categories.get(i));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind_data(data.get(position));
        return convertView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        LinearLayout container;
        Context c;

        public ViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            this.c = c;
            assign_views(itemView);
        }

        void assign_views(View v) {
            icon = v.findViewById(R.id.thumbcat);
            title = v.findViewById(R.id.ctitle);
            container = v.findViewById(R.id.container);
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            container.getLayoutParams().width = width / 2;
            if (dm.densityDpi < 240) {
                container.getLayoutParams().width = width / 3;
            }
        }

        public void bind_data(Object item) {
            if (item instanceof Subject s) {
                title.setText(s.getTitle());
                icon.setImageResource(s.getIconRes());
            } else if (item instanceof Term t) {
                title.setText(t.getTitle());
            }
        }
    }
}
