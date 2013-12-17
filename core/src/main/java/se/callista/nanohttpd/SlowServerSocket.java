package se.callista.nanohttpd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SlowServerSocket extends ServerSocket {

    private int connectionSleepTime;

    public SlowServerSocket(int connectionSleepTime) throws IOException {
    	this.connectionSleepTime = connectionSleepTime;
	}

	@Override
	public Socket accept() throws IOException {
		System.err.println("*** slow accept (" + connectionSleepTime + " ms)...");
		sleep(connectionSleepTime);
		System.err.println("*** accept progress...");
		
		return super.accept();
	}

	private void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}   
}