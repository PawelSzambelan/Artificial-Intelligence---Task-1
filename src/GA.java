import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    protected List<Double> listOfFitnesResults = new ArrayList<>();


    public void calculatingFitnesForRandomPopulation(List<City> listOfCities, List<Thief> thievesPopulation, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {
        double roadTime;
        int distance;
        double actualSpeed;
        int actualKnapsackWeight;
        List<Double> listWithRoadTimes = new ArrayList<>();
        int itemsWeightSum;

        for (Thief thief : thievesPopulation) {
            //clear actual knapsack weight for the next thief
            roadTime = 0;
            actualKnapsackWeight = 0;
            itemsWeightSum = 0;

            //for loop from one city to another
            for (int i = 0; i < thief.road.size() - 1; i++) {

                distance = distancesMatrix[thief.road.get(i)][thief.road.get(i + 1)];

                //add item to knapsack
                //check if there is an item in the city and if actualKnapsackWeight + future item weight still less than knapsack capacity
                if ((listOfCities.get(thief.road.get(i) - 1).listOfItems.size() != 0) &&
                        (actualKnapsackWeight + listOfCities.get(thief.road.get(i) - 1).listOfItems.get(0).getWeight()) <= knapsackCapacity) {
                    Item temporaryItem = listOfCities.get(thief.road.get(i) - 1).listOfItems.get(0);
                    thief.knapsack.add(temporaryItem);
                    actualKnapsackWeight += temporaryItem.getWeight();
                }

                actualSpeed = maxSpeed - ((double) actualKnapsackWeight * (maxSpeed - minSpeed) / (double) knapsackCapacity);
                roadTime += (double) distance / actualSpeed;
            }

            listWithRoadTimes.add(roadTime);
            for (Item item : thief.knapsack) {
                itemsWeightSum += item.getWeight();
            }
            listOfFitnesResults.add(itemsWeightSum - roadTime);

        }
        for (int i = 0; i < listOfFitnesResults.size(); i++) {
            System.out.println(i + ": " + listOfFitnesResults.get(i));
        }
    }

    public List<Thief> tournament(List<Double> listOfFitnesResults, List<Thief> thievesPopulation) {

        int Tour = 5;
        List<Thief> listOf2ThieviesToCrossover = new ArrayList<>();
        int thievesCountToTakeToCrossover = 2;

        for (int j = 0; j < thievesCountToTakeToCrossover; j++) {
            List<Integer> tmpList = new ArrayList<>();

            for (int i = 0; i < Tour; i++) {
                int tmp = (int) (Math.random() * 100);
                if (!tmpList.contains(tmp))
                    tmpList.add(tmp);
            }

            List<Double> choosenFitnes = new ArrayList<>();
            for (Integer choosen : tmpList) {
                choosenFitnes.add(listOfFitnesResults.get(choosen));
            }

            double theBest = Collections.max(choosenFitnes);
            int indexTMP = 0;


            for (Double findingIndexOfMax : choosenFitnes) {
                if (findingIndexOfMax == theBest) {
                    indexTMP = choosenFitnes.indexOf(theBest);
                    break;
                }
            }

            int thiefIndex = tmpList.get(indexTMP);

            listOf2ThieviesToCrossover.add(thievesPopulation.get(thiefIndex));
        }
        return listOf2ThieviesToCrossover;
    }

    public void crossover(List<Thief> TwoThievesToCross) {
        double Px = 0.7;
        Random rand = new Random();

        if (rand.nextDouble() < Px) { // <-- 70% of the time.
            Thief parent1 = TwoThievesToCross.get(0);
            Thief parent2 = TwoThievesToCross.get(1);

            //size of the parents
            final int size = parent1.road.size() - 1;

            // choose two random numbers for the start and end indices of the slice
            final int number1 = rand.nextInt(size - 1);
            final int number2 = rand.nextInt(size);

            // make the smaller the start and the larger the end
            final int start = Math.min(number1, number2);
            final int end = Math.max(number1, number2);

            // instantiate two child tours
            Thief child1 = new Thief();
            Thief child2 = new Thief();


            // add the sublist in between the start and end points to the children
            child1.road.addAll(parent1.road.subList(start, end));
            child2.road.addAll(parent2.road.subList(start, end));

            // iterate over each city in the parent tours
            int currentCityIndex = 0;
            int currentCityInTour1 = 0;
            int currentCityInTour2 = 0;
            for (int i = 0; i < size; i++) {
                // get the index of the current city
                currentCityIndex = (end + i) % size;

                // get the city at the current index in each of the two parent tours
                currentCityInTour1 = parent1.road.get(currentCityIndex);
                currentCityInTour2 = parent2.road.get(currentCityIndex);

                // if child 1 does not already contain the current city in parent 2, add it
                if (!child1.road.contains(currentCityInTour2)) {
                    child1.road.add(currentCityInTour2);
                }

                // if child 2 does not already contain the current city in tour 1, add it
                if (!child2.road.contains(currentCityInTour1)) {
                    child2.road.add(currentCityInTour1);
                }
            }

            // rotate the lists so the original slice is in the same place as in the parents
            Collections.rotate(child1.road, start);
            Collections.rotate(child2.road, start);

            // add the last city the as the first one !
            child1.road.add(child1.road.get(0));
            child2.road.add(child2.road.get(0));

            // copy the parents from the children back into the parents, because crossover functions are in-place!
            Collections.copy(parent1.road, child2.road);
            Collections.copy(parent2.road, child1.road);
        }
    }

    public void swapMutation(List<Thief> ThievesList){
        double Pm = 0.01;
        Random rand = new Random();

        for(Thief thief:ThievesList){
            if (rand.nextDouble() < 0.9) { // <-- 1% of the time.

                int size = thief.road.size() - 1;

                int number1 = rand.nextInt(size-1) + 1;
                int number2 = rand.nextInt(size-1) + 1;

                //check if random numbers are not the same
                while (number1 == number2) {
                    number2 = rand.nextInt(size-1) + 1;
                }

                //swap
                int temp = thief.road.get(number1);
                thief.road.set(number1,thief.road.get(number2));
                thief.road.set(number2,temp);
            }
        }
    }
}
