import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Loader {

    public void loadCitiesFromFile(String fileName, List<City> listOfCities) throws FileNotFoundException {

        String startLine = "NODE_COORD_SECTION	(INDEX, X, Y): ";
        String endLine = "ITEMS SECTION	(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER): ";

        var fileReader = new FileReader(fileName);
        var reader = new BufferedReader(fileReader);

        try (
                fileReader;
                reader
        ) {
            String lines[];
            String nextLine;

            //read to startLine
            while (!((nextLine = reader.readLine()).equals(startLine))) {
            }

            //read until endLine
            while (!((nextLine = reader.readLine()).equals(endLine))) {
                //wyświetlanie miast
                //System.out.println(nextLine);
                lines = nextLine.split("\t");
                listOfCities.add(new City(Integer.parseInt(lines[0]), Double.valueOf(lines[1]), Double.valueOf(lines[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] creatingMatrix(List<City> cities) {

        System.out.println("\nDistances matrix");
        int matrixSize = cities.size();
        int matrix_of_distances[][] = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix_of_distances[i][j] = Math.round(cities.get(i).distanceToCity(cities.get(j)));
                System.out.print(matrix_of_distances[i][j] + "     ");
            }
            System.out.print("\n");
        }
        return matrix_of_distances;
    }

    public void loadItemsFromFile(String fileName, List<Item> listOfItems) throws FileNotFoundException {

        String startLine = "ITEMS SECTION	(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER): ";

        var fileReader = new FileReader(fileName);
        var reader = new BufferedReader(fileReader);

        try (
                fileReader;
                reader
        ) {
            String lines[];
            String nextLine;

            //read to startLine
            while (!((nextLine = reader.readLine()).equals(startLine))) {
            }

            //read until endLine
            while ((nextLine = reader.readLine()) != null) {
                //wyswietlanie itemow
                //System.out.println(nextLine);
                lines = nextLine.split("\t");
                listOfItems.add(new Item(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]), Integer.parseInt(lines[2]), Integer.parseInt(lines[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadKnapsackCapacity(String fileName) throws IOException {

        String lines[];
        String line = Files.readAllLines(Paths.get(fileName)).get(4);
        lines = line.split("\t");
        return Integer.parseInt(lines[1]);
    }

    public void loadItemsToCities(List<City> listOfCities, List<Item> listOfItems) {
        for (City city : listOfCities) {
            city.loadItems(listOfItems);
        }
    }

    //by nearest neighbour
    public void tsp(int distancesMatrix[][], List<City> listOfCities) {

        System.out.println("\nCities in visited order:");
        int totalRoute = 0;
        int numberOfCities;
        Stack<Integer> stack = new Stack<>();

        numberOfCities = distancesMatrix[0].length;
        int[] visited = new int[numberOfCities];
        visited[0] = listOfCities.get(0).getCityNumber();  //start with first city
        stack.push(listOfCities.get(0).getCityNumber());
        int element, destination = 0, i, min;
        boolean minFlag = false;
        System.out.print(listOfCities.get(0).getCityNumber() + "\t");

        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 0;
            min = Integer.MAX_VALUE;
            while (i <= numberOfCities - 1) {
                if (distancesMatrix[element - 1][i] > 1 && visited[i] == 0) {
                    if (min > distancesMatrix[element - 1][i]) {
                        min = distancesMatrix[element - 1][i];
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
                System.out.print(destination + "\t");
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        System.out.println("\nTotal route = " + totalRoute);
    }


}
