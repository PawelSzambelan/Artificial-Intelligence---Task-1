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


        //population
        Population population = new Population();
        population.initialise(listOfCities);

        //check if roads in population are random
        //population.printingPopulation();

        GA ga = new GA();
        ga.calculatingFitnesForRandomPopulation(listOfCities, population.thievesPopulation, loader.creatingMatrix(listOfCities), knapsackCapacity, maxSpeed, minSpeed);

        ga.tournament(ga.listOfFitnesResults,population.thievesPopulation);


    }
}
