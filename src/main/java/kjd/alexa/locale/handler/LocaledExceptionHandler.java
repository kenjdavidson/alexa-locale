package kjd.alexa.locale.handler;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import lombok.extern.log4j.Log4j;

/**
 * Base {@link ExceptionHandler} used to provided {@link Locale} based {@link ResourceBundle}
 * customization in {@link Response}(s).  {@link ResourceBundle}(s) are not required, but default
 * values should be provided, as no {@link MissingResourceException} is thrown.
 * <p> 
 * {@link ResourceBundle}(s) require a resource base (filename prefix) when attempting to load, this
 * can be configured by using the {@code @ResourceBundleBase} annotation.  Without the
 * annotation, the full class name will be used (requiring the locale file to be within the same
 * class path.
 * <p>
 * A default {@link Log4j} logger is available with a default setting of WARN.  The logger 
 * settings can be overwritten by providing a custom properties file and using the 
 * environment variable {@code -Dlog4j.configuration=<config-file>} as described in the Log4j
 * documentation.
 * <p>
 * An basic extending class only needs to implement the {@link #handleRequest(HandlerInput, ResourceBundle)}
 * method:
 * <pre><code>
 * 	protected Optional<Response> handleRequest(HandlerInput input, Throwable throwable, Locale locale) {
 * 		String speech = getMessage(locale, 
 * 			"exception.text", 
 * 			throwable.getMessage());
 * 		return input.getResponseBuilder()
 * 			.withSpeech(speech)
 * 			.withReprompt(speech)
 * 			.withSimpleCard("Error", speech)
 * 			.build();
 * 	}
 * </code></pre>
 * 
 * @author kendavidson
 *
 */
public abstract class LocaledExceptionHandler extends LocaledHandler 
		implements ExceptionHandler {

	/**
	 * Handles the request by loading the {@link ResourceBundle} using the specified
	 * base file and then calling {@link #handleRequest(HandlerInput, ResourceBundle)}.
	 * 
	 * @param request
	 * @return 
	 */
	@Override
	public Optional<Response> handle(HandlerInput input, Throwable throwable) {

		String[] localeStrings = input.getRequestEnvelope().getRequest().getLocale().split("-");		
		getLogger().debug(String.format("Handling Exception %s caused from RequestEnvelope: %s", 
				throwable.getClass().getSimpleName(), input.getRequestEnvelope().toString()));
		
		Locale locale = new Locale(localeStrings[0], localeStrings[1]);
		getLogger().debug(String.format("Handling exception %s with Locale %s",
				throwable.getClass().getSimpleName(), locale.toString())); 
				
		return handleRequest(input, throwable, locale);
	}
	
	/**
	 * Perform the Request handling with a provided {@link ResourceBundle}.  The {@link ResourceBundle}
	 * can be {@code null}, in this case, the {@link RequestHandler} should provide a default
	 * {@link Response}.
	 * 
	 * @param intput
	 * @param locale
	 * @param throwable
	 * @return
	 */
	protected abstract Optional<Response> handleRequest(HandlerInput input, Throwable throwable, Locale locale);	
	
}
