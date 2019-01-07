package flow.bgu.ac.il;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class EventQueue extends Endpoint implements MessageHandler.Whole<String> {
	private static final Logger LOG = Log.getLogger(EventQueue.class);

	private RemoteEndpoint.Async remote;

	@Override
	public void onClose(Session session, CloseReason close) {
		super.onClose(session, close);
		this.remote = null;
		LOG.info("WebSocket Close: {} - {}", close.getCloseCode(), close.getReasonPhrase());
	}

	public void onOpen(Session session, EndpointConfig config) {
		this.remote = session.getAsyncRemote();
		LOG.info("WebSocket Connect: {}", session);

		// Attach this as a message handler
		session.addMessageHandler(this);


	}


	public void onMessage(String message) {
		LOG.info("Received message {}", message);


	}
}
