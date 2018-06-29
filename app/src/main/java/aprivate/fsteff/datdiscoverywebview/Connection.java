package aprivate.fsteff.datdiscoverywebview;

import android.webkit.JavascriptInterface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class Connection {
    private final SocketChannel channel;
    private Consumer<byte[]> onData;

    private ByteBuffer writeBuffer;
    private Thread reader, writer;

    public Connection(final SocketChannel channel) {
        this.onData = null;
        this.channel = channel;
        this.writeBuffer = ByteBuffer.allocateDirect(1000);

        reader = new Thread(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buf = ByteBuffer.allocateDirect(1000);
                try {
                    while (channel.isOpen()) {
                        int size = channel.read(buf);
                        byte[] cpy = new byte[size];
                        buf.get(cpy);
                        onData.accept(cpy);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        reader.setDaemon(true);

        writer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (channel.isOpen()) {
                        synchronized (writeBuffer) {
                            writeBuffer.wait();
                            if (writeBuffer.remaining() > 0) {
                                channel.write(writeBuffer);
                            }
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.setDaemon(true);
        writer.start();
    }
    @JavascriptInterface
    public void take(byte[] data) {
        if (!channel.isOpen()) return;

        synchronized (writeBuffer) {
            writeBuffer.put(data);
            writeBuffer.notifyAll();
        }
    }

    @JavascriptInterface
    public void setOnData(Consumer<byte[]> onData){
        if(onData == null) {
            this.onData = onData;
            this.reader.start();
        }
    }

    @JavascriptInterface
    public void close(){
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
