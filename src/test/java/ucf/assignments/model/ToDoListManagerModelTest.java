/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListManagerModelTest {

    private static final ToDoListManagerModel model = new ToDoListManagerModel();
    private static final File testFile = new File(System.getProperty("user.dir") +
            File.separator + "lists" + File.separator + "test_list.json");
    private static final ToDo toDo = new ToDo("New To-Do", LocalDate.of(2021, 7, 11), true);

    @BeforeAll
    static void before() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        testFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("""
                [
                  {
                    "description": "Complete COP3330 assignment",
                    "dueDate": "2021-07-11",
                    "complete": true
                  },
                  {
                    "description": "Charge phone",
                    "dueDate": "2021-07-30",
                    "complete": false
                  },
                  {
                    "description": "Go to the store",
                    "dueDate": "2021-07-21",
                    "complete": false
                  }
                ]""");
        writer.close();
        model.openList(testFile);
    }

    @BeforeEach
    void load_list() {
        model.loadList();
    }

    @Test
    void add_item() {
        model.addToDo(toDo);
        assertTrue(model.getFilteredToDoList().contains(toDo));
    }

    @Test
    void delete_item() {
        model.addToDo(toDo);
        model.deleteToDo(toDo);
        assertFalse(model.getFilteredToDoList().contains(toDo));
    }

    @Test
    void delete_all_items() {
        model.deleteAllToDos();
        assertEquals(0, model.getFilteredToDoList().size());
    }

    // All Edit operations are handled by bindings or listeners
    // ListView is attached to model's FilteredList, which is attach to an ObservableList
    // When an item is selected, its description is bound to a TextArea and dueDate to a DatePicker
    // When either is focused, it is temporarily unbound so the user can edit. The edit is then
    // sent to the currently selected item and the bind is recreated
    // Completeness as handled by a binding in the TaskCellController

    @Test
    void filter_all() {
        model.filterAll();
        assertEquals(3, model.getFilteredToDoList().size());
    }

    @Test
    void filter_complete() {
        model.filterComplete();
        assertEquals(1, model.getFilteredToDoList().size());
    }

    @Test
    void filter_incomplete() {
        model.filterIncomplete();
        assertEquals(2, model.getFilteredToDoList().size());
    }

    @Test
    void save() throws IOException {
        model.addToDo(toDo);
        model.saveList();

        String expected = """
                [
                  {
                    "description": "Complete COP3330 assignment",
                    "dueDate": "2021-07-11",
                    "complete": true
                  },
                  {
                    "description": "Charge phone",
                    "dueDate": "2021-07-30",
                    "complete": false
                  },
                  {
                    "description": "Go to the store",
                    "dueDate": "2021-07-21",
                    "complete": false
                  },
                  {
                    "description": "New To-Do",
                    "dueDate": "2021-07-11",
                    "complete": true
                  }
                ]""";
        String actual = String.join("\n", Files.readAllLines(Paths.get(testFile.toString())));

        assertEquals(expected, actual);
    }

    @AfterAll
    static void delete_list() {
        model.deleteList();
    }
}