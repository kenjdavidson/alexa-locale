package kjd.alexa.locale.samples;

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
	protected Optional<Response> handleRequest(HandlerInput input, ResourceBundle rb) {
		String speech = getMessage(rb, 
				"help", 
				"No help information in resources.");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard("Hello", speech)
				.build();
	}

}
