import java.util.ArrayList;
import java.util.List;

public class GA {

    private List<Double> listWithRoadTimes = new ArrayList<>();
    private int itemsWeightSum = 0;


    public void creatingListOfItemsToTake(List<Item> listOfItems, List<City> listOfCities, List<Thief> thievesPopulation, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {
        double roadTime;
        int actualWeight = 0;
        int distance;
        double actualSpeed;
        int actualKnapsackWeight;


        for (City city : listOfCities) {
            city.listOfItems.sort(Item::compareTo);
        }

        /*
        //listOfItems.sort(Item::compareTo);

        //creating final list of items that will be taken by the thief
        List<Item> finalItemsListToTake = new ArrayList<>();
        for (Item item : listOfItems) {
            if (actualWeight <= knapsackCapacity && item.getWeight() <= knapsackCapacity - actualWeight) {
                finalItemsListToTake.add(item);
                actualWeight += item.getWeight();
            }
        }
        */

        for (Thief thief : thievesPopulation) {
            //clear actual knapsack weight for the next thief
            roadTime = 0;
            actualKnapsackWeight = 0;

            //for loop from one city to another
            for (int i = 0; i < thief.road.size() - 1; i++) {
                actualSpeed = 0;
                distance = 0;

                distance = distancesMatrix[thief.road.get(i)][thief.road.get(i + 1)];

                /*
                //add item to knapsack
                for (Item item : finalItemsListToTake) {
                    //if item is in the cty, where the thief is
                    if (item.getCityNumber() == thief.road.get(i)) {
                        thief.knapsack.add(item);
                        actualKnapsackWeight += item.getWeight();
                    }
                }
                */

                //add item to knapsack
                if (actualKnapsackWeight <= knapsackCapacity) {
                    if (listOfCities.get(thief.road.get(i)-1).listOfItems.size() != 0) {
                        Item temporaryItem = listOfCities.get(thief.road.get(i)-1).listOfItems.get(0);
                        thief.knapsack.add(temporaryItem);
                        actualKnapsackWeight += temporaryItem.getWeight();
                    }
                }


                actualSpeed = maxSpeed - ((double) actualKnapsackWeight * (maxSpeed - minSpeed) / (double) knapsackCapacity);
                roadTime += (double) distance / actualSpeed;
            }
            listWithRoadTimes.add(roadTime);
            for (Item item: thief.knapsack){
                itemsWeightSum += item.getWeight();
            }
            //DODAĆ G(X,Y) !!!!!
        }
    }


}
