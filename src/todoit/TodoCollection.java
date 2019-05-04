package todoit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TodoCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    protected HashSet<Todo> children;
    
    public TodoCollection(Todo... children) {
        this.children = new HashSet<>();
        for(Todo child : children)
            this.children.add(child);
    }

    public List<Todo> getChildren() {
        return new ArrayList<>(children)
            .stream().sorted()
            .collect(Collectors.toList());
    }
}