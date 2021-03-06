package pl.project.goodday.models;


import javax.persistence.*;

@Entity
public class GoldenThought {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String author;

    private String sentence;

    public GoldenThought() {
    }

    public GoldenThought(String author, String sentence) {
        this.author = author;
        this.sentence = sentence;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
