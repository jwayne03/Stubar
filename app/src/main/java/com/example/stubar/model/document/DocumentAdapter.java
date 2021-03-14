package com.example.stubar.model.document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stubar.R;

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
        Document document;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDescription);
            tvRate = itemView.findViewById(R.id.tvPrice);
        }

        public void setOffer(Document document) {
            this.document = document;
            tvName.setText(document.getName());
            tvRate.setText(String.valueOf(document.getRate()));
        }
    }
}
