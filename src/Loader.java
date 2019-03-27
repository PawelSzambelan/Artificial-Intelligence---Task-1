import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Loader {

    void loadEverything(String fileName, List<City> listOfCities, List<Item> listOfItems) throws IOException {
        loadCitiesFromFile(fileName,listOfCities);
        loadItemsFromFile(fileName,listOfItems);
        loadItemsToCities(listOfCities,listOfItems);
    }

    private void loadCitiesFromFile(String fileName, List<City> listOfCities) throws FileNotFoundException {

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
                lines = nextLine.split("\t");
                listOfCities.add(new City(Integer.parseInt(lines[0]), Double.valueOf(lines[1]), Double.valueOf(lines[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int[][] creatingMatrix(List<City> cities) {

        //System.out.println("\nDistances matrix");
        int matrixSize = cities.size();
        int matrix_of_distances[][] = new int[matrixSize+1][matrixSize+1];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix_of_distances[i+1][j+1] = Math.round(cities.get(i).distanceToCity(cities.get(j)));
                //System.out.print(matrix_of_distances[i+1][j+1] + "     ");
            }
            //System.out.print("\n");
        }
        return matrix_of_distances;
    }

    private void loadItemsFromFile(String fileName, List<Item> listOfItems) throws FileNotFoundException {

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

    int loadKnapsackCapacity(String fileName) throws IOException {

        String lines[];
        String line = Files.readAllLines(Paths.get(fileName)).get(4);
        lines = line.split("\t");
        return Integer.parseInt(lines[1]);
    }

    double loadMaxSpeed(String fileName) throws IOException {

        String lines[];
        String line = Files.readAllLines(Paths.get(fileName)).get(6);
        lines = line.split("\t");
        return Double.parseDouble(lines[1]);
    }

    double loadMinSpeed(String fileName) throws IOException {

        String lines[];
        String line = Files.readAllLines(Paths.get(fileName)).get(5);
        lines = line.split("\t");
        return Double.parseDouble(lines[1]);
    }

    private void loadItemsToCities(List<City> listOfCities, List<Item> listOfItems) {
        for (City city : listOfCities) {
            city.loadItems(listOfItems);
        }
    }

}
