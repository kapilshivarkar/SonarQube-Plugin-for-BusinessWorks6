package com.tibco.utils.bw.common;

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


import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public abstract class AbstractParser {

	private static final SAXParserFactory SAX_FACTORY_NAMESPCE_AWARE;
	private static final SAXParserFactory SAX_FACTORY_NAMESPCE_UNAWARE;

	/**
	 * Build the SAXParserFactory.
	 */
	static {

		SAX_FACTORY_NAMESPCE_AWARE = new SAXParserFactoryImpl();
		SAX_FACTORY_NAMESPCE_UNAWARE = new SAXParserFactoryImpl();
		
		try {
			setCommonConf(SAX_FACTORY_NAMESPCE_AWARE);
			SAX_FACTORY_NAMESPCE_AWARE.setNamespaceAware(true);
		} catch (Exception e) {
			//ignore error
		}
		
		try {
			setCommonConf(SAX_FACTORY_NAMESPCE_UNAWARE);
			SAX_FACTORY_NAMESPCE_UNAWARE.setNamespaceAware(false);
		} catch (Exception e) {
			//ignore error
		}
	}

	protected SAXParser newSaxParser(boolean namespaceAware)
			throws ParserConfigurationException, SAXException {
		return namespaceAware ? SAX_FACTORY_NAMESPCE_AWARE.newSAXParser()
				: SAX_FACTORY_NAMESPCE_UNAWARE.newSAXParser();

	}

	private static void setCommonConf(SAXParserFactory factory)
			throws SAXNotRecognizedException, SAXNotSupportedException,
			ParserConfigurationException {
		factory.setValidating(false);
		factory.setFeature("http://xml.org/sax/features/validation", false);
		factory.setFeature(
				"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
				false);
		factory.setFeature(
				"http://apache.org/xml/features/nonvalidating/load-external-dtd",
				false);
		factory.setFeature(
				"http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature(
				"http://xml.org/sax/features/external-parameter-entities",
				false);
	}

}
