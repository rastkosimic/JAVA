package service;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;


public class ToSerbian {
    
    public static String translation(String word) throws Exception{
       
        String translate;
        ClassXPath cxp = new ClassXPath();
        XPath xp = XPathFactory.newInstance().newXPath();
        
            switch (word) {
                case "mother":
                    translate = xp.compile("//mother").evaluate(cxp.display().item(0));
                    break;
                case "father":
                    translate = xp.compile("//father").evaluate(cxp.display().item(0));
                    break;
                case "brother":
                    translate = xp.compile("//brother").evaluate(cxp.display().item(0));
                    break;
                case "sister":
                    translate = xp.compile("//sister").evaluate(cxp.display().item(0));
                    break;
                default:
                    translate = "Ovo je sr-en/en-sr recnik. Ova rec ne postoji u recniku. Proverite da li ste uneli jezike u odgovarajucem nizu.";
                    break;
            }
            return translate; 
    }
    
    
            
}
