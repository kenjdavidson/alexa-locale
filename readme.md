# Alexa Locale Testing

A project to provide simple Amazon Alexa and locale testing.  The LocaledRequestHandler provdes the abstraction
for looking up specific locale property files and allowing extending classes to easily return pre-defined
values.

## Installation

### Download and setup:

Option 1) Clone the git

Option 2) Include from Jitpack

```
<dependency>
  <groupId>com.github.kenjdavidson</groupId>
  <artifactId>alexa-locale</artifactId>
  <version>${alexa-locale.version}</version>
</dependency>
```

### Install AWS Toolkit

Install the [AWS Toolkit for Eclipse](https://aws.amazon.com/eclipse/).

>The AWS Toolkit for Eclipse is an open source plug-in for the Eclipse Java IDE that makes it easier for developers to develop, debug, and deploy Java applications using Amazon Web Services. With the AWS Toolkit for Eclipse, you’ll be able to get started faster and be more productive when building AWS applications.

### Install Properties Editor Plugin

Install the [Properties Plugin](https://marketplace.eclipse.org/content/properties-editor).  It isn't required, it just makes editing `*.properies` files a bit easier.

## Usage

Extend the `LocaledSkillHandler` (or one of the typed skill handler extensions):

```java
// src/main/java/kjd/alexa/locale/intents/LaunchRequestHandler.java
public class LaunchRequestHandler extends LocaledRequestHandler {

  ...

	@Override
	protected Optional<Response> handleRequest(HandlerInput input, Locale locale) {
		String speech = getMessage(locale, 
				"LaunchRequest.text",                   // Automatically look in locale file
				"Welcome to the Alexa locale skill.");  // Default language
		String title = getMessage(locale,
				"LaunchRequest.title",                  // Automatically look in locale file
				"Welcome");                             // Default language
		return input.getResponseBuilder()
				.withSpeech(speech)
				.withReprompt(speech)
				.withSimpleCard(title, speech)
				.build();
	}
}
```

and then provide the applicable resource file:

```properties
# src/main/java/kjd/alexa/locale/intents/LaunchRequestHandler.properties
LaunchRequest.text = Welcome to the Alexa locale skill, I don''t do much.
LaunchRequest.title = Welcome
```

### Enchancement

N/A

### Including 

N/A
