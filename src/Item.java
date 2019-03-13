public class Item implements Comparable {

    private int index;
    private int profit;
    private int weight;
    private int cityNumber;
    private boolean isTaken;
    private double takingCriterion;

    public Item(int index, int profit, int weight, int cityNumber) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.cityNumber = cityNumber;

        isTaken = false;
        takingCriterion = (double) profit / (double) weight;
    }

    public int getCityNumber() {
        return cityNumber;
    }

    public double getTakingCriterion() {
        return takingCriterion;
    }


    @Override
    public int compareTo(Object o) {
        if (this.getTakingCriterion() < ((Item) o).getTakingCriterion()) return 1;
        if (this.getTakingCriterion() > ((Item) o).getTakingCriterion()) return -1;
        return 0;

    }
}
