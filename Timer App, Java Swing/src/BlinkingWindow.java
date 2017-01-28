/**
 * Ova klasa definise sve neophodne funkcionalnosti prozora koji se pojavljuje nakon pritiska Start dugmeta prozora Settings.
 * Ova klasa nema main metod, sarzi samo dve metode i to za otvaranje i zatvaranje prozora.
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.Timer;

public class BlinkingWindow {

    static Timer t;
    static JFrame newFrame;

    public void startWindow(JSlider jSlider, Color c, JButton colorBtn, JButton startBtn, JLabel currentColor,
            JLabel speed, JCheckBox cb1, JCheckBox cb2, JSpinner jClock, JSpinner jTime) {  //startWindow metoda sa neophodnim parametrima

        newFrame = new JFrame();    //instanciranje objekta tipa JPanel i definisanje njegovih karakteristika
        JPanel vibrantBackground = new JPanel();
        vibrantBackground.setBackground(Color.WHITE);

        newFrame.add(vibrantBackground);
        newFrame.setSize(400, 300);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);

        t = new Timer(jSlider.getValue(), new ActionListener() {  //timer za promenu boje
            int counter = 1;

            @Override
            public void actionPerformed(ActionEvent e) {

                if (counter % 2 == 0) {
                    vibrantBackground.setBackground(Color.WHITE);
                } else {
                    vibrantBackground.setBackground(c);
                }
                counter = counter + 1;
            }
        });
        t.setRepeats(true);
        t.start();

        newFrame.addWindowListener(new WindowAdapter() {    //deaktivacija newFrame-a i ponovna aktivacija myFrame-a
            @Override
            public void windowDeactivated(WindowEvent e) {
                
                colorBtn.setEnabled(true); //zamrzavanje komponenti
                currentColor.setEnabled(true);
                speed.setEnabled(true);
                jSlider.setEnabled(true);
                startBtn.setEnabled(true);
                cb1.setEnabled(true);
                cb2.setEnabled(true);
                jClock.setEnabled(true);
                jTime.setEnabled(true);
            }
        });
    }// end startWindow method
    
    public void stopWindow(){
        t.stop();
    }// end stopWindow method

}//end class
