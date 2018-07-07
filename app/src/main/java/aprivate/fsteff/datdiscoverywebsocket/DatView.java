package aprivate.fsteff.datdiscoverywebsocket;

import android.util.Log;
import android.webkit.WebView;

import java.util.concurrent.atomic.AtomicInteger;

public class DatView extends WebView {

    private static final AtomicInteger injectedObjectCounter = new AtomicInteger(0);
    private static final String callbackFunction = "global.onInjectedJavaCallback";
    private WsMainActivity main;


    public DatView(WsMainActivity context) {
        super(context);

        main = context;

        getSettings().setJavaScriptEnabled(true);
        loadUrl("file:///android_asset/index.html");

    }
}
