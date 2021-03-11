package fr.doranco.myquizz.entity;

public class User {

    private String name;
    private Integer score;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + name + '\'' +
                ", mScore=" + score +
                '}';
    }
}
