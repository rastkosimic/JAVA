package service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Main {
    public static void main(String[] args) throws MalformedURLException, Exception {
        URL url = new URL("http://localhost:8080//Example/TranslatorImplService?wsdl");
        QName qname = new QName("http://service/", "TranslatorImplService");
        
        Service service = Service.create(url, qname);
        
        QName port = new QName("http://service/", "TranslatorImplPort");
        
        Translator translator = service.getPort(port, Translator.class);
        
        System.out.println(translator.translate("father", "english", "serbian"));
    }
}
