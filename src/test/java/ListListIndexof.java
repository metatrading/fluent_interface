import java.util.ArrayList;
import java.util.List;

public class ListListIndexof {
    public static void main(String[] args) {
        List<List<String>> listOfList = new ArrayList<>();

        listOfList.add(List.of("1","2"));
        listOfList.add(List.of("1","2"));

        for (List<String> list : listOfList) {
            System.out.println(listOfList.indexOf(list));
        }
    }
}
