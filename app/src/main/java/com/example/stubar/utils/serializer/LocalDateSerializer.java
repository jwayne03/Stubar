//package com.example.stubar.utils.serializer;
//
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//public class LocalDateSerializer implements JsonSerializer<LocalDate> {
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
//        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
//    }
//
//    @Override
//    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
//    }
//}