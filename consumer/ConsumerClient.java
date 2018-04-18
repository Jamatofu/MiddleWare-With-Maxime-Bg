import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class ConsumerClient extends UnicastRemoteObject implements ConsumerInterface {

    private static final Logger LOGGER = Logger.getLogger(ConsumerClient.class.getName());
    private SharedData sharedData;

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    protected ConsumerClient() throws RemoteException {
    }

    @Override
    public void receiveData(String data) {
        sharedData = new SharedData();
        sharedData.setData(data);

        LOGGER.log(Level.INFO, "Received data from producer : " + sharedData.getData());
    }
}
