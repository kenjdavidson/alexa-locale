package kjd.alexa.util;

import java.time.OffsetDateTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;

public class HandlerUtilities {

	public static IntentRequest getLocaledLaunchRequest(String intent, String locale) {
		return IntentRequest.builder()
				.withRequestId("requestId")
				.withLocale(locale)
				.withTimestamp(OffsetDateTime.now())
				.withIntent(Intent.builder().withName(intent).build())
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
	
}
