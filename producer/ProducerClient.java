import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Maxime Flament (maxime.flament@etu.unice.fr)
 */
public class ProducerClient extends UnicastRemoteObject implements ProducerInterface{
    private String url;

    protected ProducerClient() throws RemoteException {
    }

    @Override
    public void getUrl(String url) throws RemoteException {
        System.out.println("Url reçu : " + url);
        this.url = url;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ProducerClient client = new ProducerClient();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
