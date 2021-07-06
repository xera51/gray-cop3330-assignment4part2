/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


// Unsure of how I want to store data
// Might be either one json file, one json file per list, or one csv file, or a database
public class ToDoListManagerModel {

    ObservableList<ToDoList> toDoListObservableList = FXCollections.observableArrayList();

    void loadData() {
        // load data into toDoListObservableList
    }

    void saveToDoList(String listName) {
        // find the list
        // update the list (unsure of method of storage, read comment near top)
    }

    void saveAllDoToLists() {
        // update external storage (unsure of method of storage, read comment near top)
    }
}
