package kjd.alexa.locale.handler;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.Validate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.annotation.LocaleResourceBase;

/**
 * Test - {@link ResourceBundle} "kjd.alexa.locale.handler.annotated" exists.
 * @author kendavidson
 *
 */
@LocaleResourceBase("messages.annotated")
public class BundleExistsRequestHandler extends LocaledRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return true;
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
		return "Resource not found.";
	}
	
	private String getLocaleSpeech(HandlerInput input, ResourceBundle rb) {
		Validate.notNull(rb);
		
		try {
			return rb.getString("hello");
		} catch (MissingResourceException ignored) { }
		
		return getDefaultSpeech(input);
	}	
	
}
