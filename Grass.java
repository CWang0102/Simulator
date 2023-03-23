import java.util.List;
import java.util.Random;
/**
 * Write a description of class Grass here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Grass extends Plants
{
    private static final Random rand = Randomizer.getRandom();
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4; 
    /**
     * Constructor for objects of class Grass
     */
    public Grass(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * This is what the grass does most of the time - it grows.
     * 
     * @param newGrasses A list to return newly seeded grass.
     */
    public void act(List<LifeForms> newGrasses)
    {
        if(isAlive()) {
            growGrass(newGrasses);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not grass is grown at this step.
     * New growths will be made into free adjacent locations.
     * @param newGrasses A list to return newly seeded grass.
     */
    private void growGrass(List<LifeForms> newGrasses)
    {
        // New grass is grown into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int growths = grow();
        for(int b = 0; b < growths && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass seed = new Grass(field, loc);
            newGrasses.add(seed);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int grow()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

}