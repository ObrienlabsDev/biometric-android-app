package dev.obrienlabs.biometric.android.business;

//import org.chromium.net.CronetEngine;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * https://chromium.googlesource.com/chromium/src/+/master/components/cronet/README.md
 */
public class HttpService {

    String responseString;
    Button button;

    public String getRequest( ConnectivityManager connMgr, Button button) throws Exception {
        //Integer integer = new Integer(2);
        this.button = button;

        String stringUrl = "http://local.obrienlabs.io:8889/nbi/api/latest?user=20250921";
        //ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            System.out.println(stringUrl);
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            // display error
            //txtInfo.setText("No network connection available.");
        }

/*

        // manifest
        // https://developer.android.com/training/basics/network-ops/connecting
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;
        //https://developer.android.com/reference/java/net/HttpURLConnection
        URL url = new URL("https://google.com");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            byte[] buffer = new byte[1000];
            int text = in.read();
            in.close();
            //String text = readIt(in,len);
            System.out.println(text);
        } finally {

            urlConnection.disconnect();
        }
*/
        /*CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
        CronetEngine cronetEngine = myBuilder.build();

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                "https://www.example.com", new MyUrlRequestCallback(), executor);

        UrlRequest request = requestBuilder.build();*/
        return responseString;//integer;
    }

    // https://github.com/obrienlabs/biometric_android/blob/master/app/src/main/java/com/obriensystems/blackbox/MainActivity.java
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

   // https://github.com/obrienlabs/biometric_android/blob/master/app/src/main/java/com/obriensystems/blackbox/MainActivity.java
    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            button.setText(result.substring(15));

            System.out.println(result);
        }

        // Given a URL, establishes an HttpUrlConnection and retrieves
        // the web page content as a InputStream, which it returns as
        // a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 1000;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); // ms
                conn.setConnectTimeout(15000); // ms
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                System.out.println(response);

                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                //responseString = contentAsString;
                //button.text = responseString.substring(5)
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }
}
