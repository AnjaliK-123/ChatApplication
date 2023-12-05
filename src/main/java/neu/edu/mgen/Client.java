package neu.edu.mgen;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.*;
import java.util.*;

 
public class Client implements ActionListener
{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Client(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.ORANGE);
        p1.setBounds(0, 0 , 450, 70);
        p1.setLayout(null);
        f.add(p1);

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


        ImageIcon displayphoto = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
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
        final JPopupMenu popupMenu = new JPopupMenu();
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(320, 655, 123, 40);
        logoutButton.setBackground(Color.ORANGE);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.addActionListener(this);
        logoutButton.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                        JFrame frame = new JFrame("Login");
                        frame.setSize(450, 700);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        JPanel p1 = new JPanel();
                        p1.setBackground(Color.ORANGE);
                        p1.setBounds(0, 0 , 450, 70);
                        p1.setLayout(null);
                        frame.add(p1);
                        JPanel p2 = new JPanel();
                        placeComponents(p2);
                        frame.add(p2);

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
                        
                        frame.setVisible(true);
                    
            }
        });
        popupMenu.add(logoutButton);
        morevert.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent var1){
                if (SwingUtilities.isRightMouseButton(var1)) {
                    popupMenu.show(var1.getComponent(), var1.getX(), var1.getY());
                }
            }
        });

        JLabel name = new JLabel("USER2");
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
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("SEND");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(Color.ORANGE);
        send.setForeground(Color.BLACK);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent var1){
        try{
        String output = text.getText();
      //  JLabel output1 = new JLabel(output);
      JPanel output2 = new JPanel();
      if(output.substring(0,1).equals("/")){
          output2 = ImgLabel(output);
      }else{
          output2 = formatLabel(output);
      }
       // output2.add(output1);
        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(output2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(output);
        text.setText("");
        f.repaint();;
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }

       // System.out.println(output);
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
    public static JPanel ImgLabel(String output){
        JPanel panel = new JPanel();
        // System.out.println(output);
        // System.out.println(output.substring(0,1).equals("/"));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // JLabel foutput = new JLabel("<html><p style=\"width: 150px\">"+ output + "</p></html>");
        // foutput.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // foutput.setBackground(Color.ORANGE);
        // foutput.setOpaque(true);
        // foutput.setBorder(new EmptyBorder(15, 15, 15, 50));
        ImageIcon Icon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.png"));
        Image Icon2 = Icon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon Icon3 = new ImageIcon(Icon2);
        JLabel morevert = new JLabel(Icon3);
        // morevert.setBounds(420, 20, 10, 25);
        panel.add(morevert);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
        
    }

    private static void placeComponents(JPanel p2) {
        p2.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 100, 80, 25);
        p2.add(userLabel);

        final JTextField userTextField = new JTextField(20);
        userTextField.setBounds(180, 100, 165, 25);
        p2.add(userTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 150, 80, 25);
        p2.add(passwordLabel);

        final JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(180, 150, 165, 25);
        p2.add(passwordField);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(180, 200, 80, 25);
        p2.add(loginButton);

        final JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(100, 250, 200, 25);
         p2.add(messageLabel);

         
    }

    public static void main( String[] args )
    {
        new Client();

        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = new JPanel();
                if(msg.substring(0,1).equals("/")){
                    panel = ImgLabel(msg);
                }else{
                    panel = formatLabel(msg);
                }
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
                s.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
