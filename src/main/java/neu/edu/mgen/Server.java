package neu.edu.mgen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.*;
import java.util.*;

 
public class Server extends JFrame implements ActionListener
{
    JTextField text;
    JPanel a1;
    Box vertical = Box.createVerticalBox();
    Server(){
        setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.ORANGE);
        p1.setBounds(0, 0 , 450, 70);
        p1.setLayout(null);
        add(p1);

        ImageIcon arrow1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image arrow2 = arrow1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon arrow3 = new ImageIcon(arrow2);
        JLabel back = new JLabel(arrow3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent var1){
                System.exit(0);;

            }
        }); 


        ImageIcon displayphoto = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image displayphoto2 = displayphoto.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon displayphoto3 = new ImageIcon(displayphoto2);
        JLabel profile = new JLabel(displayphoto3);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon videocallImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image videocallImageIcon2 = videocallImageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon videocallImageIcon3 = new ImageIcon(videocallImageIcon2);
        JLabel video = new JLabel(videocallImageIcon3);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon phonecallImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image phonecallImageIcon2 = phonecallImageIcon.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon phonecallImageIcon3 = new ImageIcon(phonecallImageIcon2);
        JLabel phone = new JLabel(phonecallImageIcon3);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        ImageIcon morevertImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image morevertImageIcon2 = morevertImageIcon.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon morevertImageIcon3 = new ImageIcon(morevertImageIcon2);
        JLabel morevert = new JLabel(morevertImageIcon3);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        JLabel name = new JLabel("USER1");
        name.setBounds(110, 15, 100, 18);
        name.setBackground(Color.WHITE);
        name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 10);
        status.setBackground(Color.GREEN);
        status.setFont(new Font("SAN SERIF", Font.BOLD, 14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(text);

        JButton send = new JButton("SEND");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(Color.ORANGE);
        send.setForeground(Color.BLACK);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(send);

        setSize(450, 700);
        setLocation(200, 50);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent var1){
        String output = text.getText();
      //  JLabel output1 = new JLabel(output);
        JPanel output2 = formatLabel(output);
       // output2.add(output1);
        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(output2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        text.setText("");
        repaint();;
        invalidate();
        validate();

        System.out.println(output);
    }

    public static JPanel formatLabel(String output){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel foutput = new JLabel("<html><p style=\"width: 150px\">"+ output + "</p></html>");
        foutput.setFont(new Font("Tahoma", Font.PLAIN, 16));
        foutput.setBackground(Color.ORANGE);
        foutput.setOpaque(true);
        foutput.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(foutput);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
        
    }

    public static void main( String[] args )
    {
        new Server();
    }
}
