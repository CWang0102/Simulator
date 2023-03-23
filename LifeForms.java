import java.util.List;
/**
 * Write a description of interface Life_Form here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface LifeForms
{
    void act(List<LifeForms> LifeForms);
    
    void setLocation(Location newLocation);
}