/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListManagerControllerTest {

    @Test
    void add_todolist() {
        // add list
        // check if new list is in model list
    }

    @Test
    void remove_todolist() {
        // remove list
        // check if list is in model list
    }

    @Test
    void edit_todolist_title() {
        // edit list title
        // check if list is in model list
    }

    @Test
    void add_item_to_todolist() {
        // add item
        // check if the new item is in the model list todolist
    }

    @Test
    void remove_item_from_todolist() {
        // remove item
        // check if the item is in the model list todolist
    }

    @Test
    void edit_item_description() {
        // edit item description
        // check if the item is in the model list todolist
    }

    @Test
    void edit_item_due_date() {
        // edit item due date
        // find the item by description
        // check if the due date is updated
    }

    @Test
    void edit_item_complete() {
        // edit item complete
        // find the item by description
        // check if the complete is updated
    }

    @Test
    void filter_all() {
        // run filter
        // check if expected elements there
    }

    @Test
    void filter_complete() {
        // run filter
        // check if expected elements there
    }

    @Test
    void filter_incomplete() {
        // run filter
        // check if expected elements there
    }

    @Test
    void save_list() {
        // make new list
        // save list
        // check the external to see if its there (unsure of method for external)
    }

    @Test
    void save_all_lists() {
        // make multiple new lists
        // save all lists
        // check the external to see if its there (unsure of method for external)
    }


    // both 19-20. All lists are loaded upon start up, user can switch between any list.
    // (they can view one list and close (19) or view multiple lists (20))
    @Test
    void lists_are_loaded_to_model() {
        // call load method
        // look at the list, and compare to expected list
    }
}