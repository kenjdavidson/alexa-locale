package kjd.alexa.locale.handler;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.annotation.ResourceBase;
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
	private String bundleBase = this.getClass().getCanonicalName();
	
	/**
	 * Creates a new {@link LocaledRequestHandler} setting the {@link ResourceBundle}
	 * base name.
	 */
	public LocaledRequestHandler() {
		this.bundleBase = initializeResourceFile().orElse(this.bundleBase);	
	}
	
	/**
	 * Initialize the {@link ResourceBundle} baseName.
	 * @return
	 */
	private Optional<String> initializeResourceFile() {
		String base = null;
		
		if (this.getClass().isAnnotationPresent(ResourceBase.class)) {
			ResourceBase annotation = this.getClass().getAnnotation(ResourceBase.class);
			base = annotation.value();
		}
		
		return Optional.ofNullable(base);
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String[] localeStrings = input.getRequestEnvelope().getRequest().getLocale().split("_");
		Locale requestLocale = new Locale(localeStrings[0], localeStrings[1]);
		
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(bundleBase, requestLocale, this.getClass().getClassLoader());
			
			logger.info(String.format("Handling input %s using ResourceBundle %s", 
					input.getRequestEnvelope().getRequest().getType(), rb.getBaseBundleName()));
		} catch(MissingResourceException ignored) {	
			logger.warn(String.format("Unable to find ResourceBundle %s_%s", 
					this.bundleBase, requestLocale.toString()));
		} 
				
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
	protected abstract Optional<Response> handleRequest(HandlerInput input, ResourceBundle rb);	
	
}
