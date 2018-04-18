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
public class ConsumerClient {

    private static final Logger LOGGER = Logger.getLogger(ConsumerClient.class.getName());

    public static void main(String[] args) {

        if (args.length < 1) {
            LOGGER.log(Level.SEVERE, "This program takes one argument that allows to identify the consumer!");
            System.exit(-1);
        }

        LOGGER.log(Level.INFO, "Starting consumer client...");

        ProducerInterface producerInterface = null;
        Registry registry;

        try {
            producerInterface = (ProducerInterface) Naming.lookup("//" + args[0] + "/ProducerInterface");
            System.out.println("Ok");
            producerInterface.getUrl("Bonjour c'est moi");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
