package kjd.alexa.locale.handler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;

import kjd.alexa.locale.annotation.LocaleResourceBase;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * LocaledHandler provides standardized {@link ResourceBundle} locale response handling
 * to {@link RequestHandler} and {@link ExceptionHandler}
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
 * 
 * @author kendavidson
 *
 */
public abstract class LocaledHandler {

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
	 * Creates a new {@link LocaledRequestHandler} setting the {@link ResourceBundle}
	 * base name.
	 */
	public LocaledHandler() {
		this.bundleBase = initializeBundleBase().orElse(this.bundleBase);
		logger.debug("Using resource bundle base " + this.bundleBase);		
	}	
	
	/**
	 * Initialize the {@link ResourceBundle} baseName based on either the annotation
	 * or the class name.
	 * @return
	 */
	private Optional<String> initializeBundleBase() {
		String base = null;
		
		if (this.getClass().isAnnotationPresent(LocaleResourceBase.class)) {
			LocaleResourceBase annotation = this.getClass().getAnnotation(LocaleResourceBase.class);
			base = annotation.value();
		}
		
		return Optional.ofNullable(base);
	}
	
	/**
	 * Gets the ResourceBundle based on the class configuration.
	 * @param locale
	 * @return
	 */
	protected Optional<ResourceBundle> getResourceBundle(Locale locale) {
		ResourceBundle rb = null;
		
		try {
			rb = ResourceBundle.getBundle(getBundleBase(), locale, this.getClass().getClassLoader());			
			getLogger().debug(String.format("Handling using ResourceBundle %s", rb.getBaseBundleName()));			
		} catch(MissingResourceException ignored) {	
			getLogger().warn(String.format("Unable to find ResourceBundle %s_%s", 
					getBundleBase(), locale.toString()));
		}	
		
		return Optional.ofNullable(rb);
	}
	
	/**
	 * Looks up the message.
	 * 
	 * @param rb
	 * @param message
	 * @return
	 */
	protected String getMessage(Locale locale, String message) {
		return getMessage(locale, message, new Object[] {}, null);
	}
	
	/**
	 * Looks up the message.
	 * 
	 * @param rb
	 * @param message
	 * @param defaultMessage
	 * @return
	 */
	protected String getMessage(Locale locale, String message, String defaultMessage) {
		return getMessage(locale, message, new Object[] {}, defaultMessage);
	}
	
	/**
	 * Looks up the message.  If {@code ResourceBundle} is null or the resource doesn't exist
	 * then the default message is returned.
	 * 
	 * @param rb
	 * @param message
	 * @param args
	 * @param defaultMessage
	 * @return
	 */
	protected String getMessage(Locale locale, String message, Object[] args, String defaultMessage) {
		String output = defaultMessage;	
		Optional<ResourceBundle> rb = getResourceBundle(locale);
		
		if (rb.isPresent()) {
			try {
				output = MessageFormat.format(rb.get().getString(message), args); 
			} catch (MissingResourceException e) { }
		} 
		
		return output;
	}	
	
}
