package kjd.alexa.locale.handler.intents;

import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import kjd.alexa.locale.handler.LocaledRequestHandler;

public class CancelOrStopRequestHandler extends LocaledRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("AMAZON.StopIntent")
				.or(Predicates.intentName("AMAZON.CancelIntent")));
	}

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, ResourceBundle rb) {
		String type = input.matches(Predicates.intentName("AMAZON.StopIntent")) ? "stop" : "cancel";
		String speech = getMessage(rb, 
				type + ".text", 
				"Stopping or cancelling the locale skill.");
		String title = getMessage(rb, 
				type + ".title", 
				"Cancel/Stop");
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard(title, speech)
				.build();
	}

}
