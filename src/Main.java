import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.regex.Pattern;


public class Main extends JFrame {
    JLabel name;
    JTextField nameF;
    JLabel motherName;
    JTextField motherNameF;

    JLabel age ;
    PlaceholderTextField day;
    PlaceholderTextField month;
    PlaceholderTextField year;
    PlaceholderTextField hour;
    PlaceholderTextField minuet;

    JButton done;

    JPanel mainPanel;

    JLabel backgroundImage;
    String ZodiacSignBasedOnBirthDate;
    String ZodiacSignBasedOnDateOfBirth;
    String ZodiacSignBasedOnTheMotherName;
    String[] zodiacSigns = {
            "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
            "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    };
    HashMap<Character,Integer> numericValue = new HashMap<>();

    public void setNumericValue(HashMap<Character, Integer> numericValue) {
        numericValue.put('ا',1);
        numericValue.put('أ',1);
        numericValue.put('ئ',1);
        numericValue.put('ء',1);
        numericValue.put('ب',2);
        numericValue.put('ت',400);
        numericValue.put('ث',500);
        numericValue.put('ج',3);
        numericValue.put('ح',8);
        numericValue.put('خ',600);
        numericValue.put('د',4);
        numericValue.put('ذ',700);
        numericValue.put('ر',200);
        numericValue.put('ز',7);
        numericValue.put('س',60);
        numericValue.put('ش',300);
        numericValue.put('ص',90);
        numericValue.put('ض',800);
        numericValue.put('ط',9);
        numericValue.put('ظ',900);
        numericValue.put('ع',70);
        numericValue.put('غ',1000);
        numericValue.put('ف',80);
        numericValue.put('ق',100);
        numericValue.put('ك',20);
        numericValue.put('ل',30);
        numericValue.put('م',40);
        numericValue.put('ن',50);
        numericValue.put('ه',5);
        numericValue.put('و',6);
        numericValue.put('ي',10);
    }

    public void createAndShowGUI(){
        backgroundImage = new JLabel(new ImageIcon("background/A simple and elegant.png"));
        backgroundImage.setLayout(new BorderLayout());
        mainPanel = new JPanel();
        name = new JLabel("Your name (In arabic)");
        nameF = new JTextField();
        motherName = new JLabel("Your mother's name (In arabic)");
        motherNameF = new JTextField();
        age = new JLabel("Berth Information :");
        day = new PlaceholderTextField("day:");
        month = new PlaceholderTextField("month:");
        year = new PlaceholderTextField("year:");
        hour = new PlaceholderTextField("hour: (24)");
        minuet = new PlaceholderTextField("minuet:");
        done = new JButton("Configure Your Zodiac signe");
        done.setBackground(new Color(165, 181, 171, 255));
        mainPanel.setLayout(null);
        name.setBounds(62,50,200,30);
        nameF.setBounds(272,50,200,30);
        motherName.setBounds(62,90,200,30);
        motherNameF.setBounds(272,90,200,30);
        age.setBounds(62,130,200,30);
        day.setBounds(102,170,100,30);
        month.setBounds(212,170,100,30);
        year.setBounds(322,170,100,30);
        hour.setBounds(162,210,100,30);
        minuet.setBounds(272,210,100,30);
        done.setBounds(162,250,210,30);
        mainPanel.setOpaque(false);

        mainPanel.add(name);
        mainPanel.add(nameF);
        mainPanel.add(motherName);
        mainPanel.add(motherNameF);
        mainPanel.add(age);
        mainPanel.add(day);
        mainPanel.add(month);
        mainPanel.add(year);
        mainPanel.add(hour);
        mainPanel.add(minuet);
        mainPanel.add(done);

        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameText = nameF.getText();
                String motherNameText = nameF.getText();
                if(!isArabic(nameText) || !isArabic(motherNameText)){
                    JOptionPane.showMessageDialog(mainPanel,"ONLY ARABIC TEXT IN THE NAMES FIELD");
                }
                if(isInteger(day.getText()) && isInteger(month.getText()) && isInteger(year.getText())
                        && isInteger(hour.getText()) && isInteger(minuet.getText())){
                    String dayValue = day.getText();
                    String monthValue = month.getText();
                    String yearValue = year.getText();
                    int hourValue = Integer.parseInt(hour.getText());
                    int minuetValue = Integer.parseInt(minuet.getText());

                    String birthdate = dayValue+"-"+monthValue+"-"+ yearValue;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    if(isValidDate(birthdate,formatter) && (hourValue<24 && hourValue>=0) && (minuetValue<60 && minuetValue>=0)){
                        LocalDate date =  LocalDate.parse(birthdate, formatter);
                        ZodiacSignBasedOnDateOfBirth = getAscendantSign(hourValue);
                        ZodiacSignBasedOnBirthDate = getZodiacSign(date);
                        ZodiacSignBasedOnTheMotherName = getZodiacSignBasedOnTheMotherName(motherNameText,nameText);

                        String finalInfo = "<html>"
                                         + "<b>Your Birthdate sign is :</b><br><big>"+ZodiacSignBasedOnBirthDate+"</big>"
                                         + "<br><br><b>Your sign based on the date of berth : </b><br><big>"+ZodiacSignBasedOnDateOfBirth+"</big>"
                                         + "<br><br><b>Your sign based of your mothers name : </b><br><big>"+ZodiacSignBasedOnTheMotherName+"</big>"
                                         + "<html>";

                        JOptionPane.showMessageDialog(mainPanel, finalInfo, "Message", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(mainPanel,"Invalid date information");
                    }
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"Invalid date information, only input Integer numbers");
                }
            }
        });

        backgroundImage.add(mainPanel);
        setContentPane(backgroundImage);
        setTitle("Zodiac App");
        setSize(535, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public boolean isArabic(String text) {
        Pattern arabicPattern = Pattern.compile("\\p{InArabic}");
        return arabicPattern.matcher(text).find();
    }
    public  boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public  boolean isValidDate(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static String getZodiacSign(LocalDate date) {
        MonthDay md = MonthDay.of(date.getMonth(), date.getDayOfMonth());

        if (md.isBefore(MonthDay.of(1, 20))) return "Capricorn";
        else if (md.isBefore(MonthDay.of(2, 19))) return "Aquarius";
        else if (md.isBefore(MonthDay.of(3, 21))) return "Pisces";
        else if (md.isBefore(MonthDay.of(4, 20))) return "Aries";
        else if (md.isBefore(MonthDay.of(5, 21))) return "Taurus";
        else if (md.isBefore(MonthDay.of(6, 21))) return "Gemini";
        else if (md.isBefore(MonthDay.of(7, 23))) return "Cancer";
        else if (md.isBefore(MonthDay.of(8, 23))) return "Leo";
        else if (md.isBefore(MonthDay.of(9, 23))) return "Virgo";
        else if (md.isBefore(MonthDay.of(10, 23))) return "Libra";
        else if (md.isBefore(MonthDay.of(11, 22))) return "Scorpio";
        else if (md.isBefore(MonthDay.of(12, 22))) return "Sagittarius";
        else return "Capricorn"; // December 22 onward
    }
    public  String getAscendantSign(int hour) {
        int index = (hour / 2) % 12;
        return zodiacSigns[index];
    }
    public String getZodiacSignBasedOnTheMotherName(String motherName,String name){
        int val=0;
        for(char c : name.toCharArray()){
            val+= numericValue.get(c);
        }
        for(char c : motherName.toCharArray()){
            val+= numericValue.get(c);
        }
        while(val>12){
            val-=12;
        }
        return zodiacSigns[--val];
    }
    public Main(){
        createAndShowGUI();
        setNumericValue(numericValue);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}