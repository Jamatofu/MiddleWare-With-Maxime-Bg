import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by bealclement on 18/04/18.
 */
public interface ProducerInterface extends Remote {
    void getUrl(String url) throws RemoteException;
}
