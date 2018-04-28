package kjd.alexa.locale.handler;

import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

/**
 * Provides handling of Launch Intents from Alexa.  Attempts to lookup the {@code welcome}
 * resource; if it doesn't exist then a default English welcome is provided.
 * <p>
 * 
 * @author kendavidson
 *
 */
public class LaunchRequestHandler extends LocaledRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.requestType(LaunchRequest.class));
	}

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, ResourceBundle rb) {
		String speech = getMessage(rb, 
				"welcome", 
				"Welcome to the Alexa locale skill.");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard("Hello", speech)
				.build();
	}

}
