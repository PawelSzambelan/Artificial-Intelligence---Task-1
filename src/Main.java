import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String fileName = "ai-lab1-ttp_data/student/trivial_0.ttp";
        List<City> listOfCities = new ArrayList<>();
        List<Item> listOfItems = new ArrayList<>();
        int knapsackCapacity;
        double maxSpeed, minSpeed;

        Loader loader = new Loader();
        knapsackCapacity = loader.loadKnapsackCapacity(fileName);
        maxSpeed = loader.loadMaxSpeed(fileName);
        minSpeed = loader.loadMinSpeed(fileName);

        //System.out.println("Cities");
        loader.loadCitiesFromFile(fileName, listOfCities);
        //System.out.println("Items");
        loader.loadItemsFromFile(fileName, listOfItems);
        //load items to cities
        loader.loadItemsToCities(listOfCities, listOfItems);
        /*
        //check if items are in correct cities !!
        for(City city:listOfCities){
            System.out.println("City number " + city.getCityNumber());
            for (Item item:city.listOfItems){
                System.out.println("Item " + item.cityNumber);
            }
        }
        */

        //Knapsack Capacity
        System.out.println("Knapsack Capacity = " + knapsackCapacity);

        //na razie nie ważne
        //loader.tsp(Loader.creatingMatrix(listOfCities),listOfCities);

        //population
        Population population = new Population();
        population.initialise(listOfCities);
        //check if roads in population are random
        population.printingPopulation();

        GA ga = new GA();
        ga.creatingListOfItemsToTake(listOfItems, listOfCities, population.thievesPopulation, loader.creatingMatrix(listOfCities), knapsackCapacity, maxSpeed, minSpeed);

    }
}
