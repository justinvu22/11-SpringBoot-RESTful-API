package Siirex.model;

import java.util.Objects;

public class Todo {
    private final String title;
    private final String detail;

    public Todo(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return title.equals(todo.title) && detail.equals(todo.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, detail);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
