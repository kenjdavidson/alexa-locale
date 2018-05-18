# SAM Local commands for testing
# Template:
# sam local invoke -t sam/${event.json} -n sam/environment.json AlexaLocaleStreamHandler

# LaunchRequest
sam local invoke AlexaLocaleStreamHandler --profile=kenneth -t template-local.yaml -e sam/launch-intent-event.json -n sam/environment.json 
sam local invoke AlexaLocaleStreamHandler --profile=kenneth -t template-local.yaml -e sam/launch-intent-event-fr.json -n sam/environment.json 

# HelpRequest
sam local invoke AlexaLocaleStreamHandler --profile=kenneth -t template-local.yaml -e sam/help-intent-event.json -n sam/environment.json 
sam local invoke --profile=kenneth -t template-local.yaml -e sam/help-intent-event-fr.json -n sam/environment.json AlexaLocaleStreamHandler

sam local invoke AlexaLocaleStreamHandler --env-vars sam/environment.json --event sam/launch-intent-event.json --profile kenneth --template /Users/kendavidson/eclipse-workspace/alexa-locale/.serverless.template