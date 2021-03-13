package com.example.stubar.model.document;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentApiResponse {
    @SerializedName("document")
    List<Document> documents;

    public DocumentApiResponse(List<Document> documents) {
        this.documents = documents;
    }

    public DocumentApiResponse() {
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
