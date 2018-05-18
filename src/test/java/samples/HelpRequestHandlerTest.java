package samples;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import kjd.alexa.locale.handler.intents.HelpRequestHandler;
import kjd.alexa.util.HandlerUtilities;

@RunWith(JUnit4.class)
public class HelpRequestHandlerTest {
	
	private HelpRequestHandler help;
	
	@Before
	public void before() {
		this.help = new HelpRequestHandler();
	}
	
	@Test
	public void can_handle_launchRequest() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.HelpIntent", "en-US");
		boolean canHandle = help.canHandle(input);
		
		Assert.assertTrue(canHandle);
	}

	@Test
	public void valid_english_request_english_response() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.HelpIntent", "en-US");
		Optional<Response> response = help.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("You just requested help."));
	}
	
	@Test
	public void valid_french_request_french_response() {
		HandlerInput input = HandlerUtilities.getHandlerInput("AMAZON.HelpIntent", "fr-CA");
		Optional<Response> response = help.handle(input);
		
		Assert.assertTrue(response.isPresent());
		Assert.assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		Assert.assertTrue(response.get().getOutputSpeech().toString().contains("Vous venez de demander"));
	}
}
