import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String fileName = "ai-lab1-ttp_data/student/trivial_0.ttp";
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

        //population
        Population population = new Population();
        population.initialise(listOfCities);

        //check if roads in population are random
        //population.printingPopulation();

        GA ga = new GA();
        ga.calculatingFitnesForRandomPopulation(listOfCities, population.thievesPopulation, loader.creatingMatrix(listOfCities), knapsackCapacity, maxSpeed, minSpeed);

        List<Thief> thievesToCross = ga.tournament(ga.listOfFitnesResults, population.thievesPopulation);
        System.out.println(thievesToCross.get(0).road);
        System.out.println(thievesToCross.get(1).road);
        ga.crossover(thievesToCross);

        System.out.println(thievesToCross.get(0).road);
        System.out.println(thievesToCross.get(1).road);

        ga.swapMutation(thievesToCross);



    }
}
