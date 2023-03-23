import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a Cat.
 * Cates age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Cat extends Predator
{
    // Characteristics shared by all Cates (class variables).

    // The age at which a Cat can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a Cat can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a Cat breeding.
    private static final double BREEDING_PROBABILITY = 0.24;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The Cat's age.
    private int age;

    //The gender of the Cat.
    //Female is true, male is false
    private boolean gender;
    /**
     * Create a Cat. A Cat can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Cat will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cat(boolean randomAge, Field field, Location location)
    {
        super(randomAge,field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            gender = rand.nextBoolean();
        }
        else {
            age = 0;
            gender = rand.nextBoolean();
        }
    }

    /**
     * This is what the Cat does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newCates A list to return newly born Cates.
     */
    public void dayAct(List<Animal> newCates)
    { 
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newCates);            
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

    /**
     * How the fow would behave during the night.
     * Same behaviour as the day except it won't give birth to new Cates.
     */
    public void nightAct(List<Animal> newCates)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {  
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
            if(animal instanceof Rat) {
                increaseFoodLevel(animal, where);
            }
        }
        return null;
    }
    
    /**
     * Increase the age. This could result in the Cat's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this Cat is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newCates A list to return newly born Cates.
     */
    private void giveBirth(List<Animal> newCates)
    {
        // New Cates are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);  
            Cat young = new Cat(false, field, loc);
            newCates.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed and there's an opposite sex Cat nearby.
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
     *  Find Cates of the opposite sex in the neighboring places
     */
    private boolean findCates()
    {
        Field field = getField();
        List<Location> adjacentAnimals = field.getAdjacentAnimals(getLocation());
        for(Location location : adjacentAnimals) {
            Object animal = field.getObjectAt(location);
            if (animal instanceof Cat) {
                Cat Cat = (Cat) animal;
                if ( gender != Cat.getGender() ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A Cat can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        if ( findCates() && age >= BREEDING_AGE) {
            return true;
        }
        return false;
    }

    /**
     * @return Gender of the animal.
     */
    private boolean getGender()
    {
        return gender;
    }
}
