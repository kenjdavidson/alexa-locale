package kjd.alexa.locale.handler.intents;

import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import kjd.alexa.locale.handler.LocaledRequestHandler;

/**
 * Provides handling of Launch Intents from Alexa.  Messages are looked up based on 
 * the following:
 * <ul>
 * 	<li>launch.text - the content of the LaunchIntent speech and re-prompt text.</li>
 * 	<li>launch.title - the title text for Alexa devices with screens.</li>
 * </ul>
 * By extending and providing a specific {@link ResourceBundle} file 
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
				"launch.text", 
				"Welcome to the Alexa locale skill.");
		String title = getMessage(rb,
				"launch.title",
				"Welcome");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard(title, speech)
				.build();
	}

}
