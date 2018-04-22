package kjd.alexa.locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import com.amazon.ask.Skill;

@RunWith(JUnit4.class)
public class AlexaLocaleStreamHandlerTest {

	private Skill skill;
	
	@Before
	public void before() {
		skill = Mockito.mock(Skill.class);
	}
	
}
