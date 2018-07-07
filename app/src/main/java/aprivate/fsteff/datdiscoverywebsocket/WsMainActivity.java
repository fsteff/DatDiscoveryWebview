package aprivate.fsteff.datdiscoverywebsocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.fsteff.DiscoveryServer;

import java.net.InetSocketAddress;

public class WsMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DatView(this));

        DiscoveryServer srv = new DiscoveryServer();
        srv.start();
    }

}
