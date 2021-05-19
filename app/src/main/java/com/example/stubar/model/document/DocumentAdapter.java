 package com.example.stubar.model.document;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stubar.R;
import com.example.stubar.utils.constants.Constants;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private Context context;
    private DocumentApiResponse documentApiResponse;

    public DocumentAdapter(Context context, DocumentApiResponse documentApiResponse) {
        this.context = context;
        this.documentApiResponse = documentApiResponse;
    }

    @NonNull
    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_documents, parent, false);
        return new DocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.ViewHolder holder, int position) {
        holder.setDocument(documentApiResponse.getDocuments().get(position));
    }

    @Override
    public int getItemCount() {
        return documentApiResponse.getDocuments().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cnstLayFront, cnstLayBack;
        TextView tvLocalName, tvGrade, tvAuthorDoc, tvTopic, tvBackName;
        ImageButton btnDownloadDoc;
        ImageView ivBackground;

        Document document;
        DownloadManager downloadManager;
        boolean clicked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cnstLayFront = itemView.findViewById(R.id.cnstLayFront);
            cnstLayBack = itemView.findViewById(R.id.cnstLayBack);
            tvLocalName = itemView.findViewById(R.id.tvLocalName);
            tvBackName = itemView.findViewById(R.id.tvBackName);
            tvGrade = itemView.findViewById(R.id.tvGrade);
            tvAuthorDoc = itemView.findViewById(R.id.tvAuthorDoc);
            tvTopic = itemView.findViewById(R.id.tvTopic);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            btnDownloadDoc = itemView.findViewById(R.id.btnDownloadDoc);

            clicked = false;

            itemView.setOnClickListener(view -> {
                clicked = !clicked;

                if (clicked) {
                    tvLocalName.setVisibility(View.GONE);
                    cnstLayBack.setVisibility(View.VISIBLE);
                } else {
                    tvLocalName.setVisibility(View.VISIBLE);
                    cnstLayBack.setVisibility(View.GONE);
                }
            });
        }

        @SuppressLint("SetJavaScriptEnabled")
        private void setDocument(Document document) {
            this.document = document;
            tvLocalName.setText(document.getName());
            tvBackName.setText(document.getName());
            tvGrade.setText(String.valueOf(document.getGrade()));
            tvAuthorDoc.setText("hola");
            tvTopic.setText(String.valueOf(document.getTopicName()));

            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            btnDownloadDoc.setOnClickListener(v -> {
                Uri uri = Uri.parse(Constants.DOCUMENTS_DOWNLOAD_URL + document.getDocPath());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            });

        }
    }
}
