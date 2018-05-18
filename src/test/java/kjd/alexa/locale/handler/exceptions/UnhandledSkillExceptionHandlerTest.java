package kjd.alexa.locale.handler.exceptions;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.UnhandledSkillException;
import com.amazon.ask.model.Response;

import kjd.alexa.util.HandlerUtilities;

public class UnhandledSkillExceptionHandlerTest {

	UnhandledSkillExceptionHandler handler;
	
	@Before
	public void setup() {
		handler = new UnhandledSkillExceptionHandler();
	}
	
	@Test
	public void canHandle_whenCorrectUnhandledException() {
		HandlerInput input = HandlerUtilities.getHandlerInput("KJD.SomeIntent", "en-US");
		UnhandledSkillException exception = new UnhandledSkillException(new Exception("Unable to find a suitable request handler"));
		boolean canHandle = handler.canHandle(input, exception);
		
		Assert.assertTrue(canHandle);
	}

	@Test
	public void cantHandle_whenIncorrectUnhandledException() {
		HandlerInput input = HandlerUtilities.getHandlerInput("KJD.SomeIntent", "fr-CA");
		IllegalArgumentException exception = new IllegalArgumentException(new Exception("IllegalArgumentException"));
		boolean canHandle = handler.canHandle(input, exception);
		
		Assert.assertFalse(canHandle);
	}
	
	@Test
	public void shouldReturnEnglishMessage_whenExceptionHandled() {
		HandlerInput input = HandlerUtilities.getHandlerInput("KJD.SomeIntent", "en-US");
		UnhandledSkillException exception = new UnhandledSkillException(new Exception("Unable to find a suitable request handler"));
		Optional<Response> response = handler.handle(input, exception);
		
		assertTrue(response.isPresent());
		assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		assertTrue(response.get().getOutputSpeech().toString().contains("Unable to handle your request."));
	}
	
	@Test
	public void shouldReturnFrenchMessage_whenExceptionHandled() {
		HandlerInput input = HandlerUtilities.getHandlerInput("KJD.SomeIntent", "fr-CA");
		UnhandledSkillException exception = new UnhandledSkillException(new Exception("Unable to find a suitable request handler"));
		Optional<Response> response = handler.handle(input, exception);
		
		assertTrue(response.isPresent());
		assertTrue(response.get().getOutputSpeech().getType().equals("SSML"));
		assertTrue(response.get().getOutputSpeech().toString().contains("Impossible de gérer votre demande."));
	}	
	
}
