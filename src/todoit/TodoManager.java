package todoit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoManager {
    public static final String LISTS_FILE = "lists.dat";
    private Map<String, TodoList> todoListMap;
    private String listsDirectory;

    public TodoManager(String listsDirectory) {
        this.listsDirectory = listsDirectory;
        if(!listsDirectory.endsWith(File.separator))
            this.listsDirectory += File.separator;

        todoListMap = new HashMap<>();
    }

    public void saveLists() {
        try(ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(listsDirectory + LISTS_FILE))) {
            oos.writeObject(todoListMap);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLists() {
        try(ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream(listsDirectory + LISTS_FILE))) {
            todoListMap = (HashMap<String, TodoList>)ois.readObject();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TodoList addList(TodoList list) {
        return todoListMap.put(list.getTitle(), list);
    }

    public List<TodoList> getLists() {
        return todoListMap
            .values().stream().sorted()
            .collect(Collectors.toList());
    }

    public TodoList removeList(TodoList list) {
        for(String key : todoListMap.keySet()) {
            if(list.equals(todoListMap.get(key)))
                return removeList(key);
        }

        return null;
    }

    public TodoList removeList(String key) {
        return todoListMap.remove(key);
    }

    public void addTodo(String todoList, Todo entry) {
        if(!todoListMap.containsKey(todoList))
            todoListMap.put(todoList, new TodoList(todoList, entry));
        else todoListMap.get(todoList).add(entry);
    }

    public boolean removeTodo(String todoList, Todo entry) {
        if(todoListMap.containsKey(todoList))
            return todoListMap.get(todoList).remove(entry);

        return false;
    }

    public List<Todo> getTodos(String todoList) {
        if(!todoListMap.containsKey(todoList))
            todoListMap.put(todoList, new TodoList(todoList));

        return todoListMap.get(todoList).getChildren();
    }

    public void printTodos(String todoList, PrintStream out) {
        if(!todoListMap.containsKey(todoList))
            todoListMap.put(todoList, new TodoList(todoList));

        for(Todo todo : todoListMap.get(todoList).getChildren())
            out.println(todo);
    }
}