 package com.example.stubar.model.document;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        holder.setOffer(documentApiResponse.getDocuments().get(position));
    }

    @Override
    public int getItemCount() {
        return documentApiResponse.getDocuments().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocalName, tvDescription, tvAuthorDoc, tvTopic;
        ImageButton btnDownloadDoc;
        ImageView ivBackground;
        Document document;
        DownloadManager downloadManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocalName = itemView.findViewById(R.id.tvLocalName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAuthorDoc = itemView.findViewById(R.id.tvAuthorDoc);
            tvTopic = itemView.findViewById(R.id.tvTopic);
            btnDownloadDoc = itemView.findViewById(R.id.btnDownloadDoc);
        }

        @SuppressLint("SetJavaScriptEnabled")
        public void setOffer(Document document) {
            this.document = document;
            tvLocalName.setText(document.getName());
            tvDescription.setText(document.getName());
            tvAuthorDoc.setText(document.getName());
            tvTopic.setText(String.valueOf(document.getRate()));

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
