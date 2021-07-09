package ucf.assignments.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import ucf.assignments.ToDoListSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

public class ToDoDAO {

    private File listFile;

    public ToDoDAO() { listFile = null; }

    public File getListFile() {
        return listFile;
    }

    public void setListFileToNull() {
        listFile = null;
    }

    /* Creates a new file using the dir as the directory and fileName as the file name
     * If succeeds, this ToDoDAO's listFile is set
     */
    public void create(File dir, String fileName)
            throws IOException {
        listFile = Files.createFile(new File(dir, fileName + ".json").toPath()).toFile();
    }

    /* Opens a file
     * If file ends with ".json", sets this ToDoDAO's listFile to passed file
     * otherwise, throws IllegalArgumentException
     */
    public void open(File file) throws IllegalArgumentException {
        if(file.toString().endsWith(".json")) {
            listFile = file;
        } else {
            throw new IllegalArgumentException();
        }

    }

    /* Reads a json file
     * Returns collection of To-Do Objects
     */
    public Collection<? extends ToDo> read()
            throws JsonSyntaxException, JsonIOException {
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
