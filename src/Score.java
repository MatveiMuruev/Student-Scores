import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable {
    int id;
    int val;

    ArrayList <Integer> arr = new ArrayList<>();

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public void setVal(int val) throws IllegalArgumentException {
        if (val < 1 || val > 5)
            throw new IllegalArgumentException("оценка должна быть в диапазоне от 1 до 5");
        this.val = val;
        this.id = addValtoArr(val);
    }

    Score(){}
    Score (int val){
        this.val=val;
        this.id = addValtoArr(val);
    }
    Score(int id, ArrayList<Integer> arr){
        this.id = id;
        this.arr = arr;
    }

    int addValtoArr(int val){
        arr.add(val);
        return arr.size()-1;
    }

    @Override
    public String toString() {

        return "Оценки: " + arr.toString() ;
    }
}
