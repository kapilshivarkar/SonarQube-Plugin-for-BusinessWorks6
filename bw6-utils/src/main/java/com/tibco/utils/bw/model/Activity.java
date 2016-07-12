package com.tibco.utils.bw.model;

import org.w3c.dom.Element;
import com.tibco.utils.bw.helper.XmlHelper;
public class Activity extends ProcessNode{

	public Element getConfiguration(){
		Element element = XmlHelper.firstChildElement((Element) getNode(), "config");
		return element;
	}
	
	public Element getInputBindings(){
		Element element = XmlHelper.firstChildElement((Element) getNode(), "inputBindings");
		return element;
	}
	
}
