import java.util.Random;
/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private int weather;

    private static final Random rand = Randomizer.getRandom();
    
    private static double FROG_CREATION_PROBABILITY = 0.9;

    private static double RAT_CREATION_PROBABILITY = 0.5;
    
    private int currentWeather;
    //private int weather;
    private static final int NOTHING = 0;    
    private static final int HOT = 1;
    private static final int RAIN = 2;
    private static final int WINDY = 3;
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // initialise instance variables
        weather = 0;
        currentWeather = NOTHING;
    }
    
    public static int random(int max)
    {
        int weather = rand.nextInt(max);
        return weather;
    }

    /**
     *
     * 
     */
    public int getWeather()
    {
        int weather = random(4);
        if(weather == 0){
            currentWeather = HOT;
            FROG_CREATION_PROBABILITY = 0.99;
        }    
        else if(weather == 1){
            currentWeather = RAIN;
            FROG_CREATION_PROBABILITY = 0.99;
            RAT_CREATION_PROBABILITY = 0.05;
        }
        else if(weather == 2){
            currentWeather = WINDY;
            RAT_CREATION_PROBABILITY = 0.05;
        }
        else{
            currentWeather = NOTHING;
        }
        return currentWeather;
    }
}