package kjd.alexa.locale;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.lang3.Validate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.model.Context;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.SessionEndedRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import samples.LaunchRequestHandlerTest;

/**
 * For more testing details see the following:
 * <p> 
 * {@code https://developer.amazon.com/docs/custom-skills/request-and-response-json-reference.html}
 * <p>
 * <strong>Header</strong>
 * <pre>
 * POST / HTTP/1.1
 * Content-Type : application/json;charset=UTF-8
 * Host : your.application.endpoint
 * Content-Length :
 * Accept : application/json
 * Accept-Charset : utf-8
 * Signature: 
 * SignatureCertChainUrl: https://s3.amazonaws.com/echo.api/echo-api-cert.pem
 * </pre>
 * <strong>Request Body {@link RequestEnvelope}</strong>
 * <ul>
 * 	<li>Version - request version</li>
 * 	<li>Session - session Object {@link Session}</li>
 * 	<li>Context - context object {@link Context}</li>
 * 	<li>Request - request Object {@link Request} can be one of the following:
 * 		<ul>
 * 			<li>{@link LaunchRequest}</li>
 * 			<li>{@link IntentRequest}</li>
 * 			<li>{@link SessionEndedRequest}</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * @author kendavidson
 *
 */
@RunWith(JUnit4.class)
public class AlexaLocaleStreamHandlerTest {

	private ObjectMapper mapper;
	
	private AlexaLocaleStreamHandler handler;
	
	@Before
	public void before() {
		handler = new AlexaLocaleStreamHandler();
		mapper = new ObjectMapper();
	}
	
	@Test
	public void testEnglishRequestEnvelope() throws IOException {
		RequestEnvelope englishLaunch = LaunchRequestHandlerTest.getLocaledRequest("en-CA");
		String output = getHandlerOutput(englishLaunch);
		
		Validate.isTrue(output.contains("Welcome"));
	}
	
	@Test
	public void testFrenchRequestEnvelope() throws IOException {
		RequestEnvelope englishLaunch = LaunchRequestHandlerTest.getLocaledRequest("fr-CA");
		String output = getHandlerOutput(englishLaunch);
		
		Validate.isTrue(output.contains("Bienvenue"));
	}	
	
	private String getHandlerOutput(RequestEnvelope envelope) throws IOException {
		byte[] json = mapper.writeValueAsBytes(envelope);
		InputStream is = new ByteArrayInputStream(json);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		handler.handleRequest(is, os, null);
		return new String(os.toByteArray(), Charset.defaultCharset());
	}
}
