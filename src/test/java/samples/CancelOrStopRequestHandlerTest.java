package samples;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.handler.intents.CancelOrStopRequestHandler;
import kjd.alexa.util.HandlerUtilities;

@RunWith(JUnit4.class)
public class CancelOrStopRequestHandlerTest {
	
	private CancelOrStopRequestHandler stopCancel;
	
	@Before
	public void before() {
		this.stopCancel = new CancelOrStopRequestHandler();
	}
	
	@Test
	public void canHandle_shouldReturnTrue_whenCancelIntent() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.CancelIntent", "en-US");
		boolean canHandle = stopCancel.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}
	
	@Test
	public void canHandle_shoudlReturnTrue_whenStopIntent() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.StopIntent", "en-US");
		boolean canHandle = stopCancel.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}	

	@Test
	public void shouldReturnEnglishStop_whenEnglishLocale() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.StopIntent", "en-US");
		Optional<Response> response = stopCancel.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("You just stopped"));
	}
	
	@Test
	public void shouldReturnFrenchStop_whenFrenchLocale() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.StopIntent", "fr-CA");
		Optional<Response> response = stopCancel.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("Vous venez"));
	}
}
