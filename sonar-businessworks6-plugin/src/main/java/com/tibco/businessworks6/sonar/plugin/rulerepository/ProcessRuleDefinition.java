package com.tibco.businessworks6.sonar.plugin.rulerepository;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

import com.google.common.collect.ImmutableList;

public final class ProcessRuleDefinition implements RulesDefinition{
	public static final String REPOSITORY_KEY = "process";
	public static final String REPOSITORY_NAME = "BusinessWorks Process Repo";
	protected static final List<String> LANGUAGE_KEYS = ImmutableList.of("process");
	public static List<Class> checkRules;
	public static Class check[] = {	
			com.tibco.businessworks6.sonar.plugin.check.process.NoDescriptionCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.NumberofActivitiesCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.TransitionLabelCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.ChoiceOtherwiseCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.JDBCWildCardCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.JDBCHardCodeCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.MultipleTransitionCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.DeadLockCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.LogOnlyInSubprocessCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.JMSHardCodeCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.ForEachMappingCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.ForEachGroupCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.NumberofServicesCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.CheckpointAfterHttpCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.CheckpointAfterRESTCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.CheckpointAfterJDBCÇheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.CheckpointInTransation.class,
			com.tibco.businessworks6.sonar.plugin.check.process.JMSAcknowledgementModeCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.CriticalSectionCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.process.SubProcessInlineCheck.class
		};
	
	protected String rulesDefinitionFilePath() {
		return "/rules.xml";
	}

	public ProcessRuleDefinition() {
	}
	private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
		NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
		/*InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
		if (rulesXml != null) {
			RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
			rulesLoader.load(repository, rulesXml, Charsets.UTF_8.name());
		}*/
		RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
		annotationLoader.load(repository, getChecks());
		repository.done();
	}


	public static String getRepositoryKeyForLanguage(String languageKey) {
		return REPOSITORY_KEY;
	}

	public static String getRepositoryNameForLanguage(String languageName) {
		return REPOSITORY_NAME;
	}

	public void define(Context context) {
		for (String languageKey : LANGUAGE_KEYS) {
			/*defineRulesForLanguage(context, ProcessRuleDefinition.getRepositoryKeyForLanguage(languageKey), ProcessRuleDefinition.getRepositoryNameForLanguage(languageKey),
					languageKey);*/
			defineRulesForLanguage(context, ProcessRuleDefinition.REPOSITORY_KEY, ProcessRuleDefinition.REPOSITORY_NAME,
					languageKey);
		}
	}



	@SuppressWarnings("rawtypes")
	public static Class [] getChecks() {
		checkRules = Arrays.asList(check);
		return check;
	}

	/*@SuppressWarnings("rawtypes")
	public static Class [] getChecks() {
		return new Class[] {
				// Common
				com.tibco.businessworks6.sonar.plugin.check.process.NoDescriptionCheck.class			
				// Generic Reusable Rule for Hard Coded Values
				//com.tibco.businessworks6.sonar.plugin.check.activity.CustomHardCodedCheck.class,
				// HTTP Request Activity
				com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedHostCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedPortCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedTimeoutCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedUriCheck.class,
			// HTTP Receiver Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.receiver.HardCodedDestinationCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.receiver.HardCodedMaxSessionsCheck.class,
			// JMS Queue Request Reply Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.requestor.HardCodedDestinationCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.requestor.HardCodedTimeoutCheck.class,
			// JMS Queue Sender Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.sender.HardCodedDestinationCheck.class,
			// JMS Topic Publisher Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.topic.publisher.HardCodedDestinationCheck.class,
			// JMS Topic Subscriber
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.topic.subscriber.HardCodedDestinationCheck.class,			
		};
	}*/
}
