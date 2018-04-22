package kjd.alexa.locale;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.builder.CustomSkillBuilder;

import kjd.alexa.locale.handler.LaunchRequestHandler;

public class LocaleStreamHandler extends SkillStreamHandler {

	private static Skill getSkill() {
		return new CustomSkillBuilder()
				.addRequestHandler(
						new LaunchRequestHandler())
				.build();
	}
	
	public LocaleStreamHandler() {
		super(getSkill());
	}

}
