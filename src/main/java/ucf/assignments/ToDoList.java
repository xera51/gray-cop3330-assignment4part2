/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class ToDoList {
    private StringProperty title = new SimpleStringProperty();
    private ObjectProperty<List<Item>> list = new ObjectPropertyBase<List<Item>>() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    public ToDoList() {}

    public ToDoList(String title, List<Item> list) {}

    public String getTitle() {
        // return the title string
        return null;
    }

    public String getList() {
        // return the list
        return null;
    }

    public void setTitle(String title) {
        // set the title
    }

    public void setList(List<Item> list) {
        // set the list
    }

    public StringProperty titleProperty() {
        // return title
        return null;
    }

    public ObjectProperty<List<Item>> listProperty() {
        // return list
        return null;
    }

    public void add(Item item) {
        // add item to list
    }

    public void remove(Item item) {
        // remove item from list
    }

}
