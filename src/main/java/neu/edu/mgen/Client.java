package neu.edu.mgen;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;


import com.google.common.util.concurrent.Runnables;

import neu.edu.mgen.Server.ClientHandler;
import java.text.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

 
public class Client implements ActionListener
{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static JScrollPane scrollPane;
    static JPanel panel;
    static JPanel right;
    static List<String> messages = new ArrayList<>();
    static List<ClientHandler> clients = new ArrayList<>();
    static AtomicInteger messageIdCounter = new AtomicInteger(0);
   // static Map<Integer, JPanel> messagePanels = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private JTextField scheduledTimeInput;
    int messageId = messageIdCounter.incrementAndGet();
    private  Runnable sendScheduledMessage ;

    Client()
    {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.decode("#806d9e"));
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


        ImageIcon displayphoto = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
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

        JLabel name = new JLabel("USER2");
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
        a1.setBounds(5, 75, 540, 570);
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
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(send);

        f.setSize(540, 700);
        f.getContentPane().setBackground(Color.decode("#806d9e"));
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.setVisible(true);

        sendScheduledMessage = new Runnable() {
            @Override
            public void run() {
             
                String messageToSend = text.getText().trim(); 
                sendAndDisplayMessage (messageToSend);
            }
        }; 
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
        try
        {
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
        if (sbar != null) 
        {
            sbar.setValue(sbar.getMaximum());
        }  
        f.repaint();
        f.invalidate();
        f.validate();


        JButton deleteButton = new JButton("Delete");
        output2.add(deleteButton); 
       deleteButton.setActionCommand(Integer.toString(messageId));
          deleteButton.addActionListener(new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent e) 
              {
                  deleteMessage(right, messageId);
                  JOptionPane.showMessageDialog(f, "Message deleted successfully!");

              } 
          }); 
          } catch(Exception e)
          {
              e.printStackTrace();
          } 
          
      }

      public void sendAndDisplayMessage(String message) 
      {
        try 
        {
            JPanel output2 = new JPanel();
            if (message.substring(0, 1).equals("/")) 
            {
                output2 = ImgLabel(message);
            } else 
            {
                output2 = formatLabel(message, 1);
            }

            a1.setLayout(new BorderLayout());
            final JPanel right = new JPanel(new BorderLayout());
            right.add(output2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(message);
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
  
        } catch (Exception e) 
        {
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
            foutput.setBackground(Color.decode("#806d9e"));
        }
        else
        {
            foutput.setBackground(Color.decode("#5b95bd"));
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
        // morevert.setBounds(420, 20, 10, 25);
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
        private Socket socket;
        private DataInputStream din;
        private static DataOutputStream dout;

        public ClientHandler(Socket s, DataInputStream din, DataOutputStream dout) {
        this.socket = socket;
        this.din = din;
        this.dout = dout;

        }
  

        public void closeConnection() 
        {
        }



       @Override
        public void run() 
        {
            try 
            {
                while (true) 
                {
                    String msg = din.readUTF();
                        messages.add(msg);
                            JPanel panel = new JPanel();
                            if (msg.substring(0, 1).equals("/")) 
                            {
                                panel = ImgLabel(msg);
                            } else 
                            {
                                panel = formatLabel(msg, 0);
                            }
                            JPanel left = new JPanel(new BorderLayout());
                            left.add(panel, BorderLayout.LINE_START);
                            vertical.add(left);
                            f.validate();
                    }
                }
             catch (Exception e) 
             {
                e.printStackTrace();
            }
    } 
} 

public void sendDeleteMessage(int messageId) throws IOException 
{

    dout.writeUTF("DELETED:" + messageId);
} 


    public static void main( String[] args )
    {
        new Client();

        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
           while(true)
           {
                 a1.setLayout(new BorderLayout());
                 while(true){
                String msg = din.readUTF();
               messages.add(msg);
                System.out.println(msg);
                JPanel panel = new JPanel();
                if(msg.substring(0,1).equals("/")){
                    panel = ImgLabel(msg);
                }else
                {
                    panel = formatLabel(msg,0);
                }
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                 vertical.add(Box.createVerticalStrut(15));
                 a1.add(vertical, BorderLayout.PAGE_START);
               JScrollBar   sbar = scrollPane.getVerticalScrollBar(); 
                if (sbar != null) 
                {
                    sbar.setValue(sbar.getMaximum());
                }  
                f.validate();
            } 
        
        
    }
    }catch (Exception e)
    {
            e.printStackTrace();
        }

    }
}

    

