import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Thief {

    List<Integer> road;
    List<Item> knapsack;

    public Thief(List<City> listOfCities) {
        road = new ArrayList<>();
        knapsack = new ArrayList<>();
        generatingRoad(listOfCities);
    }


    public void generatingRoad(List<City> listOfCities) {
        for (City city : listOfCities) {
            road.add(city.getCityNumber());
        }
        Collections.shuffle(road);
    }

}
