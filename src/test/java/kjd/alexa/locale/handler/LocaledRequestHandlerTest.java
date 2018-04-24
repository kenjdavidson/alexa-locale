package kjd.alexa.locale.handler;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;

@RunWith(JUnit4.class)
public class LocaledRequestHandlerTest {

	private Request getLocaledRequest(String locale) {
		return IntentRequest.builder()
				.withRequestId("requestId")
				.withLocale(locale)
				.withTimestamp(OffsetDateTime.now())
				.withIntent(Intent.builder().withName("TestIntent").build())
				.build();
	}
	
	private RequestEnvelope getLocaledRequestEnvelope(String locale) {
		return RequestEnvelope.builder()
				.withRequest(getLocaledRequest(locale))
				.build();
	}
	
	private HandlerInput getHandlerInput(String locale) {
		return HandlerInput.builder()
				.withRequestEnvelope(getLocaledRequestEnvelope(locale))
				.build();
	}
	
	@Test
	public void annotated_request_handler_finds_bundle() {
		BundleExistsRequestHandler handler = new BundleExistsRequestHandler();
		HandlerInput input = getHandlerInput("en_CA");
		
		boolean canHandle = handler.canHandle(input);
		Validate.isTrue(canHandle);
		
		Optional<Response> response = handler.handle(input);
		Validate.isTrue(response.isPresent());
		Validate.isTrue(response.get().getOutputSpeech().toString().contains("Good day"));
	}
	
	@Test
	public void annotated_request_handler_finds_bundle_fr() {
		BundleExistsRequestHandler handler = new BundleExistsRequestHandler();
		HandlerInput input = getHandlerInput("fr_CA");
		
		boolean canHandle = handler.canHandle(input);
		Validate.isTrue(canHandle);
		
		Optional<Response> response = handler.handle(input);
		Validate.isTrue(response.isPresent());
		Validate.isTrue(response.get().getOutputSpeech().toString().contains("Bonjour"));
	}	
	
	@Test
	public void annotated_request_handler_no_bundle() {
		BundleNotExistsRequestHandler handler = new BundleNotExistsRequestHandler();
		HandlerInput input = getHandlerInput("en_CA");
		
		boolean canHandle = handler.canHandle(input);
		Validate.isTrue(canHandle);
		
		Optional<Response> response = handler.handle(input);
		Validate.isTrue(response.isPresent());
		Validate.isTrue(response.get().getOutputSpeech().toString().contains("Resource not found"));		
	}
}
