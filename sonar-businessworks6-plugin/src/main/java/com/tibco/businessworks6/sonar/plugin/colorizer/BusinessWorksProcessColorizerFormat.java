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
package com.tibco.businessworks6.sonar.plugin.colorizer;

import org.sonar.api.web.CodeColorizerFormat;
import org.sonar.colorizer.Tokenizer;

import com.google.common.collect.ImmutableList;
import com.tibco.businessworks6.sonar.plugin.language.ProcessLanguage;

import java.util.List;

public class BusinessWorksProcessColorizerFormat extends CodeColorizerFormat {

	  private static final String END_TAG = "</span>";
	
	public BusinessWorksProcessColorizerFormat() {
		super(ProcessLanguage.KEY);
	}

	@Override
	public List<Tokenizer> getTokenizers() {
		ImmutableList.Builder<Tokenizer> builder = ImmutableList.builder();

		//builder.addAll(XmlColorizer.createTokenizers());
		/*builder.add(new CDataDocTokenizer(span("k"), END_TAG));
		builder.add(new RegexpTokenizer(span("j"), END_TAG, "<!DOCTYPE.*>"));
		builder.add(new MultilinesDocTokenizer("<!--", "-->", span("j"),
				END_TAG));
		builder.add(new MultilinesDocTokenizer("</", ">", span("k"), END_TAG));
		builder.add(new CustomXmlStartElementTokenizer(span("k"), END_TAG, span("c"),
				END_TAG, span("s"), END_TAG));*/
		return builder.build();
	}
	
	private static String span(String clazz) {
	    return "<span class=\"" + clazz + "\">";
	  }
}
