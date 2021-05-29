package com.example.stubar.model.document;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentApiResponse {
    @SerializedName("document")
    List<Document> documents;

    /**
     * Default constructor
     */
    public DocumentApiResponse() {
    }

    /**
     * List of documents where we storage all the information that has been requested to the API
     *
     * @return List<Document>
     */
    public List<Document> getDocuments() {
        return documents;
    }
}