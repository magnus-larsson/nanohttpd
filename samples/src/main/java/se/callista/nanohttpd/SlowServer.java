package se.callista.nanohttpd;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class SlowServer extends NanoHTTPD {

	private static final int PORT = 8080;
	private static final int CONNECTION_SLEEP_TIME = 5000;
	private static final int REQUEST_SLEEP_TIME = 3000;
	private static final String RESPONSE_FILE = "response.xml";

	public static void main(String[] args) {
        ServerRunner.run(SlowServer.class);
    }

    public SlowServer() {
    	super(PORT, CONNECTION_SLEEP_TIME);
    }

    @Override public Response serve(IHTTPSession session) {

		System.err.println("*** slow request handling (" + REQUEST_SLEEP_TIME + " ms)...");
		sleep(REQUEST_SLEEP_TIME);
		System.err.println("*** request handling progress...");

		Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        return new NanoHTTPD.Response(getResponse());
    }

	private void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}   

	private String response = null;
	private String getResponse() {
		if (response == null) {
			try {
				InputStream is = getClass().getClassLoader().getResourceAsStream(RESPONSE_FILE);
				response = IOUtils.toString(is);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return response;
	}
}