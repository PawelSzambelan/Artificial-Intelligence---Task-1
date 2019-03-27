import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class GA {

    private List<Double> listOfFitnesResults;


    private void calculatingFitnesForPopulation(List<City> listOfCities, List<Thief> thievesPopulation, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {
        listOfFitnesResults = new ArrayList<>();
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
//        for (int i = 0; i < listOfFitnesResults.size(); i++) {
////            System.out.println(i + ": " + listOfFitnesResults.get(i));
////        }
    }

    private List<Thief> tournament(List<Double> listOfFitnesResults, List<Thief> thievesPopulation) {

        int Tour = 10;
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

            Thief thiefCopy = new Thief();
            thiefCopy.road.addAll(thievesPopulation.get(thiefIndex).road);
            listOf2ThieviesToCrossover.add(thiefCopy);
        }
        return listOf2ThieviesToCrossover;
    }

    private List<Thief> roulette(List<Double> listOfFitnesResults, List<Thief> thievesPopulation) {

        List<Double> copyOfFitneses = new ArrayList<>();
        copyOfFitneses.addAll(listOfFitnesResults);

        List<Thief> listOf2ThieviesToCrossover = new ArrayList<>();
        int thievesCountToTakeToCrossover = 2;

        double min = Collections.min(copyOfFitneses);

        for (int j = 0; j < thievesCountToTakeToCrossover; j++) {

            double fitnesSum = 0;

            for (int k = 0; k < copyOfFitneses.size(); k++) {
                double fitnes = copyOfFitneses.get(k);
                fitnes -= min;
                copyOfFitneses.set(k, fitnes);
                fitnesSum += fitnes;
            }

            Random rand = new Random();
            int randomNumber = rand.nextInt((int) fitnesSum);

            double partialSum = 0;
            int indexOfThiefToTake = 0;
            for (int i = copyOfFitneses.size() - 1; i >= 0; i--) {
                partialSum += copyOfFitneses.get(i);
                if (partialSum >= randomNumber) {
                    indexOfThiefToTake = i;
                    break;
                }
            }

            Thief thiefCopy = new Thief();
            thiefCopy.road.addAll(thievesPopulation.get(indexOfThiefToTake).road);
            listOf2ThieviesToCrossover.add(thiefCopy);
        }

        return listOf2ThieviesToCrossover;
    }

    private void crossover(List<Thief> TwoThievesToCross) {
        double Px = 0.8;
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

            TwoThievesToCross.clear();
            TwoThievesToCross.add(child1);
            TwoThievesToCross.add(child2);
        }
    }

    private void swapMutation(List<Thief> ThievesList) {
        double Pm = 0.1;
        Random rand = new Random();

        for (Thief thief : ThievesList) {
            if (rand.nextDouble() < Pm) { // <-- 1% of the time.

                int size = thief.road.size() - 1;

                int number1 = rand.nextInt(size - 1) + 1;
                int number2 = rand.nextInt(size - 1) + 1;

                //check if random numbers are not the same
                while (number1 == number2) {
                    number2 = rand.nextInt(size - 1) + 1;
                }

                //swap
                int temp = thief.road.get(number1);
                thief.road.set(number1, thief.road.get(number2));
                thief.road.set(number2, temp);
            }
        }
    }

    void geneticAlgorithmWithTournament(List<City> listOfCities, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {

        List<List<Double>> list_with_all_populations_fitneses = new ArrayList<>();
        int gen = 100;

        //population
        Population population = new Population();
        population.initialise(listOfCities);

        calculatingFitnesForPopulation(listOfCities, population.thievesPopulation, distancesMatrix, knapsackCapacity, maxSpeed, minSpeed);
        list_with_all_populations_fitneses.add(listOfFitnesResults);

        while (list_with_all_populations_fitneses.size() < gen) {

            Population newPopulation = new Population();

            for (int i = 0; i < population.pop_size / 2; i++) {
                List<Thief> thievesToCross = tournament(listOfFitnesResults, population.thievesPopulation);
                crossover(thievesToCross);
                swapMutation(thievesToCross);

                newPopulation.thievesPopulation.addAll(thievesToCross);
            }
            calculatingFitnesForPopulation(listOfCities, newPopulation.thievesPopulation, distancesMatrix, knapsackCapacity, maxSpeed, minSpeed);
            list_with_all_populations_fitneses.add(listOfFitnesResults);

            Collections.copy(population.thievesPopulation, newPopulation.thievesPopulation);

            System.out.println(Collections.max(listOfFitnesResults));
        }

        listToFile(list_with_all_populations_fitneses);
    }

    void geneticAlgorithmWithRoulette(List<City> listOfCities, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {

        List<List<Double>> list_with_all_populations_fitneses = new ArrayList<>();
        int gen = 100;

        //population
        Population population = new Population();
        population.initialise(listOfCities);

        calculatingFitnesForPopulation(listOfCities, population.thievesPopulation, distancesMatrix, knapsackCapacity, maxSpeed, minSpeed);
        list_with_all_populations_fitneses.add(listOfFitnesResults);

        while (list_with_all_populations_fitneses.size() < gen) {

            Population newPopulation = new Population();

            for (int i = 0; i < population.pop_size / 2; i++) {
                List<Thief> thievesToCross = roulette(listOfFitnesResults, population.thievesPopulation);
                crossover(thievesToCross);
                swapMutation(thievesToCross);

                newPopulation.thievesPopulation.addAll(thievesToCross);
            }
            calculatingFitnesForPopulation(listOfCities, newPopulation.thievesPopulation, distancesMatrix, knapsackCapacity, maxSpeed, minSpeed);
            list_with_all_populations_fitneses.add(listOfFitnesResults);

            Collections.copy(population.thievesPopulation, newPopulation.thievesPopulation);

            System.out.println(Collections.max(listOfFitnesResults));
        }

        listToFile(list_with_all_populations_fitneses);
    }

    private void listToFile(List<List<Double>> list_with_all_populations_fitneses) {

        StringBuilder builder = new StringBuilder();

        builder.append("Population");
        builder.append(" ");
        builder.append("Max");
        builder.append(" ");
        builder.append("Min");
        builder.append(" ");
        builder.append("Average");
        builder.append("\n");

        for (int i = 0; i < list_with_all_populations_fitneses.size(); i++) {
            double max = Collections.max(list_with_all_populations_fitneses.get(i));
            double min = Collections.min(list_with_all_populations_fitneses.get(i));
            double average = calculatingTheAverege(list_with_all_populations_fitneses.get(i));

            builder.append(i);
            builder.append(" ");
            builder.append((int) max);
            builder.append(" ");
            builder.append((int) min);
            builder.append(" ");
            builder.append((int) average);
            builder.append("\n");

        }

        builder.toString();
        try (PrintWriter out = new PrintWriter("test1.txt")) {
            out.println(builder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private double calculatingTheAverege(List<Double> fitnesesList) {
        double sum = 0;
        for (Double fitnes : fitnesesList) {
            sum += fitnes;
        }
        return sum / fitnesesList.size();
    }



//--------------------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------NOT EVOLUTIONARY METHOD-------------------------

    //by nearest neighbour
    void tsp(int distancesMatrix[][], List<City> listOfCities, int knapsackCapacity, double maxSpeed, double minSpeed) {

//        System.out.println("\nCities in visited order:");
        int totalRoute = 0;
        int numberOfCities;
        Stack<Integer> stack = new Stack<>();
        Thief thief = new Thief();


        numberOfCities = distancesMatrix[0].length - 1;
        int[] visited = new int[numberOfCities];
        visited[0] = listOfCities.get(0).getCityNumber();  //start with first city
        stack.push(listOfCities.get(0).getCityNumber());
        int element, destination = 0, i, min;
        boolean minFlag = false;
//        System.out.print(listOfCities.get(0).getCityNumber() + "\t");
        thief.road.add(listOfCities.get(0).getCityNumber());

        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 0;
            min = Integer.MAX_VALUE;
            while (i <= numberOfCities - 1) {
                if (distancesMatrix[element][i + 1] > 1 && visited[i] == 0) {
                    if (min > distancesMatrix[element][i + 1]) {
                        min = distancesMatrix[element][i + 1];
                        destination = i + 1;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag) {
                totalRoute += min;
                visited[destination - 1] = 1;
                stack.push(destination);
//                System.out.print(destination + "\t");
                thief.road.add(destination);
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        thief.road.add(listOfCities.get(0).getCityNumber());
//        System.out.println("\nTotal route = " + totalRoute);



        double FitnessResult = 0;
        double roadTime =0;
        int distance;
        double actualSpeed;
        int actualKnapsackWeight =0;
        int itemsWeightSum=0;

        //for loop from one city to another
        for (int j = 0; j < thief.road.size() - 1; j++) {

            distance = distancesMatrix[thief.road.get(j)][thief.road.get(j + 1)];

            //add item to knapsack
            //check if there is an item in the city and if actualKnapsackWeight + future item weight still less than knapsack capacity
            if ((listOfCities.get(thief.road.get(j) - 1).listOfItems.size() != 0) &&
                    (actualKnapsackWeight + listOfCities.get(thief.road.get(j) - 1).listOfItems.get(0).getWeight()) <= knapsackCapacity) {
                Item temporaryItem = listOfCities.get(thief.road.get(j) - 1).listOfItems.get(0);
                thief.knapsack.add(temporaryItem);
                actualKnapsackWeight += temporaryItem.getWeight();
            }

            actualSpeed = maxSpeed - ((double) actualKnapsackWeight * (maxSpeed - minSpeed) / (double) knapsackCapacity);
            roadTime += (double) distance / actualSpeed;
        }

        for (Item item : thief.knapsack) {
            itemsWeightSum += item.getWeight();
        }
        FitnessResult = (itemsWeightSum - roadTime);
        System.out.println("Fitness = " + FitnessResult);
    }


    void tspWithRandomRoad(List<City> listOfCities, int[][] distancesMatrix, int knapsackCapacity, double maxSpeed, double minSpeed) {

        List<List<Double>> list_with_all_populations_fitneses = new ArrayList<>();

        //population
        Population population = new Population();
        population.initialise(listOfCities);

        calculatingFitnesForPopulation(listOfCities, population.thievesPopulation, distancesMatrix, knapsackCapacity, maxSpeed, minSpeed);
        list_with_all_populations_fitneses.add(listOfFitnesResults);

        System.out.println("Fitness max = " + Collections.max(listOfFitnesResults));
        listToFile(list_with_all_populations_fitneses);

    }
}


