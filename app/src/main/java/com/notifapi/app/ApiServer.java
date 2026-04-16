package com.notifapi.app;

import java.io.IOException;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

public class ApiServer extends NanoHTTPD {

    public ApiServer() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        
        if (uri.equals("/notifications")) {
            return newFixedLengthResponse(
                Response.Status.OK,
                "application/json",
                NotificationService.notifications.toString()
            );
        }
        
        if (uri.equals("/clear")) {
            NotificationService.notifications = new org.json.JSONArray();
            return newFixedLengthResponse("cleared");
        }
        
        return newFixedLengthResponse("NotifAPI Running!");
    }
}
