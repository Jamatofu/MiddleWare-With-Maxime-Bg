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
public class MainConsumerClient {

    private static final Logger LOGGER = Logger.getLogger(ConsumerClient.class.getName());

    public static void main(String[] args) {

        if (args.length < 1) {
            LOGGER.log(Level.SEVERE, "This program takes one argument that allows to identify the consumer!");
            System.exit(-1);
        }

        LOGGER.log(Level.INFO, "Starting consumer client...");
        ConsumerClient consumerClient;
        ProducerInterface producerInterface = null;
        Registry registry;

        try {
            consumerClient = new ConsumerClient();
            Naming.rebind(SharedConfig.COMPLETE_URL + "/" + SharedConfig.CONSUMER_INTERFACE_NAMING, consumerClient);
        } catch (RemoteException | MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "Error while rebinding the client stub");
        }

        try {
            producerInterface = (ProducerInterface) Naming.lookup(SharedConfig.COMPLETE_URL + SharedConfig.PRODUCER_INTERFACE_NAMING);
            LOGGER.log(Level.INFO, "Successfully connected to the producer");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        try {
            // args[0] contains the identifier of this consumer
            // example : rmi://localhost:8080/ConsumerInterface/c0
            producerInterface.getUrl(SharedConfig.COMPLETE_URL + "/" + SharedConfig.CONSUMER_INTERFACE_NAMING + "/" + args[0]);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An exception occurred while trying to contact the producer from consumer");
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Couldn't instantiate ProducerInterface stub");
        }

    }
}
