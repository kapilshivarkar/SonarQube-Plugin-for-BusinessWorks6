package com.tibco.businessworks6.sonar.plugin.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;

@Rule(key = DeadLockCheck.RULE_KEY, name="Deadlock Detection Check", priority = Priority.BLOCKER, description = "There are many situations in which deadlocks can be created between communicating web services. This rule checks for deadlocks and infinite loops in BW6 process design.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.BLOCKER)
public class DeadLockCheck  extends AbstractProcessCheck {
	public static final String RULE_KEY = "DeadlockDetection";

	@Override
	protected void validate(ProcessSource processSource) {
		// whole logic is written in analyseDeadLock method of ProcessRuleSensor as this validation has to be taken place across processes
		
	}
}
