package kjd.alexa.locale.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ResourceBundle;

import kjd.alexa.locale.handler.LocaledRequestHandler;

/**
 * Annotates a class extending {@link LocaledRequestHandler} with a value or property
 * name used to modify the {@link ResourceBundle} base name.
 * <p>
 * Examples:
 * <pre><code>
 * @ResourceBase("LocaleMessages")
 * public class SomeRequestHandler extends LocaledRequestHandler
 * </code></pre>
 * or
 * <pre><code>
 * @ResourceBase(system="LOCALE_MESSAGES_BASE")
 * public class SomeRequestHandler extends LocaledRequestHandler
 * </code></pre>
 * <p>
 * 
 * @author kendavidson
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ResourceBase {

	/**
	 * Sets the baseName
	 * 
	 * @return
	 */
	String value();
	
}
