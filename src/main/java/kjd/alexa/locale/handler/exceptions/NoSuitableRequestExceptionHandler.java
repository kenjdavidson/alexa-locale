package kjd.alexa.locale.handler.exceptions;

import java.util.Locale;
import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.UnhandledSkillException;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.handler.LocaledExceptionHandler;
import kjd.alexa.locale.handler.LocaledHandler;

public class NoSuitableRequestExceptionHandler extends LocaledExceptionHandler {

	@Override
	public boolean canHandle(HandlerInput input, Throwable throwable) {
		return (throwable instanceof UnhandledSkillException 
				&& throwable.getMessage().equals("Unable to find a suitable request handler"));
	}

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, Throwable throwable, Locale locale) {
		String speech = getMessage(locale, 
				"UnhandledSkillException.text", 
				throwable.getMessage());
		String title = getMessage(locale, 
				"UnhandledSkillException.title",
				"Cancel/Stop");		
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard(title, speech)
				.build();
	}

}
