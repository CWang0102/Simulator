import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A simulation of snakes.
 * Snakes age, move, breed, eat rabbits and rats.
 * @author (your name)
 * @version (a version number or a date)
 */
public class Snake extends Predator
{
    // The age at which a Snake can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a Snake can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a Snake breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    //Individual fields
    //The snake's age
    private int age;

    /**
     * Constructor for objects of class Snake
     */
    public Snake(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }
    
    public void dayAct(List<Animal> newSnakes)
    {
        incrementAge();
    }
    
    public void nightAct(List<Animal> newSnakes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSnakes);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    public Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rat || animal instanceof Frog) {
                increaseFoodLevel(animal, where);
            }
        }
        return null;
    }
    
    /**
     * Increase the age. This could result in the Snake's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this snake is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSnakees A list to return newly born snakees.
     */
    private void giveBirth(List<Animal> newSnakees)
    {
        // New snakees are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Snake young = new Snake(false, field, loc);
            newSnakees.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A animal can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

}

