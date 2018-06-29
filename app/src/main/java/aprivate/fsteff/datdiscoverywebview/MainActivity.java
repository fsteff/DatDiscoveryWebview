package aprivate.fsteff.datdiscoverywebview;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new DatView(this));
        //new TestDNS().start();
    }

    class TestDNS extends Thread{
        public void run() {
            Log.i("DNS", DatDNS.lookup("b3462ad8c9ddc312ab3749c2b52b88d504861c9c", DatDNS.defaultServers[0]).toString());
        }
    }
}
