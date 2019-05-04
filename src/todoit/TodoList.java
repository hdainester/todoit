package todoit;

public class TodoList extends TodoCollection implements Comparable<TodoList> {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;

    public TodoList(String title, Todo... todos) {
        this(title, "", todos);
    }

    public TodoList(String title, String description, Todo... todos) {
        super(todos);
        this.title = title;
        this.description = description;
    }

    public boolean add(Todo todo) {
        return children.add(todo);
    }

    public boolean remove(Todo todo) {
        return children.remove(todo);
    }

    public void addAll(Todo... todos) {
        for(Todo todo : todos)
            children.add(todo);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(TodoList o) {
        return title.compareTo(o.title);
    }

    @Override
    public String toString() {
        return title;
    }
}