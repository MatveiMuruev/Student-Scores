import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private Score score;

    public void newScore(int val) throws IllegalArgumentException {
        score.setVal(val);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Score getScore() {
        return score;
    }

    Student() {
        this.score = new Score();
    }

    Student(String name, Score score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Имя: " + name + " " + score;

    }
}
