/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments.model;

import com.google.gson.JsonSyntaxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class ToDoListManagerModel {

    private final ObservableList<ToDo> toDoList = FXCollections.observableArrayList(ToDo.extractor());
    private final FilteredList<ToDo> filteredToDoList = new FilteredList<>(toDoList);
    ToDoListDAO dao = new ToDoListDAO();

    public ToDoListManagerModel() {}

    public ToDoListDAO getDao() {
        return dao;
    }

    public FilteredList<ToDo> getFilteredToDoList() {
        return filteredToDoList;
    }

    public void filterAll() {
        filteredToDoList.setPredicate(null);
    }

    public void filterComplete() {
        filteredToDoList.setPredicate(ToDo::isComplete);
    }

    public void filterIncomplete() {
        filteredToDoList.setPredicate(toDo -> !toDo.isComplete());
    }

    public void sortLexicographic() {
        toDoList.sort(ToDo::compareTo);
    }

    public void sortDue() {
        toDoList.sort(Comparator.comparing(ToDo::getDueDate).thenComparing(o -> o));
    }

    public void createList(File dir, String fileName) throws IOException {
        dao.create(dir, fileName);
    }

    public boolean openList(File file) {
        return dao.open(file);
    }

    public void loadList() throws JsonSyntaxException {
        toDoList.clear();
        toDoList.addAll(dao.read());
    }

    public boolean saveList() {
        return dao.save(toDoList);
    }

    public boolean deleteList() {
       boolean success = dao.delete();
       if(success) toDoList.clear();
       return success;
    }

    public void clearList() {
        dao.setListFileToNull();
        toDoList.clear();
    }

    public void addToDo(ToDo toDo) {
        toDoList.add(toDo);
    }

    public void deleteToDo(ToDo toDo) {
        toDoList.remove(toDo);
    }

    public void deleteAllToDos() {
        toDoList.clear();
    }

}
