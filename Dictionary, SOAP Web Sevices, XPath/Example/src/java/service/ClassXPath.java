package service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class ClassXPath {
    
    public static NodeList display() throws Exception{
        
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("C:\\Users\\Rastko\\Documents\\NetBeansProjects\\Example\\Dictionary.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList)xp.compile("//dictionary").evaluate(d, XPathConstants.NODESET);
        
        return nl;
    }
    
}
