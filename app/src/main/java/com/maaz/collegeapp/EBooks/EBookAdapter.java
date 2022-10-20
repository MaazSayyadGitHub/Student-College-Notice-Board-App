package com.maaz.collegeapp.EBooks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maaz.collegeapp.R;

import java.util.List;

public class EBookAdapter extends RecyclerView.Adapter<EBookAdapter.EBookViewHolder> {

    private Context context;
    private List<EBookData> list;

    public EBookAdapter(Context context, List<EBookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout, parent, false);
        return new EBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EBookViewHolder holder, int position) {

        holder.eBookName.setText(list.get(position).getPdfTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() { // for every position.
            @Override
            public void onClick(View view) {
                // it will open pdf in Pdf Viewer Activity
                Intent intent = new Intent(context, PdfViewerActivity.class);
                // we will use pdf url for open.
                intent.putExtra("pdfUrl", list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.eBookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // it will download the pdf with help of Uri of getPdfUrl.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EBookViewHolder extends RecyclerView.ViewHolder {

        private TextView eBookName;
        private ImageView eBookDownload;

        public EBookViewHolder(@NonNull View itemView) {
            super(itemView);
            eBookDownload = itemView.findViewById(R.id.eBookDownload);
            eBookName = itemView.findViewById(R.id.eBookName);
        }
    }
}
