import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Thief {

    List<Integer> road;
    List<Item> knapsack;

    Thief(){
        road = new ArrayList<>();
        knapsack = new ArrayList<>();
    }

    Thief(List<City> listOfCities) {
        road = new ArrayList<>();
        knapsack = new ArrayList<>();
        generatingRoad(listOfCities);
    }


    private void generatingRoad(List<City> listOfCities) {
        for (City city : listOfCities) {
            road.add(city.getCityNumber());
        }
        Collections.shuffle(road);
        //add first city to the end - to finish the route
        road.add(road.get(0));
    }

}
