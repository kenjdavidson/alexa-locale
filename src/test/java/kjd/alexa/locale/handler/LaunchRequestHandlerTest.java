package kjd.alexa.locale.handler;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.samples.LaunchRequestHandler;

@RunWith(JUnit4.class)
public class LaunchRequestHandlerTest {
	
	private LaunchRequestHandler launch;
	
	public static LaunchRequest getLocaledLaunchRequest(String locale) {
		return LaunchRequest.builder()
				.withRequestId("requestId")
				.withLocale(locale)
				.build();
	}
	
	public static RequestEnvelope getLocaledRequest(String locale) {
		return RequestEnvelope.builder()
				.withRequest(getLocaledLaunchRequest(locale))
				.build();
	}
	
	public static HandlerInput getHandlerInput(String locale) {
		return HandlerInput.builder()
				.withRequestEnvelope(getLocaledRequest(locale))
				.build();
	}
	
	@Before
	public void before() {
		this.launch = new LaunchRequestHandler();
	}
	
	@Test
	public void can_handle_launchRequest() {
		HandlerInput input = getHandlerInput("en_US");
		boolean canHandle = launch.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}

	@Test
	public void valid_english_request_english_response() {
		HandlerInput input = getHandlerInput("en_US");
		Optional<Response> response = launch.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("Welcome"));
	}
	
	@Test
	public void valid_french_request_french_response() {
		HandlerInput input = getHandlerInput("fr_CA");
		Optional<Response> response = launch.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("Bienvenue"));
	}
}
