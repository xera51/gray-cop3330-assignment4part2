/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ucf.assignments.model.ToDo;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ToDoListSerializer {

    private static final Type LOCAL_DATE_PROPERTY_TYPE = new TypeToken<ObjectProperty<LocalDate>>(){}.getType();
    private static final Type COLLECTION_TYPE = new TypeToken<ObservableList<ToDo>>(){}.getType();
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SimpleStringProperty.class, new StringPropertySerializer())
            .registerTypeAdapter(LOCAL_DATE_PROPERTY_TYPE, new LocalDatePropertySerializer())
            .registerTypeAdapter(SimpleBooleanProperty.class, new BooleanPropertySerializer())
            .registerTypeAdapter(StringProperty.class, new StringPropertyDeserializer())
            .registerTypeAdapter(LOCAL_DATE_PROPERTY_TYPE, new LocalDatePropertyDeserializer())
            .registerTypeAdapter(BooleanProperty.class, new BooleanPropertyDeserializer())
            .create();

    public static Collection<? extends ToDo> fromJson(File json) {
        if (json.length() == 0) return new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(json))){
            return GSON.fromJson(reader, COLLECTION_TYPE);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Improper format for To-Do List");
        } catch (JsonIOException | IOException e) {
            throw new JsonIOException("Failed to Read Json");
        }
    }

    public static String toJson(Collection<? extends ToDo> list) {
        return GSON.toJson(list.toArray());
    }

    private static class StringPropertySerializer implements JsonSerializer<SimpleStringProperty> {
        public JsonElement serialize(SimpleStringProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getValue());
        }
    }

    private static class LocalDatePropertySerializer implements JsonSerializer<ObjectProperty<LocalDate>> {
        public JsonElement serialize(ObjectProperty<LocalDate> src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getValue().toString());
        }
    }

    private static class BooleanPropertySerializer implements JsonSerializer<SimpleBooleanProperty> {
        public JsonElement serialize(SimpleBooleanProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getValue());
        }
    }

    private static class StringPropertyDeserializer implements JsonDeserializer<StringProperty> {
        public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new SimpleStringProperty(json.getAsJsonPrimitive().getAsString());
        }
    }

    private static class LocalDatePropertyDeserializer implements JsonDeserializer<ObjectProperty<LocalDate>> {
        public ObjectProperty<LocalDate> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new SimpleObjectProperty<>(LocalDate.parse(json.getAsJsonPrimitive().getAsString()));
        }
    }

    private static class BooleanPropertyDeserializer implements JsonDeserializer<BooleanProperty> {
        public BooleanProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new SimpleBooleanProperty(json.getAsJsonPrimitive().getAsBoolean());
        }
    }

}


