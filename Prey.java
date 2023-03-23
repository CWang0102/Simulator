import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * Abstract class Prey - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Prey extends Animal
{
    // instance variables - replace the example below with your own
    private static final Random rand = Randomizer.getRandom();
    //Plants plants = ;
    int PLANTS_FOOD_VALUE = 30;
    // Preys food level.
    int foodConsumed; 
    public Prey(boolean randomAge, Field field, Location location) {
        super(field,location);
        if(randomAge) {
            foodConsumed = rand.nextInt(PLANTS_FOOD_VALUE);
        }
        else {
            foodConsumed = PLANTS_FOOD_VALUE;
        }
    }

    protected Location getFood() {
        Field field = getField();
        List<Location> adjacent = field.getFreeAdjacentLocations(getLocation());
        //Plants plants = (Plants) plants;
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()){
        Location where = it.next();
        Object plants = field.getObjectAt(where);
        if (plants instanceof Plants) {
            Plants plant = (Plants) plants;
            if (plant.isAlive()){
                plant.setDead();
                foodConsumed = PLANTS_FOOD_VALUE;
                return where;
            }
        }
        }
        return null;
    }
}