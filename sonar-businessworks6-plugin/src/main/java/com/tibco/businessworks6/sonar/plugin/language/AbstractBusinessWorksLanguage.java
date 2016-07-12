/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tibco.businessworks6.sonar.plugin.language;

import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import java.util.List;

/**
 * This class defines the XML language.
 * 
 * @author Kapil Shivarkar
 */
public abstract class AbstractBusinessWorksLanguage extends AbstractLanguage {

	protected Settings settings;

	/** The bw language key. */
	public static final String BW_KEY = "bw";
	/**
	 * Default constructor.
	 */
	protected AbstractBusinessWorksLanguage(Settings settings, String key, String languageName) {
		super(key, languageName);
		this.settings = settings;
	}
	
	protected AbstractBusinessWorksLanguage(String key, String languageName) {
		super(key, languageName);
	}
	
	protected String[] getFileSuffixes(String fileSuffixesKey, String[] defaultFileSuffixes) {
		String[] suffixes = filterEmptyStrings(settings
				.getStringArray(fileSuffixesKey));
		if (suffixes.length == 0) {
			suffixes = defaultFileSuffixes;
		}
		return suffixes;
	}

	protected String[] filterEmptyStrings(String[] stringArray) {
		List<String> nonEmptyStrings = Lists.newArrayList();
		for (String string : stringArray) {
			if (StringUtils.isNotBlank(string.trim())) {
				nonEmptyStrings.add(string.trim());
			}
		}
		return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
	}
}
