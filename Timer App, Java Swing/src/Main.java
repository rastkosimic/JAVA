
/**
 * Ovaj zadatak resen je u potpunosti programabilno (bez koriscenja dizajnera). Aplikacija se sastoji od dve klase,
 * Main i BlinkingWindow klase. Main klasa sadrzi main metod koji pokrece jedinu nit ove aplikacije. U okviru main metode
 * dolazi do pokretanja aplikacije koja je smestena u System Tray-u iz koga je moguce dalje otvoriti Settings prozor ili
 * je zatvoriti pritiskom na opciju Close. Ukoliko se odabere opcija Settings otvara se prozor u kome je moguce manipulisati
 * bojom novog prozora, brzinom otvaranja novog prozora kao i brzinom blinkanja odnosno smenjivanja odabrane boje novog
 * prozora i njegove pocetne boje. Taj novi prozor se otvara i zatvara pozivanjem odgovarajucih metoda klase BlinkingWindow.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            Color c;

            @Override
            public void run() {

                PopupMenu pum = new PopupMenu();    //definisanje sadrzaja popup menija 
                MenuItem miSettings = new MenuItem("Settings"); //opcija za otvaranje novog prozora
                MenuItem miClose = new MenuItem("Close");   //jedina moguca opcija za gasenje programa
                miClose.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                miSettings.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JFrame myFrame = new JFrame();
                        myFrame.setSize(450, 300);
                        myFrame.setLayout(new GridLayout(0, 2, 5, 5));
                        myFrame.setTitle("Settings");

                        JButton colorBtn = new JButton("Choose a color");   //kreiranje komponenti prozora Settings
                        JLabel currentColor = new JLabel("no color selected");
                        JLabel speed = new JLabel("Speed: ");
                        speed.setHorizontalAlignment(JLabel.CENTER);
                        JSlider jSlider = new JSlider(100, 3000, (3000 - 100) / 2);
                        JButton startBtn = new JButton("Start");
                        JButton stopBtn = new JButton("Stop");

                        ButtonGroup myChoices = new ButtonGroup();
                        JCheckBox cb1 = new JCheckBox("on time");
                        JCheckBox cb2 = new JCheckBox("countdown (min)");

                        Calendar calendar = new GregorianCalendar();    //Definisanje prikazatacnog vremena u okviru jClock spiner kontrole
                        SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null,
                                null, Calendar.SECOND);

                        JSpinner jClock = new JSpinner(dateModel);  //spiner kontrola koja pokazuje vreme startovanja settings prozora

                        JFormattedTextField tf = ((JSpinner.DefaultEditor) jClock.getEditor()).getTextField();
                        DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
                        DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
                        formatter.setFormat(new SimpleDateFormat("hh:mm a"));

                        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) jClock.getEditor();
                        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.RIGHT);

                        JSpinner jTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));   //spiner kontrola koja definise za koliko minuta ce doci do pokretanja novog prozora

                        colorBtn.addActionListener(new ActionListener() {   //dodavanje slusaca colorBtn-u
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JColorChooser jcc = new JColorChooser();
                                JDialog d = JColorChooser.createDialog(null, "Choose color", true, jcc, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        c = jcc.getColor();
                                        String[] rgb = c.toString().split("\\[");
                                        currentColor.setText("Current color: ["+rgb[1]);//ispisivanje RGB sifre izabrane boje
                                    }
                                }, null);
                                d.setVisible(true);
                            }
                        });

                        startBtn.addActionListener(new ActionListener() { //dodavanje slusaca startBtn-u
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                colorBtn.setEnabled(false); //zamrzavanje komponenti
                                currentColor.setEnabled(false);
                                speed.setEnabled(false);
                                jSlider.setEnabled(false);
                                startBtn.setEnabled(false);
                                cb1.setEnabled(false);
                                cb2.setEnabled(false);
                                jClock.setEnabled(false);
                                jTime.setEnabled(false);

                                if (cb1.isSelected()) {

                                    String fullDate = dateModel.getValue().toString();  //dobijanje zeljenog vremena pokretanja prozora
                                    //u obzir su uzeti samo minuti
                                    String[] dateParts = fullDate.split("\\s+");

                                    String[] desiredTime = dateParts[3].split(":");
                                    int desiredMin = Integer.parseInt(desiredTime[1]);

                                    DateFormat dateFormat = new SimpleDateFormat("mm");
                                    Date date = new Date();

                                    int currentMin = Integer.parseInt(dateFormat.format(date));

                                    int delay = (desiredMin - currentMin) * 1000 * 60; //prebacivanje u minute

                                    Timer postponedWindow = new Timer(delay, new ActionListener() { //kreiranje objekta tipa Timer
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            BlinkingWindow bw = new BlinkingWindow();   //instanciranje objekta tipa BlinkingWindow
                                            bw.startWindow(jSlider, c, colorBtn, startBtn, currentColor, speed, cb1, cb2, jClock, jTime);   //pozivanje metode za startovanje novog prozora

                                        }
                                    });
                                    postponedWindow.setRepeats(false);
                                    postponedWindow.start();

                                } else if (cb2.isSelected()) {  //ista stvar samo za drugi jSpinner
                                    int miliSec = (Integer) jTime.getValue() * 1000 * 60;
                                    Timer postponedWindow = new Timer(miliSec, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            BlinkingWindow bw = new BlinkingWindow();
                                            bw.startWindow(jSlider, c, colorBtn, startBtn, currentColor, speed, cb1, cb2, jClock, jTime);

                                        }
                                    });
                                    postponedWindow.setRepeats(false);
                                    postponedWindow.start();
                                }
                            }
                        });

                        stopBtn.addActionListener(new ActionListener() {    //zatvaranje blicajuceg prozora klikom na stop dugme
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                BlinkingWindow bw = new BlinkingWindow();
                                bw.stopWindow();

                                colorBtn.setEnabled(true); //zamrzavanje komponenti
                                currentColor.setEnabled(true);
                                speed.setEnabled(true);
                                jSlider.setEnabled(true);
                                startBtn.setEnabled(true);
                                cb1.setEnabled(true);
                                cb2.setEnabled(true);
                                jClock.setEnabled(true);
                                jTime.setEnabled(true);
                                bw.newFrame.setVisible(false);

                            }
                        });

                        myChoices.add(cb1); //dodavanje komponenti u ButtonGroup
                        myChoices.add(cb2);

                        myFrame.add(cb1);   //dodavanje komponenti u Grid Layout
                        myFrame.add(jClock);
                        myFrame.add(cb2);
                        myFrame.add(jTime);
                        myFrame.add(colorBtn);
                        myFrame.add(currentColor);
                        myFrame.add(speed);
                        myFrame.add(jSlider);
                        myFrame.add(startBtn);
                        myFrame.add(stopBtn);

                        myFrame.setVisible(true);

                    }
                });
                pum.add(miSettings);    //dodavanje elemenata popup meniju
                pum.add(miClose);

                SystemTray st = SystemTray.getSystemTray(); //st objekat tipa SystemTray

                try {   //definisanje ikonice koja ce se pojaviti u System Tray-u i ToolTip poruke
                    Image img = ImageIO.read(new File("Robot.png"));
                    TrayIcon myTray = new TrayIcon(img);
                    myTray.setImageAutoSize(true);
                    myTray.setToolTip("Right click here to see the options");
                    myTray.setPopupMenu(pum);
                    st.add(myTray);
                } catch (Exception ex) {
                    System.out.println("Exception " + ex);
                }
            }
        });
    }
}
