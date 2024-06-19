package neu.edu.mgen;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import java.text.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.*;
import javax.swing.text.DefaultCaret;
 
public class Server implements ActionListener
{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static JScrollPane scrollPane;
    static JPanel panel;
    static JPanel right;
    static List messages = new ArrayList<>();
    static List<ClientHandler> clients = new ArrayList<>();
    static AtomicInteger messageIdCounter = new AtomicInteger(0);
   // static Map<Integer, JPanel> messagePanels = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private JTextField scheduledTimeInput;
    static int messageId = messageIdCounter.incrementAndGet();
    private static  Runnable sendScheduledMessage ;
    JTextField usernameField; 
    JPasswordField passwordField;
    static DataOutputStream dout;

    
    Server()
    {
            f.setLayout(null);
            JPanel p1 = new JPanel();
            p1.setBackground(Color.decode("#5b95bd"));
            p1.setBounds(0, 0 , 800, 70);
            p1.setLayout(null);
            f.add(p1);

            ImageIcon arrow1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
            Image arrow2 = arrow1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
            ImageIcon arrow3 = new ImageIcon(arrow2);
            JLabel back = new JLabel(arrow3);
            back.setBounds(5, 20, 25, 25);
            p1.add(back);
            back.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent var1)
                {
                    System.exit(0);;

                }
            }); 


            ImageIcon displayphoto = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
            Image displayphoto2 = displayphoto.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            ImageIcon displayphoto3 = new ImageIcon(displayphoto2);
            JLabel profile = new JLabel(displayphoto3);
            profile.setBounds(40, 10, 50, 50);
            p1.add(profile);


            ImageIcon morevertImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
            Image morevertImageIcon2 = morevertImageIcon.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
            ImageIcon morevertImageIcon3 = new ImageIcon(morevertImageIcon2);
            JButton morevert = new JButton(morevertImageIcon3);
            morevert.setBounds(520, 20, 10, 25);
            morevert.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            JComboBox<String> dropdownBox = new JComboBox<>();
            dropdownBox.addItem("Logout");
            JPopupMenu menu = new JPopupMenu();
            menu.add(dropdownBox); 
            morevert.setComponentPopupMenu(menu);
            p1.add(morevert);
            final JPopupMenu popupMenu = new JPopupMenu();
            JButton logoutButton = new JButton("Logout");
            logoutButton.setBounds(320, 655, 123, 40);
            logoutButton.setBackground(Color.ORANGE);
            logoutButton.setForeground(Color.BLACK);
            logoutButton.addActionListener(this);
            logoutButton.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
            logoutButton.addActionListener(new ActionListener() 
            {
            

                @Override
                public void actionPerformed(ActionEvent e) 
                {
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
                            loginFunc(p2, frame);
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
            morevert.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent var1){
                    if (SwingUtilities.isRightMouseButton(var1)) 
                    {
                        popupMenu.show(var1.getComponent(), var1.getX(), var1.getY());
                    }
                }
            });


            JLabel name = new JLabel("USER1");
            name.setBounds(110, 15, 100, 18);
            name.setBackground(Color.WHITE);
            name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
            p1.add(name);


            JLabel status = new JLabel("Active Now");
            status.setBounds(110, 35, 100, 25);
            status.setBackground(Color.GREEN);
            status.setFont(new Font("SAN SERIF", Font.BOLD, 14));
            p1.add(status);

            a1 = new JPanel();
            a1.setBounds(20, 75, 500, 570);
            f.add(a1);


            scrollPane = new JScrollPane(a1);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(530, 570));
            scrollPane.setBounds(5, 75, 530, 570);
            f.add(scrollPane);

            text = new JTextField();
            text.setBounds(5, 655, 290, 40);
            text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
            f.add(text);

            scheduledTimeInput = new JTextField();
            scheduledTimeInput.setBounds(300, 655, 75, 40);
            f.add(scheduledTimeInput);

            JButton scheduleButton = new JButton("Scheduler");
            scheduleButton.setBounds(380, 655, 75, 40);
            scheduleButton.addActionListener(new ActionListener() 
            {
                
            
            @Override
                public void actionPerformed(ActionEvent e) 
                {
                scheduleMessage();
                }
                
            }); 
            f.add(scheduleButton);

        


            JButton send = new JButton("SEND");
            send.setBounds(460, 655, 75, 40);
            send.setBackground(Color.decode("#2ecc71"));
            send.addActionListener(this);
            send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
            f.add(send);

        
            f.setSize(540, 700);
            f.getContentPane().setBackground(Color.decode("#5b95bd"));
            f.setLocation(200, 50);
            f.setUndecorated(true);
            f.setVisible(true);

            sendScheduledMessage = new Runnable() 
            {
                @Override
                public void run() 
                {
                    String messageToSend = text.getText().trim(); 
                    sendAndDisplayMessage (messageToSend);
                }
            }; 
    }

    private Color hexToColor(String string) 
    {
            return null;
    }

    public void scheduleMessage() 
    {
            try 
            {
                String scheduledTime = scheduledTimeInput.getText().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("America"));
                Date date = sdf.parse(scheduledTime);
                String time = getCurrentTime();
                Date current = sdf.parse(time);
                long delay = date.getTime() - current.getTime();
                System.out.println(delay);
                if (delay > 0) 
                {
                
                scheduler.schedule(sendScheduledMessage, delay, TimeUnit.MILLISECONDS);
                
                    JOptionPane.showMessageDialog(f, "Message scheduled successfully!");
                } else 
                {
                    JOptionPane.showMessageDialog(f, "Invalid scheduled time. Please enter a future time.");
                }
            } catch (ParseException ex) 
            {
                JOptionPane.showMessageDialog(f, "Invalid time format. Please enter time in HH:mm:ss format.");
            }
        
    } 

    public void actionPerformed(ActionEvent var1)
    {
        try{
        String output = text.getText();
        JPanel output2 = new JPanel();
        if(output.substring(0,1).equals("/"))
        {
          output2 = ImgLabel(output);
        }else
        {
          output2 = formatLabel(output,1);
        }
       
        a1.setLayout(new BorderLayout());
        final JPanel right = new JPanel(new BorderLayout());
        right.add(output2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(output);
    
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
        JScrollBar   sbar = scrollPane.getVerticalScrollBar(); 
        if (sbar != null) {
            sbar.setValue(sbar.getMaximum());
        } 
        f.repaint();
        f.invalidate();
        f.validate();
        System.out.println(output);
        System.out.println(messages);
       
        JButton deleteButton = new JButton("Delete");
        output2.add(deleteButton); 
        deleteButton.setActionCommand(Integer.toString(messageId));
        deleteButton.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e) {
            deleteMessage(right, messageId);
            JOptionPane.showMessageDialog(f, "Message deleted successfully!");
         
            }
            
        });
    
        } catch(Exception e){
            e.printStackTrace();
        } 
  
    }
   
    public static void sendAndDisplayMessage(String messages2) {
     
        try {
            JPanel output2 = new JPanel();
            if (messages2.substring(0, 1).equals("/")) 
            {
                output2 = ImgLabel(messages2);
            } else 
            {
                output2 = formatLabel(messages2, 1);
            }

            a1.setLayout(new BorderLayout());
            final JPanel right = new JPanel(new BorderLayout());
            right.add(output2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(messages2);
            JButton deleteButton = new JButton("Delete");
            output2.add(deleteButton); 
            deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                deleteMessage(right, messageId);
                JOptionPane.showMessageDialog(f, "Message deleted successfully!");
    
            } 
     }); 
     } catch(Exception e){
         e.printStackTrace();
     } 
        
    }

    public static String getCurrentTime() 
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
   
    public static JPanel formatLabel(String output,int a)
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel foutput = new JLabel("<html><p style=\"width: 150px\">"+ output + "</p></html>");
        foutput.setFont(new Font("Tahoma", Font.PLAIN, 16));
        if(a==1)
        {
            foutput.setBackground(Color.decode("#5b95bd"));
        }
        else
        {
            foutput.setBackground(Color.decode("#806d9e"));
            assert a==0;
        } 
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
    public static JPanel ImgLabel(String output)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ImageIcon Icon = new ImageIcon(ClassLoader.getSystemResource("emojis/smile.png"));
        Image Icon2 = Icon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon Icon3 = new ImageIcon(Icon2);
        JLabel morevert = new JLabel(Icon3);
        panel.add(morevert);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
        
    }

  

    public static void deleteMessage(JPanel panel, int messageId) 
    {
        vertical.remove(panel);
        f.repaint();
        f.revalidate();             
    } 


     static class ClientHandler implements Runnable 
     {
        public ClientHandler(Socket s, DataInputStream din, DataOutputStream dout) 
        {
        }

        public void closeConnection() 
        {
        }
        @Override
        public void run() 
        {   
        }
    }

    public static void loginFunc(JPanel p2,final JFrame frame) 
    {
        p2.setLayout(new GridBagLayout());

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

        loginButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());

                System.out.println("Entered username: " + username);
                System.out.println("Entered password: " + password);
                System.out.println("Action performed!");


                // For testing purposes, let's assume "testuser" is the correct username and "testpassword" is the correct password
                if ("testuser".equals(username) && "testpassword".equals(password)) 
                {
                    messageLabel.setText("Login successful");
                    frame.setVisible(false);
                    f.dispose();
                        new Server();
                    
                } 
                
                else 
                {
                    messageLabel.setText("Username or password is incorrect");
                }
            }
        });    
    }
    

    public static void main( String[] args )
    {
        new Server();

         try
         {
                    ServerSocket skt = new ServerSocket(6001);
                    while(true)
                    {
                        Socket s = skt.accept();
                        DataInputStream din = new DataInputStream(s.getInputStream());
                        dout = new DataOutputStream(s.getOutputStream());
                        while(true)
                        {
                            String msg = din.readUTF();
                            messages.add(msg);
                            ClientHandler clientHandler = new ClientHandler(s, din, dout);
                            clients.add(clientHandler);
                            Thread thread = new Thread(clientHandler);
                            thread.start();
                            JPanel panel = new JPanel();
                            if(msg.substring(0,1).equals("/"))
                            {
                                panel = ImgLabel(msg);
                            }else
                            {
                                panel = formatLabel(msg,0);
                            }
                            JPanel left = new JPanel(new BorderLayout());
                            left.add(panel, BorderLayout.LINE_START);
                            vertical.add(left);
                            JScrollBar   sbar = scrollPane.getVerticalScrollBar(); 
                            if (sbar != null) 
                            {
                                sbar.setValue(sbar.getMaximum());
                            }  
                            f.validate();
                            
                        }
                    }
                }

                catch(Exception e){
                e.printStackTrace();
                
        }
    }
}
