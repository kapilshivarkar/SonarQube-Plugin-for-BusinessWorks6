/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.tibco.businessworks6.sonar.plugin.profile;

import org.sonar.api.profiles.AnnotationProfileParser;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

import com.tibco.businessworks6.sonar.plugin.BusinessWorksPlugin;
import com.tibco.businessworks6.sonar.plugin.language.AbstractBusinessWorksLanguage;
/*import com.tibco.businessworks6.sonar.plugin.rulerepository.SharedHttpRuleRepository;
import com.tibco.businessworks6.sonar.plugin.bw.rulerepository.SharedJdbcRuleRepository;
import com.tibco.businessworks6.sonar.plugin.rulerepository.SharedJmsRuleRepository;
 */
import java.util.ArrayList;

public class CommonRulesSonarWayProfile extends ProfileDefinition {

	public static final String SONAR_WAY_PROFILE_NAME = "BusinessWorks Common Profile";
	private final AnnotationProfileParser annotationProfileParser;

	public CommonRulesSonarWayProfile(
			AnnotationProfileParser annotationProfileParser) {
		this.annotationProfileParser = annotationProfileParser;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RulesProfile createProfile(ValidationMessages messages) {
		ArrayList<Class> classes = new ArrayList<Class>();
		//classes.addAll(ProcessRuleRepository.getChecks());
		/*classes.addAll(SharedHttpRuleRepository.getChecks());	
		classes.addAll(SharedJdbcRuleRepository.getChecks());	
		classes.addAll(SharedJmsRuleRepository.getChecks());*/	
		RulesProfile ruleProfile = annotationProfileParser.parse(BusinessWorksPlugin.TIBCO_BUSINESSWORK_RULEREPOSITORY,	SONAR_WAY_PROFILE_NAME, AbstractBusinessWorksLanguage.BW_KEY,
				classes, messages);
		return ruleProfile;
	}
}
