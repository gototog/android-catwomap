package gotocorp.catwomapp2.webservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui sert a faire physiquement l'appel au web serbvice.
 */
public class WebServiceHandler {
    static String response = null;
    private final static int GET = 1;
    private final static int POST = 2;
    private final static String URL = "http://catwomap.renaud-bredy.fr/v1/";
    public WebServiceHandler() {}

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    private String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
    /**
     * Making service call
     *
     * */
    public String doGetAlertsServiceCall() {

        List<NameValuePair> params =  new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("status", "active"));

        return this.makeServiceCall(URL.concat("alerts"), GET, params);
    }

    public String doAuthenticateServiceCall(String email, String pass) {
        pass = getSha1Hex(pass);
        List<NameValuePair> params =  new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("password", pass));

        return this.makeServiceCall(URL.concat("authenticate/").concat(email), POST, params);
    }

    private  String getSha1Hex(String clearString)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes)
            {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        }
        catch (Exception ignored)
        {
            ignored.printStackTrace();
            return null;
        }
    }


    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                Log.d("url POST", url);
                Log.d("url POST", httpPost.getParams().toString() );
                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                Log.d("url GET", url);
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            Log.d("Response  status", httpResponse.getStatusLine().toString() );
            response = EntityUtils.toString(httpEntity);

            if (httpResponse.getStatusLine().toString() == "HTTP/1.1 400 Bad Request" ) {
                response="400";
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
