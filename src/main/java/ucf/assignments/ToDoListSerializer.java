/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collection;

public class ToDoListSerializer {

    private static final Type LOCAL_DATE_PROPERTY_TYPE = new TypeToken<ObjectProperty<LocalDate>>(){}.getType();
    private static final Type COLLECTION_TYPE = new TypeToken<ObservableList<Item>>(){}.getType();
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SimpleStringProperty.class, new StringPropertySerializer())
            .registerTypeAdapter(LOCAL_DATE_PROPERTY_TYPE, new LocalDatePropertySerializer())
            .registerTypeAdapter(SimpleBooleanProperty.class, new BooleanPropertySerializer())
            .registerTypeAdapter(StringProperty.class, new StringPropertyDeserializer())
            .registerTypeAdapter(LOCAL_DATE_PROPERTY_TYPE, new LocalDatePropertyDeserializer())
            .registerTypeAdapter(BooleanProperty.class, new BooleanPropertyDeserializer())
            .create();

    public static Collection<? extends Item> fromJson(Reader json) {
        try {
            return gson.fromJson(json, COLLECTION_TYPE);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new JsonSyntaxException("Improper format for To-Do List");
        }
    }

    public static String toJson(ObservableList<Item> list) {
        return gson.toJson(list.toArray());
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


