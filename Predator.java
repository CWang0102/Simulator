import java.util.Random;
import java.util.List;
import java.util.Iterator;
/**
 * Abstract class Predator - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Predator extends Animal
{
    // The food value of a single rat. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RAT_FOOD_VALUE = 9;
    //The food value of a single frog.
    private static final int FROG_FOOD_VALUE = 6;
    //The predator's food level. The steps it can take before it dies of hunger.
    private int foodLevel;

    public Predator(boolean randomAge,Field field, Location location)
    {
        super(field, location);
        Random rand = new Random();
        if(randomAge) {
            foodLevel = rand.nextInt(RAT_FOOD_VALUE);
        }
        else{
            foodLevel = RAT_FOOD_VALUE;
        }

    }

    /**
     *Make the predator more hungry. This could result in the fox's death.
     *
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rats adjacent to the current location.
     * Only the first live rat is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    abstract public Location findFood();

    protected Location increaseFoodLevel(Object animal, Location where)
    {
        if (animal instanceof Rat) {
            Rat rat = (Rat) animal;
            if (rat.isAlive()) { 
                rat.setDead();
                foodLevel = RAT_FOOD_VALUE;
                return where;
            }
        }
        if (animal instanceof Frog) {
            Frog frog = (Frog) animal;
            if (frog.isAlive()) { 
                frog.setDead();
                foodLevel = FROG_FOOD_VALUE;
                return where;
            }
        }
    }

}
