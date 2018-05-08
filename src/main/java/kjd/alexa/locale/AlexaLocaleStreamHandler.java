package kjd.alexa.locale;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.builder.CustomSkillBuilder;

import kjd.alexa.locale.samples.LaunchRequestHandler;

public class AlexaLocaleStreamHandler extends SkillStreamHandler {

	/**
	 * Environment property used for Alexa skill Id.
	 */
	private static final String ENV_APP_ID = "alexa.app.id";
	
	private static Skill getSkill() {
		return new CustomSkillBuilder()
				.addRequestHandler(
						new LaunchRequestHandler())
				.withSkillId(System.getenv(ENV_APP_ID))
				.build();
	}
	
	public AlexaLocaleStreamHandler() {
		super(getSkill());
	}
	
}
