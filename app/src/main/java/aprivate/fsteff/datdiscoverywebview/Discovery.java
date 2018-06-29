package aprivate.fsteff.datdiscoverywebview;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Discovery {
    private ExecutorService pool = Executors.newCachedThreadPool();

    @JavascriptInterface
    public void join(String id, Consumer<Connection> callback){
        Log.i("Discovery", "Trying to join for '" + id + "'");
        pool.submit(new JoinTask(id, callback));
    }

    private class JoinTask implements Runnable{
        private final String id;
        private final Consumer<Connection> callback;
        public JoinTask(String id, Consumer<Connection> callback){
            this.id = id;
            this.callback = callback;
        }

        public void run(){
            Set<InetSocketAddress> peers = DatDNS.lookupDefaultServers(id);
            if(peers.size() > 0){
                for(InetSocketAddress addr : peers){
                    try {
                        SocketChannel channel = SocketChannel.open(addr);
                        Connection connection = new Connection(channel);
                        Log.i("Discovery", "connected to peer " + addr.getAddress() + ":" + addr.getPort());
                        callback.accept(connection);
                    }catch (IOException e){
                        Log.e("Discovery", "connecting to peer" + addr.getAddress() + ":" + addr.getPort() + " failed");
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
