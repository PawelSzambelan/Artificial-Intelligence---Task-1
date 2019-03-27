import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String fileName = "ai-lab1-ttp_data/student/hard_4.ttp";
        List<City> listOfCities = new ArrayList<>();
        List<Item> listOfItems = new ArrayList<>();
        int knapsackCapacity = 0;
        double maxSpeed = 0;
        double minSpeed = 0;

        //loader
        Loader loader = new Loader();
        loader.loadEverything(fileName, listOfCities, listOfItems);
        knapsackCapacity = loader.loadKnapsackCapacity(fileName);
        maxSpeed = loader.loadMaxSpeed(fileName);
        minSpeed = loader.loadMinSpeed(fileName);

        GA gaTest = new GA();
//        gaTest.geneticAlgorithmWithRoulette(listOfCities, loader.creatingMatrix(listOfCities),knapsackCapacity,maxSpeed,minSpeed);
//        gaTest.geneticAlgorithmWithTournament(listOfCities, loader.creatingMatrix(listOfCities),knapsackCapacity,maxSpeed,minSpeed);

        gaTest.tsp(loader.creatingMatrix(listOfCities), listOfCities,knapsackCapacity,maxSpeed,minSpeed);
//        gaTest.tspWithRandomRoad(listOfCities, loader.creatingMatrix(listOfCities),knapsackCapacity,maxSpeed,minSpeed);

    }
}
