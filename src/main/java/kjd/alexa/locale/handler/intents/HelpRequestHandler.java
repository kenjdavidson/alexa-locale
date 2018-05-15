package kjd.alexa.locale.handler.intents;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import kjd.alexa.locale.handler.LocaledRequestHandler;

public class HelpRequestHandler extends LocaledRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("AMAZON.HelpIntent"));
	}

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, Locale locale) {
		String speech = getMessage(locale, 
				"HelpRequest.text", 
				"No help information in resources.");
		String title = getMessage(locale, 
				"HelpRequest.title", 
				"Help");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard(title, speech)
				.build();
	}

}
