package com.tibco.businessworks6.sonar.plugin.source;

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

import org.apache.commons.io.FileUtils;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Rule;
import org.sonar.api.utils.SonarException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.businessworks6.sonar.plugin.file.XmlFile;
import com.tibco.businessworks6.sonar.plugin.util.SaxParser;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
/*import com.tibco.utils.bw.helper.GvHelper;
import com.tibco.utils.bw.helper.XmlHelper;*/
import com.tibco.utils.bw.helper.XmlHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * A object representing a XML Source code document
 * on which validation rules ({@link Rule}) can be executed and 
 * violations ({@link Violation}) can be reported.
 * 
 * It uses SAX parser to load the XML {@link Document} in memory
 * based on a {@link File} or a simple {@link String}.
 * 
 * It provide a set of method to control validity of XML source
 * code fragments.
 * 
 * @author Kapil Shivarkar
 */
public class XmlSource extends AbstractSource {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger(XmlSource.class.getName());
	
	private XmlFile xmlFile;
	
	private Document documentNamespaceAware = null;
	private Document documentNamespaceUnaware = null;

	/**
	 * @param file
	 */
	public XmlSource(File file) {
		super();
		this.xmlFile = new XmlFile(file);
	}
	
	/**
	 * @param xmlFile
	 */
	public XmlSource(XmlFile xmlFile) {
		super();
		this.xmlFile = xmlFile;
	}
	
	/**
	 * @param code
	 */
	public XmlSource(String code) {
		super();
		setCode(code);
	}

	/**
	 * Create an {@link InputStream} based on the {@link XmlFile} file if defined
	 * or based on the source code {@link String}
	 * 
	 * @return created {@link InputStream}
	 */
	public InputStream createInputStream() {
		if (xmlFile.getIOFile() != null) {
			try {
				return FileUtils.openInputStream(xmlFile.getIOFile());
			} catch (IOException e) {
				throw new SonarException(e);
			}
		} else {
			return new ByteArrayInputStream(code.getBytes());
		}
	}

