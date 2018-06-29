package aprivate.fsteff.datdiscoverywebview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsLog {
    @JavascriptInterface
    public void log(String msg){
        Log.i("JS", msg);
    }

    @JavascriptInterface
    public void debug(String msg){
        Log.d("JS", msg);
    }

    @JavascriptInterface
    public void error(String msg){
        Log.e("JS", msg);
    }
}
