import java.util.concurrent.Semaphore;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class SharedData {

    private String data;

    public synchronized String getData() {
        return data;
    }

    public synchronized void setData(String data) {
        this.data = data;
    }
}
