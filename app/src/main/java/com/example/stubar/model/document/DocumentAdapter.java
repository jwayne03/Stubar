package com.example.stubar.model.document;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stubar.R;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.decode.Decode;

import java.io.File;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private Context context;
    private DocumentApiResponse documentApiResponse;

    /**
     * Constructor of the Document Adapter, Adapter is a tool to inflate or fill the information
     * that provides the server and the only thing that needs to do is the structure of the
     * layout.
     *
     * @param context             Context
     * @param documentApiResponse DocumentApiResponse
     */
    public DocumentAdapter(Context context, DocumentApiResponse documentApiResponse) {
        this.context = context;
        this.documentApiResponse = documentApiResponse;
    }

    /**
     * Method DocumentAdapter.ViewHolder where create the view of the Adapter and inflates the Layout
     *
     * @param parent   @NonNull ViewGroup
     * @param viewType int
     * @return new DocumentAdapter.ViewHolder(view)
     */
    @NonNull
    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_documents, parent, false);
        return new DocumentAdapter.ViewHolder(view);
    }

    /**
     * Method of the RecyclerView to find Documents by position that the adapter has given.
     *
     * @param holder   DocmumentAdapter.ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.ViewHolder holder, int position) {
        holder.setDocument(documentApiResponse.getDocuments().get(position));
    }

    /**
     * Method of the RecyclerView to count all the items that the adapter has receive
     *
     * @return DocumentApiResponse
     */
    @Override
    public int getItemCount() {
        return documentApiResponse.getDocuments().size();
    }

    /**
     * Protected class called ViewHolder and extends of ReciclerView.ViewHolder and this class is
     * incharged to manage the data has need to be viewed in the layout
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cnstLayFront, cnstLayBack, itemDoc;
        TextView tvLocalName, tvGrade, tvAuthorDoc, tvTopic, tvBackName;
        ImageButton btnDownloadDoc;
        ImageView ivBackground;

        Document document;
        DownloadManager downloadManager;
        boolean clicked;

        /**
         * Constructor to find view by id with every element of the layout that is ubicated in the XML
         *
         * @param itemView @NonNull View
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDoc = itemView.findViewById(R.id.itemDoc);
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

            WindowManager wm = (WindowManager)    context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            itemDoc.setMaxWidth(display.getWidth() / 2);
            itemDoc.setMaxHeight(display.getWidth() / 2);

            tvLocalName.setText(Decode.decodeUTF8(document.getName()));
            tvBackName.setText(Decode.decodeUTF8(document.getName()));
            tvGrade.setText(String.valueOf(document.getGrade()));
            tvAuthorDoc.setText(R.string.author);
            tvTopic.setText(Decode.decodeUTF8(document.getTopicName()));

            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            btnDownloadDoc.setOnClickListener(v -> {
                Uri uri = Uri.parse(Constants.DOCUMENTS_DOWNLOAD_URL + document.getDocPath());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download", document.getName() + ".pdf")));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            });
        }
    }
}
