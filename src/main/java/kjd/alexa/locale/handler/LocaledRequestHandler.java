package kjd.alexa.locale.handler;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.annotation.LocaleResourceBase;
import kjd.alexa.util.JsonUtils;
import lombok.Getter;

/**
 * Base {@link RequestHandler} used to provided {@link Locale} based {@link ResourceBundle}
 * customized {@link Response}(s).  {@link ResourceBundle}(s) are not required, but default
 * values should be provided, as no {@link MissingResourceException} is thrown.
 * <p> 
 * 
 * @author kendavidson
 *
 */
public abstract class LocaledRequestHandler implements RequestHandler {
	
	/**
	 * Logger
	 */
	@Getter
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Base name used to load {@link ResourceBundle} objects.
	 */
	@Getter
	private String bundleBase = this.getClass().getCanonicalName();
	
	/**
	 * Json Utils
	 */
	@Getter
	private JsonUtils json;
	
	/**
	 * Creates a new {@link LocaledRequestHandler} setting the {@link ResourceBundle}
	 * base name.
	 */
	public LocaledRequestHandler() {
		this.bundleBase = initializeResourceFile().orElse(this.bundleBase);
		logger.debug("Using resource bundle base " + this.bundleBase);
		
		json = JsonUtils.builder()
				.build();
	}
	
	/**
	 * Initialize the {@link ResourceBundle} baseName.
	 * @return
	 */
	private Optional<String> initializeResourceFile() {
		String base = null;
		
		if (this.getClass().isAnnotationPresent(LocaleResourceBase.class)) {
			LocaleResourceBase annotation = this.getClass().getAnnotation(LocaleResourceBase.class);
			base = annotation.value();
		}
		
		return Optional.ofNullable(base);
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String[] localeStrings = input.getRequestEnvelope().getRequest().getLocale().split("_");
		String requestId = input.getRequestEnvelope().getRequest().getRequestId();		
		logger.debug(String.format("Handling input request %s with RequestEnvelope: %s", 
				requestId, new String(json.serialize(input.getRequestEnvelope()))));
		
		Locale requestLocale = new Locale(localeStrings[0], localeStrings[1]);
		logger.debug(String.format("Handling input request %s with Locale %s",
				requestId, requestLocale.toString()));
		
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(bundleBase, requestLocale, this.getClass().getClassLoader());			
			logger.debug(String.format("Handling input %s using ResourceBundle %s", 
					requestId, rb.getBaseBundleName()));			
		} catch(MissingResourceException ignored) {	
			logger.warn(String.format("Unable to find ResourceBundle %s_%s", 
					this.bundleBase, requestLocale.toString()));
		} 
				
		return handleRequest(input, rb);
	}
	
	/**
	 * Looks up the message.  If {@code ResourceBundle} is null or the resource doesn't exist
	 * then the default message is returned.
	 * 
	 * @param rb
	 * @param message
	 * @param defaultMessage
	 * @return
	 */
	protected String getMessage(ResourceBundle rb, String message, String defaultMessage) {
		String output = defaultMessage;	
		
		if (rb != null) {
			try {
				output = rb.getString(message);
			} catch (MissingResourceException e) {}
		}
		
		return output;
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
	protected abstract Optional<Response> handleRequest(HandlerInput input, ResourceBundle rb);	
	
}
