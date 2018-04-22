package kjd.alexa.locale.handler;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.Validate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

/**
 * Implements a {@link RequestHandler} for a {@link LaunchRequest}.
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
		String speech = (rb == null)
				? getDefaultSpeech(input) 
				: getLocaleSpeech(input, rb);
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard("Locale Testing", speech)
				.build();
	}
	
	private String getDefaultSpeech(HandlerInput input) {
		return "Welcome to the Alexa locale skill, you can't do much.";
	}
	
	private String getLocaleSpeech(HandlerInput input, ResourceBundle rb) {
		Validate.notNull(rb);
		
		try {
			return rb.getString("welcome");
		} catch (MissingResourceException ignored) { }
		
		return getDefaultSpeech(input);
	}
}
