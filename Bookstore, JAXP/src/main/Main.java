package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        XPathExpression xPathExpression = xPath.compile("//book[price>10]/publish_date | //book[price>10]/title | //book[price>10]/author | //book[price>10]/genre | //book[price>10]/price | //book[price>10]/description");// uslov za knjige skuplje od 10

        File xmlDocument = new File("catalog.xml");
        InputSource inputSource = new InputSource(new FileInputStream(xmlDocument));

        Object result = xPathExpression.evaluate(inputSource, XPathConstants.NODESET);

        NodeList nodeList = (NodeList) result;

        for (int i = 4; i < nodeList.getLength(); i = i + 6) { // biranje noda publish_date radi postavljanja drugog uslova da budu prikazani sve knjige izdate posle 2005
                    //svaki cetvrti cvor je publish_date cvor posle koga sledi jos jedan pa da bi se odabrao prvi sledeci publish_date cvor potrebno je dodati 6
            String publish_date = nodeList.item(i).getFirstChild().getNodeValue();
            String[] yrs = publish_date.split("-");
            int year = Integer.parseInt(yrs[0]);

            if (year > 2005) {
                
                System.out.println("Author: " + nodeList.item(i - 4).getFirstChild().getNodeValue());
                System.out.println("Title: " + nodeList.item(i - 3).getFirstChild().getNodeValue());
                System.out.println("Genre: " + nodeList.item(i - 2).getFirstChild().getNodeValue());
                System.out.println("Price: " + nodeList.item(i - 1).getFirstChild().getNodeValue());
                System.out.println("Publish date: " + nodeList.item(i).getFirstChild().getNodeValue());
                System.out.println("Description: " + nodeList.item(i + 1).getFirstChild().getNodeValue());
                System.out.print("\n");

            }//end if

        }//end for
    }//end main
}
