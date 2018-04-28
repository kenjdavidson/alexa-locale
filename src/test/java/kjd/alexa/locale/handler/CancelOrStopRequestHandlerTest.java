package kjd.alexa.locale.handler;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;

@RunWith(JUnit4.class)
public class CancelOrStopRequestHandlerTest {
	
	private CancelOrStopRequestHandler help;
	
	public static IntentRequest getLocaledLaunchRequest(String intent, String locale) {
		return IntentRequest.builder()
				.withRequestId("requestId")
				.withLocale(locale)
				.withTimestamp(OffsetDateTime.now())
				.withIntent(Intent.builder().withName("AMAZON." + intent).build())
				.build();
	}
	
	public static RequestEnvelope getLocaledRequest(String intent, String locale) {
		return RequestEnvelope.builder()
				.withRequest(getLocaledLaunchRequest(intent, locale))
				.build();
	}
	
	public static HandlerInput getHandlerInput(String intent, String locale) {
		return HandlerInput.builder()
				.withRequestEnvelope(getLocaledRequest(intent, locale))
				.build();
	}
	
	@Before
	public void before() {
		this.help = new CancelOrStopRequestHandler();
	}
	
	@Test
	public void can_handle_cancelRequest() {
		HandlerInput input = getHandlerInput("CancelIntent", "en_US");
		boolean canHandle = help.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}
	
	@Test
	public void can_handle_stopRequest() {
		HandlerInput input = getHandlerInput("StopIntent", "en_US");
		boolean canHandle = help.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}	

	@Test
	public void valid_english_cancel_request_english_response() {
		HandlerInput input = getHandlerInput("StopIntent", "en_US");
		Optional<Response> response = help.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("You just stopped"));
	}
	
	@Test
	public void valid_french_stop_request_french_response() {
		HandlerInput input = getHandlerInput("StopIntent", "fr_CA");
		Optional<Response> response = help.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("Vous venez d''arrÃªter"));
	}
}