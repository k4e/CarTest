package com.example.testcontroller;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

class SocketClientTask extends AsyncTask<String, Void, Void>{
    /*
    private static final byte[] ADDRESS = new byte[] {
            (byte)192, (byte)168, (byte)11, (byte)119
    };
    */
    private static final byte[] ADDRESS = new byte[]{
            (byte) 192, (byte) 168, (byte) 11, (byte) 9
    };
    private static final int PORT = 12345;
    private static Socket sock = null;
    private final Activity activity;
    public SocketClientTask(Activity activity) {
        this.activity = activity;
        Log.d("debug","init");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... args) {
        String str = args[0];
        byte[] ascii = str.getBytes(Charset.forName("US-ASCII")); // 必ず ASCII コードにエンコードすること
        byte[] message = new byte[ascii.length + 1];
        System.arraycopy(ascii, 0, message, 0, ascii.length);
        message[ascii.length] = '\0';                             // 必ず NULL 文字を追加すること
        try {
            if (sock == null || sock.isClosed()) {
                InetAddress address = InetAddress.getByAddress(ADDRESS);
                //落ちる原因
                sock = new Socket(address, PORT);
                Log.d("debug","connet");
            }
            OutputStream out = sock.getOutputStream();
            out.write(message);
            out.flush();
            //Toast.makeText(activity, "DebugSend " + str, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(activity, "DebugError: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) { }
}
