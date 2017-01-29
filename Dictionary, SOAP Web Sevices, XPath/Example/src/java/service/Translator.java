/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Rastko
 */
@WebService
public interface Translator {
    
    @WebMethod
    String translate(String word, String from, String to) throws Exception;
    
}
