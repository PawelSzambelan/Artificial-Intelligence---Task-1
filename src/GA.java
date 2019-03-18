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


        for (City city : listOfCities) {
            city.listOfItems.sort(Item::compareTo);
        }

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

    public void tournament(List<Double> listOfFitnesResults, List<Thief> thievesPopulation) {

        int Tour = 5;
        List<Thief> listOf2ThieviesToCrossover = new ArrayList<>();

        for(int j =0; j < 2;j++) {
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
        System.out.println(listOf2ThieviesToCrossover);
    }

    public void crossover(List<Thief> TwoThievesToCross){
        double Px = 0.7;
        Random rand = new Random();

        if (rand.nextDouble() < Px) { // <-- 70% of the time.

        }
    }

}
