/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class SharedConfig {

    private static final String HOST = "rmi://localhost:";
    private static final String PORT = "8080";
    public static final String COMPLETE_URL = HOST + PORT;

    // interface naming
    public static final String CONSUMER_INTERFACE_NAMING = "ConsumerInterface";
    public static final String PRODUCER_INTERFACE_NAMING = "ProducerInterface";
}
