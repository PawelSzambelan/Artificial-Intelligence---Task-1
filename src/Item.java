public class Item implements Comparable {

    private int index;
    private int profit;
    private int weight;
    private int cityNumber;
    private double takingCriterion;

    Item(int index, int profit, int weight, int cityNumber) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.cityNumber = cityNumber;

        takingCriterion = (double) profit / (double) weight;
    }

    int getCityNumber() {
        return cityNumber;
    }

    private double getTakingCriterion() {
        return takingCriterion;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object o) {
        if (this.getTakingCriterion() < ((Item) o).getTakingCriterion()) return 1;
        if (this.getTakingCriterion() > ((Item) o).getTakingCriterion()) return -1;
        return 0;

    }
}
