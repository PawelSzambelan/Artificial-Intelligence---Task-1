import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("mesage");

        String fileName = "ai-lab1-ttp_data/student/trivial_0.ttp";
        ArrayList<City> listOfCities = new ArrayList<>();
        ArrayList<Item> listOfItems = new ArrayList<>();

        Loader loader = new Loader();
        System.out.println("Cities");
        loader.loadCitiesFromFile(fileName, listOfCities);
        System.out.println("Items");
        loader.loadItemsFromFile(fileName, listOfItems);

        //Knapsack Capacity
        System.out.println(loader.loadKnapsackCapacity(fileName));

        //na razie nie ważne
        loader.tsp(Loader.creatingMatrix(listOfCities),listOfCities);

    }
}
