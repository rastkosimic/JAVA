/** Ovaj zadatak sastoji se od dve klase, klase Server i klase Calculator koje kreiraju TCP konekciju. Klasa kalkulator je 
 * kreirana kao JFrame Form i koriscen je design tab za pravljenje GUI-ja a nakon drag-and-drop procedure dodavana je 
 * funkcionalnost svim komponentama. Ideja je da klasa Calculator bude ujedno i klijentska strana u okviru klijent-server zadatka. 
 * Odatle se prvo kreira nit koja pokrece prvo Server koji ima implementiranu Runnable klasu i pregazenu run() metodu u kojoj se 
 * vrsi citanje korisnicke poruke, njeno type cast-ovanje u double tip i konacno se vrsi i sabiranje. Rezultat se zatim vraca 
 * klijentu (klasi Calculator), gde biva procitan i ispisan na definisanoj text field komponenti.
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

        @Override
        public void run(){
        
        try (ServerSocket sServer = new ServerSocket(100);
                Socket cn = sServer.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(cn.getInputStream()));
                BufferedOutputStream out = new BufferedOutputStream(cn.getOutputStream())) {
            
            double[] a = new double[2]; //Obzirom da ce uvek postojati dva sabirka koristio sam niz umesto ArrayList-e (dinamickog niza)
            String message = in.readLine();
            String[] numbers = message.trim().split("\\+"); //parsovanje poruke
            
            for (Integer i = 0; i < numbers.length; i++) {  
                a[i] = Double.parseDouble(numbers[i]);  //smestanje parsovane poruke u niz a[]

            }
           
            double total = a[0]+a[1];   //sabiranje
           
            String totalStr = Double.toString(total);   //type cast-ovanje nazad u String tip
            out.write(totalStr.getBytes()); //ispis rezultata na BufferedOutputStream-u
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + 100);
            System.out.println(e.getMessage());

        }
//    }
        
    }//end main
        

}//end class
