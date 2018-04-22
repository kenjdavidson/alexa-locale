package kjd.alexa.locale.handler;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

/**
 * Base {@link RequestHandler} used to provided {@link Locale} based {@link ResourceBundle}
 * customized {@link Response}(s). 
 * 
 * @author kendavidson
 *
 */
public abstract class LocaledRequestHandler implements RequestHandler {
	
	/**
	 * System environment property name used to specify 
	 */
	private static final String RESOURCE_ENV = "resources.bundleBase";
	
	/**
	 * Base name used to load {@link ResourceBundle} objects.
	 */
	private String bundleBase = this.getClass().getCanonicalName();
	
	/**
	 * Creates a new {@link LocaledRequestHandler} setting the {@link ResourceBundle}
	 * base name.
	 */
	public LocaledRequestHandler() {
		this.bundleBase = Optional
				.ofNullable(System.getenv(RESOURCE_ENV))
				.orElse(bundleBase);
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String[] localeStrings = input.getRequestEnvelope().getRequest().getLocale().split("_");
		Locale requestLocale = new Locale(localeStrings[0], localeStrings[1]);
		
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(bundleBase, requestLocale, this.getClass().getClassLoader());
		} catch(MissingResourceException ignored) {}
		
		return handleRequest(input, rb);
	}
	
	/**
	 * Perform the Request handling with a provided {@link ResourceBundle}.  The {@link ResourceBundle}
	 * can be {@code null}, in this case, the {@link RequestHandler} should provide a default
	 * {@link Response}.
	 * 
	 * @param intput
	 * @param rb
	 * @return
	 */
	protected abstract Optional<Response> handleRequest(HandlerInput intput, ResourceBundle rb);	
	
}
