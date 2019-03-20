import java.util.ArrayList;
import java.util.List;

class City {

    private int cityNumber;
    private double x;
    private double y;
    List<Item> listOfItems = new ArrayList<>();

    City(int cityNumber, double x, double y) {
        this.cityNumber = cityNumber;
        this.x = x;
        this.y = y;
    }

    int getCityNumber() {
        return cityNumber;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    int distanceToCity(City city) {
        double x = Math.abs(getX() - city.getX());
        double y = Math.abs(getY() - city.getY());
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    void loadItems(List<Item> itemsList) {
        for (Item item : itemsList) {
            if (item.getCityNumber() == getCityNumber()) {
                listOfItems.add(item);
            }
        }
        listOfItems.sort(Item::compareTo);
    }
}
