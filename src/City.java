public class City {

    private int cityNumber;
    private double x;
    private double y;

    public City(int cityNumber, double x, double y){
        this.cityNumber = cityNumber;
        this.x = x;
        this.y = y;
    }


    public int getCityNumber() {
        return cityNumber;
    }

    public void setCityNumber(int cityNumber) {
        this.cityNumber = cityNumber;
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
        return (int)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
