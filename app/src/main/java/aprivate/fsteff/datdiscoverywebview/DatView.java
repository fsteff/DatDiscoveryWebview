package aprivate.fsteff.datdiscoverywebview;

import android.content.Context;
import android.webkit.WebView;

public class DatView extends WebView {
    public DatView(Context context) {
        super(context);

        this.getSettings().setJavaScriptEnabled(true);
        this.loadUrl("file:///android_asset/index.html");
        this.addJavascriptInterface(new Discovery(), "discovery");
        this.addJavascriptInterface(new JsLog(), "logcat");
    }
}
