package service;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;


public class ToEnglish {
    
    public static String prevod(String word) throws Exception{
        
        String translate;
        ClassXPath cxp = new ClassXPath();
        XPath xp = XPathFactory.newInstance().newXPath();
        
        switch (word) {
                case "majka":
                    translate = xp.compile("//majka").evaluate(cxp.display().item(0));
                    break;
                case "otac":
                    translate = xp.compile("//otac").evaluate(cxp.display().item(0));
                    break;
                case "brat":
                    translate = xp.compile("//brat").evaluate(cxp.display().item(0));
                    break;
                case "sestra":
                    translate = xp.compile("//sestra").evaluate(cxp.display().item(0));
                    break;
                default:
                    translate = "This is se-en/en-sr dictionary. The word does not exist in this dictionary. Make sure you typed from-to languages in correct order.";
                    break;
            }
            return translate;
    }
    
}
