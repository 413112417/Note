package pers.xjh.server;

import android.util.Log;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xjh on 18-1-13.
 */

public class WebServer extends NanoHTTPD {

    public WebServer(int port) {
        super(port);
    }

    @Override
    protected Response serve(IHTTPSession session) {
        Map<String, String> header = session.getHeaders();
        Map<String, String> parms = session.getParms();

        try {
            session.parseBody(new HashMap<String, String>());
            Log.d("webServer", "session" + session.getParms());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        Log.d("webServer", "header" + header.toString() + ":" + parms.toString());
        return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT, "Hello World");
    }
}
