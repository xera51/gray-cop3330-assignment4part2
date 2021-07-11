/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments.model;

import com.google.gson.JsonSyntaxException;
import ucf.assignments.util.ToDoListSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

public class ToDoListDAO {

    private File listFile = null;

    public ToDoListDAO() {}

    public File getListFile() {
        return listFile;
    }

    public void setListFileToNull() {
        listFile = null;
    }

    /* Creates a new file using the dir as the directory and fileName as the file name
     * If succeeds, this ToDoListDAO's listFile is set
     */
    public void create(File dir, String fileName)
            throws IOException {
        listFile = Files.createFile(new File(dir, fileName + ".json").toPath()).toFile();
    }

    /* Opens a file
     * If file ends with ".json", sets this ToDoListDAO's listFile to passed file
     * otherwise, throws IllegalArgumentException
     */
    public boolean open(File file) {
        if(file.toString().endsWith(".json")) {
            listFile = file;
            return true;
        } else {
            return false;
        }

    }

    /* Reads a json file
     * Returns collection of To-Do Objects
     */
    public Collection<? extends ToDo> read()
            throws JsonSyntaxException {
        return ToDoListSerializer.fromJson(listFile);
    }

    /* Saves a ToDoList to listFile
     * Returns true if succeeded, false otherwise
     */
    public boolean save(Collection<? extends ToDo> toDoList) {
        try {
            Files.writeString(listFile.toPath(), ToDoListSerializer.toJson(toDoList));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Deletes listFile
     * Returns true if succeeded, false otherwise
     */
    public boolean delete() {
        if(listFile.delete()) {
            listFile = null;
            return true;
        } else {
            return false;
        }
    }
}
