
import java.util.Arrays;

public class Modul3 {

    public static void main(String[] args) {
        int[] array = {12, 23, -22, 0, 43, 545, -4, -55, 43, 12, 0, -999, -87};

        int posArrLength = 0;   //duzina niza koji sadrzi pozitivne elemente
        int negArrLength = 0;   //duzina niza koji sadrzi negativne elemente

        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                negArrLength++;     //povecati vrednost promenljive za jedan ako je element niza negativan
            } else {
                posArrLength++;     //nula je uzeta kao pozitivna vrednost
            }
        }

        int[] posArr = new int[posArrLength];   //niz duzine koja odgovara vrednosti "posArrLengrh"
        int[] negArr = new int[negArrLength];   //niz duzine koja odgovara vrednosti "negArrLengrh"

        /*Popunjavanje posArr i negArr nizova odgovarajucim vrednostima*/
        int p = 0;  //varijabla p oznacava trenutni indeks u nizu posArr
        int n = 0;  //varijabla n oznacava trenutni indeks u nizu negArr

        for (int var : array) {
            if (var < 0) {
                negArr[n] = var;
                n++;
            } else {
                posArr[p] = var;
                p++;
            }
        }

        System.out.println("posArr niz i njegovi elementi: posArr" + Arrays.toString(posArr));
        System.out.println("negArr niz i njegovi elementi: negArr" + Arrays.toString(negArr));

        /*---------------------------------------------------------------------------*/
 /*Odredjivanje duplikata u nizu*/
        int count = 0;  //broj duplikata

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j && array[i] == array[j]) {   //ne brojati jedan-isti element kao duplikat
                    count++;    //zbog dvostrukog prolaza kroz array, potrebno je vrednost "count"
                                //podeliti sa dva.
                }
            }
        }
        System.out.println("Broj elemenata niza \"array\" koji se ponavljaju: " + count / 2);

    }//end main

}//end class
