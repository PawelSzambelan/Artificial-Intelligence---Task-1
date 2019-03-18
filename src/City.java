import java.util.ArrayList;
import java.util.List;

public class City {

    private int cityNumber;
    private double x;
    private double y;
    List<Item> listOfItems = new ArrayList<>();

    public City(int cityNumber, double x, double y) {
        this.cityNumber = cityNumber;
        this.x = x;
        this.y = y;
    }

    public int getCityNumber() {
        return cityNumber;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int distanceToCity(City city) {
        double x = Math.abs(getX() - city.getX());
        double y = Math.abs(getY() - city.getY());
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void loadItems(List<Item> itemsList) {
        for (Item item : itemsList) {
            if (item.getCityNumber() == getCityNumber()) {
                listOfItems.add(item);
            }
        }
    }
}
