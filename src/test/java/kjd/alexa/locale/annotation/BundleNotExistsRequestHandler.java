package kjd.alexa.locale.annotation;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.handler.LocaledRequestHandler;

/**
 * Test - {@link ResourceBundle} "annotated" doesn't exist.
 *
 * @author kendavidson
 *
 */
@LocaleResourceBase("annotated")
public class BundleNotExistsRequestHandler extends LocaledRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return true;
	}

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, Locale locale) {
		String speech = getMessage(locale, "hello", "Resource not found");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard("Locale Testing", speech)
				.build();
	}	
			
}
