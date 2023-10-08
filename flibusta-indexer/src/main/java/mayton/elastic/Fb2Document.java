package mayton.elastic;

import java.util.Objects;

public class Fb2Document {

    private String path;

    private String author;

    private String title;

    private String body;

    public Fb2Document() {}

    public Fb2Document(String path, String author, String title, String body) {
        this.path = path;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fb2Document that = (Fb2Document) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(author, that.author) &&
                Objects.equals(title, that.title) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, author, title, body);
    }

    @Override
    public String toString() {
        return "Fb2Document{" +
                "path='" + path + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
