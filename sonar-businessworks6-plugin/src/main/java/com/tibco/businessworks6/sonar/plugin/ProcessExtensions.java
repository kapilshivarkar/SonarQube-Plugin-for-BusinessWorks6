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
package com.tibco.businessworks6.sonar.plugin;

import com.google.common.collect.ImmutableList;

import com.tibco.businessworks6.sonar.plugin.language.ProcessLanguage;
import com.tibco.businessworks6.sonar.plugin.rulerepository.ProcessRuleDefinition;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.sensor.ProcessRuleSensor;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.List;

public final class ProcessExtensions {

	public static final String SUB_CATEGORY_NAME = "Processes";

	private ProcessExtensions() {
	}

	@SuppressWarnings("rawtypes")
	public static List getExtensions() {
		ImmutableList.Builder<Object> builder = ImmutableList.builder();
		builder.add(ProcessLanguage.class);
		builder.add(ProcessSonarWayProfile.class);	
		builder.add(ProcessRuleDefinition.class);
		builder.add(ProcessRuleSensor.class);
		//builder.add(ProcessMetricSensor.class);
		builder.add(PropertyDefinition
				.builder(ProcessLanguage.FILE_SUFFIXES_KEY)
				.defaultValue(StringUtils.join(ProcessLanguage.DEFAULT_FILE_SUFFIXES,","))
				.name("Process file suffixes")
				.description(
						"Comma-separated list of suffixes for files to analyze.")
				.category(BusinessWorksPlugin.TIBCO_BUSINESSWORK_CATEGORY)
				.subCategory(SUB_CATEGORY_NAME)
				.onQualifiers(Qualifiers.PROJECT).build());
		return builder.build();
	}

}
