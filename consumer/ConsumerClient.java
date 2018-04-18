import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class ConsumerClient implements ConsumerInterface {

    private static final Logger LOGGER = Logger.getLogger(ConsumerClient.class.getName());
    private SharedData sharedData;

    public static void main(String[] args) {

        if (args.length < 1) {
            LOGGER.log(Level.SEVERE, "This program takes one argument that allows to identify the consumer!");
            System.exit(-1);
        }

        LOGGER.log(Level.INFO, "Starting consumer client...");

        ProducerInterface producerInterface = null;
        Registry registry;

        try {
            producerInterface = (ProducerInterface) Naming.lookup(SharedConfig.COMPLETE_URL + SharedConfig.PRODUCER_INTERFACE_NAMING);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        try {
            // args[0] contains the identifier of this consumer
            // example : http://localhost:8080/ConsumerInterface/c0
            producerInterface.getUrl(SharedConfig.COMPLETE_URL + "/" + SharedConfig.CONSUMER_INTERFACE_NAMING + "/" + args[0]);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while trying to contact the producer from consumer");
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Couldn't instantiate ProducerInterface stub");
        }

    }

    @Override
    public void receiveData(String data) {
        sharedData = new SharedData();
        sharedData.setData(data);

        LOGGER.log(Level.INFO, "Received data from producer : " + sharedData.getData());
    }
}
