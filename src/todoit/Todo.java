package todoit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Todo extends TodoCollection implements Comparable<Todo> {
    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private int priority;
    private TodoState state;

    public Todo(String title, String description, int priority, Todo... dependencies) {
        super(dependencies);
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.state = TodoState.Pending;
    }

    public void setState(TodoState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean addDependency(Todo todo) {
        if(todo == this || todo.getChildren().contains(this))
            return false;

        return children.add(todo);
    }

    public void addDependencies(Todo... todos) {
        for(Todo todo : todos)
            addDependency(todo);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null || getClass() != other.getClass())
            return false;

        Todo todo = (Todo)other;
        return title.equals(todo.title)
            && description.equals(todo.description)
            && children.equals(todo.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            title, description,children);
    }

    @Override
    public int compareTo(Todo o) {
        int r = state.compareTo(o.state);

        if(r == 0) r = _containsR(children, o) ? 1
            : _containsR(o.children, this) ? -1 : 0;

        if(r == 0) r = Integer.compare(priority, o.priority);
        if(r == 0) r = title.compareTo(o.title);
        if(r == 0) r = description.compareTo(o.description);
        return r;
    }

    @Override
    public String toString() {
        // TODO
        return title + ": " + description;
    }

    private static boolean _containsR(Collection<Todo> children, Todo value) {
        return _containsR(new HashSet<Todo>(), new HashSet<Todo>(children), value);
    }

    private static boolean _containsR(HashSet<Todo> closed, Collection<Todo> children, Todo value) {
        if(children.contains(value))
            return true;

        children.removeAll(closed);
        for(Todo todo : children) {
            closed.add(todo);

            if(_containsR(closed, todo.children, value))
                return true;
        }

        return false;
    }
}