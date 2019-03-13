import java.util.ArrayList;
import java.util.List;

public class GA {

    public void creatingListOfItemsToTake(ArrayList<Item> listOfItems, int KnapsackCapacity) {
        int actualWeight = 0;


        for (Item item : listOfItems) {
            System.out.println("Item takingCriterion " + item.getTakingCriterion());
        }
    }


//        while (actualWeight <= KnapsackCapacity){
//            for(Item item:listOfItems){
//
//            }
//        }


    public void creatingListOfItemsToTake(List<Item> listOfItems, List<Thief> thievesPopulation) {

        listOfItems.sort(Item::compareTo);

        /*
        //only check if items are in corrected order
        for (Item item:listOfItems){
            System.out.println("Item takingCriterion " + item.getTakingCriterion());
        }
        */

        for (Thief thief : thievesPopulation) {

        }
    }


}
