package com.example.stubar.utils.deserializer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    private static final long serialVersionUID = 1L;

    /**
     * LocalDateDeserializer
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    /**
     * Method to deserialize the date
     *
     * @param jp   JsonParser
     * @param ctxt DeserializationContext
     * @return LocalDate
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        return LocalDate.parse(jp.readValueAs(String.class));
    }
}