	/**
	 * Return root document. Be careful
	 * it will be null if the source has not
	 * been parsed yet
	 * 
	 * @param namespaceAware 
	 * @return documentNamespaceAware or documentNamespaceUnaware {@link Document}
	 */
	public Document getDocument(boolean namespaceAware) {
		return namespaceAware ? documentNamespaceAware
				: documentNamespaceUnaware;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.source.AbstractSource#parseSource(java.nio.charset.Charset)
	 */
	@Override
	public boolean parseSource(Charset charset) {
		xmlFile.checkForCharactersBeforeProlog(charset);

		documentNamespaceUnaware = parseFile(false);
		if (documentNamespaceUnaware != null) {
			documentNamespaceAware = parseFile(true);
		}
		return documentNamespaceUnaware != null
				|| documentNamespaceAware != null;
	}

	/**
	 * @param namespaceAware
	 * @return
	 */
	private Document parseFile(boolean namespaceAware) {
		//return new SaxParser().parseDocument(createInputStream(), namespaceAware);
		return new SaxParser().parseDocument(xmlFile.getIOFile(), namespaceAware);
	}

	/**
	 * @param node
	 * @return
	 */
	public int getLineForNode(Node node) {
		return SaxParser.getLineNumber(node) + xmlFile.getLineDelta();
	}

	/**
	 * Returns the line number where the prolog is located in the file.
	 */
	public int getXMLPrologLine() {
		return xmlFile.getPrologLine();
	}

	/**
	 * @return
	 */
	public boolean isPrologFirstInSource() {
		return xmlFile.hasCharsBeforeProlog();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return xmlFile.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.source.AbstractSource#create(org.sonar.api.resources.Resource, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Resource create(Resource parent, String key) {
		return create(); 
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public Resource create() {
		return new org.sonar.api.resources.File(xmlFile.getIOFile().getAbsolutePath()); 
	}
	
	/**
	 * @param rule
	 * @param node
	 * @param message
	 * @return
	 */
	public List<Violation> getViolationsHardCodedNode(Rule rule, Node node, String message){
		List<Violation> violations = new ArrayList<Violation>();
		if(node.getTextContent() == null || node.getTextContent().isEmpty()){
			violations.add(new DefaultViolation(rule, getLineForNode(node), message + " (empty)"));
		}else{
			/*if(!GvHelper.isGVReference(node.getTextContent())){
				violations.add(new DefaultViolation(rule, getLineForNode(node), message));
			}*/
		}
		return violations;
	}
	
	/**
	 * @param rule
	 * @param node
	 * @param elementDescription
	 */
	public void findAndValidateHardCodedNode(Rule rule, Node node, String elementDescription){
		for(Violation violation:getViolationsHardCodedNode(rule, node, elementDescription)){
			addViolation(violation);
		}	
	}
	
	
	/**
	 * Return hard coded violations of first parent {@link Element}'s child {@link Node} based on input childName
	 *  
	 * @param rule			violated {@link Rule}
	 * @param parent		parent {@link Element} on which lookup will be done
	 * @param childName		childName {@link Node} name sought
	 * @param message		violation message
	 * 
	 * @return List<Violation>
	 */
	public List<Violation> getViolationsHardCodedChild(Rule rule, Element parent, String childName, String message){
		List<Violation> violations = new ArrayList<Violation>();
		/*try{
			Element elem = XmlHelper.firstChildElement(parent, childName);
			violations.addAll(getViolationsHardCodedNode(rule, elem, message));
		}catch (Exception e) {
			LOGGER.info("context", e);
			violations.add(new DefaultViolation(rule, getLineForNode(parent), message + " (not found)"));
		}*/
		return violations;
	}
	
	/**
	 * Add hard coded violations of first parent {@link Element}'s child {@link Node} based on input childName
	 * 
	 * @param rule			violated {@link Rule}
	 * @param parent		parent {@link Element} on which lookup will be done
	 * @param childName		childName {@link Node} name sought
	 * @param message		violation message
	 */
	public void findAndValidateHardCodedChild(Rule rule, Element parent, String childName, String message) {
		for(Violation violation:getViolationsHardCodedChild(rule, parent, childName, message)){
			addViolation(violation);
		}	
	}
	
	/**
	 * Return hard coded violations of all elements related to the xPathQuery evaluated on the input context
	 * 
	 * @param rule			violated {@link Rule}
	 * @param context		input context {@link Node}
	 * @param xPathQuery 	XPath query {@link String}
	 * @param message		violations message {@link String}
	 * 
	 * @return List<Violation>
	 */
	public List<Violation> getViolationsHardCodedXPath(Rule rule, Node context, String xPathQuery, String message){
		// Init violation 
		List<Violation> violations = new ArrayList<Violation>();
		try{
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodesFound = (NodeList) xpath.evaluate(xPathQuery, context, XPathConstants.NODESET);
			int length = nodesFound.getLength();
			for (int i = 0; i < length; i++) {
				Node nodeFound = nodesFound.item(i);
				violations.addAll(getViolationsHardCodedNode(rule, nodeFound, message + " ("+i+"/"+length+")"));
			}		
		}catch (Exception e) {
			LOGGER.error("context", e);
			violations.add(new DefaultViolation(rule, getLineForNode(context), message + " (not found)"));
		}
		return violations;
	}

	/**
	 * Add hard coded violations of all elements related to the xPathQuery evaluated on the input context
	 * @see XmlSource#getViolationsHardCodedXPath(Rule, Node, String, String)
	 * 
	 * @param rule			violated {@link Rule}
	 * @param context		input context {@link Node}
	 * @param xPathQuery 	XPath query {@link String}
	 * @param message		violations message {@link String}
	 */
	public void findAndValidateHardCodedXPath(Rule rule, Node context, String xPathQuery, String message){
		for(Violation violation:getViolationsHardCodedXPath(rule, context, xPathQuery, message)){
			addViolation(violation);
		}
	}

	/**
	 * Return a list of violation if the input mapping context ({@link Node}) is hard coded
	 * @param rule		violated {@link Rule} 
	 * @param context	input mapping {@link Node} to validate 
	 * @param message	violations message {@link String}
	 * 
	 * @return List<Violation>
	 */
	public List<Violation> getViolationsHardCodedMapping(Rule rule, Node context, String message) {
		List<Violation> violations = new ArrayList<Violation>();
		// By default no violation
		boolean violated = false;
		int line = getLineForNode(context);
		if(context.getTextContent() != null && !context.getTextContent().trim().isEmpty()){
			// If forced constant set violated
			violated = true;
		}else{
			// Select value-of child node 
			Node valueOf = XmlHelper.firstChildElement((Element)context, "http://www.w3.org/1999/XSL/Transform", "value-of");
			if(valueOf != null){
				// Select select attribute
				Node select = valueOf.getAttributes().getNamedItem("select");
				if(select != null){
					// Get select content
					String selectFormula = select.getTextContent();
					if(selectFormula.startsWith("\"") && selectFormula.endsWith("\"")){
						// If double quoted string set violated
						violated = true;
						line = getLineForNode(select);
					}else if(selectFormula.startsWith("'") && selectFormula.endsWith("'")){
						// If simple quoted string set violated
						violated = true;
						line = getLineForNode(select);
					}else if(selectFormula.matches("[0-9]+(.[0-9]*)?")){
						// If number set violated
						violated = true;
						line = getLineForNode(select);
					}else if("true()".equals(selectFormula)){
						// If true function set violated
						violated = true;
						line = getLineForNode(select);
					}else if("false()".equals(selectFormula)){
						// If false function set violated
						violated = true;
						line = getLineForNode(select);
					}
				}
			}
		}
		if(violated){
			// If violated add a new violation
			violations.add(new DefaultViolation(rule, line, message));
		}
		return violations;
	}
	
}
