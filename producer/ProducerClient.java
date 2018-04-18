import java.net.MalformedURLException;
import java.rmi.Naming;
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

    private String generateLongString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 100_000; i++) {
            sb.append("a");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ProducerClient client = new ProducerClient();

            String url = "rmi://localhost/Server";
            Naming.rebind(url, client);
            System.out.println("Bind serveur avec l'url suivante : " + url);

            System.out.println("Démarage du serveur");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
