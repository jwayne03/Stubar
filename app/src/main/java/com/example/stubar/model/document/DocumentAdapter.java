package com.example.stubar.model.document;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        TextView tvName, tvRate, tvGrade, tvTopic;
        ImageButton ibDownload;
        Document document;
        DownloadManager downloadManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDescription);
            tvRate = itemView.findViewById(R.id.tvPrice);
            ibDownload = itemView.findViewById(R.id.ibDownload);
        }

        public void setOffer(Document document) {
            this.document = document;
            tvName.setText(document.getName());
            tvRate.setText(String.valueOf(document.getRate()));
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            ibDownload.setOnClickListener(v -> {
                Uri uri = Uri.parse(Constants.DOCUMENTS_DOWNLOAD_URL + document.getDocPath());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            });

        }
    }
}
