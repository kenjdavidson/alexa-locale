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

import com.amazon.ask.model.RequestEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;

import kjd.alexa.locale.handler.LaunchRequestHandlerTest;

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
		RequestEnvelope englishLaunch = LaunchRequestHandlerTest.getLocaledRequest("en_CA");
		String output = getHandlerOutput(englishLaunch);
		
		Validate.isTrue(output.contains("Welcome"));
	}
	
	@Test
	public void testFrenchRequestEnvelope() throws IOException {
		RequestEnvelope englishLaunch = LaunchRequestHandlerTest.getLocaledRequest("fr_CA");
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
