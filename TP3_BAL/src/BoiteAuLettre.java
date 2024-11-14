import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BoiteAuLettre {
    private final BlockingQueue<String> queue = new SynchronousQueue<>();

    public void deposer(String lettre) throws InterruptedException {
        queue.put(lettre);
    }

    public String retirer() throws InterruptedException {
        return queue.take();
    }
}
