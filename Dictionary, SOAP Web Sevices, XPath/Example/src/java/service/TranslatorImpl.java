/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.jws.WebService;

/**
 *
 * @author Rastko
 */
@WebService(endpointInterface = "service.Translator")
public class TranslatorImpl implements Translator {

    @Override
    public String translate(String word, String from, String to) throws Exception {
        
        switch (from){
            case "serbian":
                return ToEnglish.prevod(word);
            case "english":
                return ToSerbian.translation(word);
            default:
                return "this is sr-en/en-sr dictionary";
    }
        
        

//        String translate;
//
//        ClassXPath cxp = new ClassXPath();
//        XPath xp = XPathFactory.newInstance().newXPath();
//
//        switch (word) {
//            case "majka":
//                translate = xp.compile("//majka").evaluate(cxp.display().item(0));
//                break;
//            case "otac":
//                translate = xp.compile("//otac").evaluate(cxp.display().item(0));
//                break;
//            case "brat":
//                translate = xp.compile("//brat").evaluate(cxp.display().item(0));
//                break;
//            case "sestra":
//                translate = xp.compile("//sestra").evaluate(cxp.display().item(0));
//                break;
//            case "mother":
//                translate = xp.compile("//mother").evaluate(cxp.display().item(0));
//                break;
//            case "father":
//                translate = xp.compile("//father").evaluate(cxp.display().item(0));
//                break;
//            case "brother":
//                translate = xp.compile("//brother").evaluate(cxp.display().item(0));
//                break;
//            case "sister":
//                translate = xp.compile("//sister").evaluate(cxp.display().item(0));
//                break;
//            default:
//                translate = "ova rec ne postoji u recniku";
//                break;
//        }
//        return translate;

//-------------------------------------------------------------------------------------
    }

}
