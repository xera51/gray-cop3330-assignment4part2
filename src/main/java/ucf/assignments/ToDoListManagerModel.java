/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collection;

public class ToDoListManagerModel {
    ObservableList<Item> toDoList = FXCollections.observableArrayList();

    private File listFile;


    void open(File listFile) throws JsonSyntaxException {
        try(BufferedReader reader = new BufferedReader(new FileReader(listFile))) {
            if(listFile.length() != 0) {
                toDoList.addAll(ToDoListSerializer
                        .fromJson(reader));
            }
            this.listFile = listFile;
        } catch (IOException e) { e.printStackTrace(); }
    }

    void close() {
        toDoList.clear();
        listFile = null;
    }

    void save() {
        if(listFile != null) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
                writer.write(ToDoListSerializer.toJson(toDoList));
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void delete() {
        toDoList.clear();
        if(listFile != null) listFile.delete();
    }

    public File getListFile() {
        return listFile;
    }

    public void setListFile(File listFile) {
        this.listFile = listFile;
    }
}
