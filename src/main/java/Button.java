import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Button extends JFrame
{
    JButton b1[][] = new JButton[10][10];
    String SPREADSHEET_ID = "19t0R6XnessAwnADpHB8RSkmelRyIBv_-VExsplhmylg";
    JLabel l1;
    int a[][] = new int[10][10];
    int co = 0,n = 0;
    Sheets sheetsService = SheetsServiceUtil.getSheetsService();
    Button() throws GeneralSecurityException, IOException {
        JFrame f1 = new JFrame();
        f1.addWindowListener(new window());
        f1.setSize(1920,1080);
        f1.setTitle("CHECKING");
        f1.setVisible(true);
        f1.setLayout(null);
        JPanel p1,p2,p3;
        l1= new JLabel("Label");
        l1.setBounds(40, 10, 100, 20);
        f1.add(l1);
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p1.setBounds(40,80,400,400);
        p1.setLayout(new GridLayout(10,3));
        p2.setBounds(470,80,400,400);
        p2.setLayout(new GridLayout(10,4));
        p3.setBounds(900,80,400,400);
        p3.setLayout(new GridLayout(10,3));
        JComboBox<Integer> c1 = new JComboBox<Integer>();
        c1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                co=c1.getItemAt(c1.getSelectedIndex());
            }
        });
        c1.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3,4,5,6,7,8,9,10,11,12}));
        c1.setFont(new Font("Times New Roman", Font.PLAIN, 19));
        c1.setBounds(470, 10, 100, 30);
        String range ="Sheet1!A1:J10";
        ValueRange res = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID,range)
                .execute();
        List<List<Object>> values = res.getValues();
        if (values == null || values.isEmpty())
        {
            System.out.println("Not Found");
        }
        else {
            for(int i=0;i<10;i++)
                for (int j = 0; j < 10; j++)
                    a[i][j] = Integer.parseInt((String) values.get(i).get(j));
        }
        for(int i=1;i<=10;i++)
        {
            for(int j=1;j<=3;j++)
            {
                b1[i-1][j-1] = new JButton((char)(64+i)+""+j);
                b1[i-1][j-1].setForeground(Color.white);
                if(a[i-1][j-1]==0)
                    b1[i-1][j-1].setBackground(Color.red);
                else
                    b1[i-1][j-1].setBackground(Color.green);
                p1.add(b1[i-1][j-1]);
                b1[i-1][j-1].addActionListener(new P1());
            }
            for(int j=4;j<=7;j++)
            {
                b1[i-1][j-1] = new JButton((char)(64+i)+""+j);
                if(a[i-1][j-1]==0)
                    b1[i-1][j-1].setBackground(Color.red);
                else
                    b1[i-1][j-1].setBackground(Color.green);

                b1[i-1][j-1].setForeground(Color.white);
                p2.add(b1[i-1][j-1]);
                b1[i-1][j-1].addActionListener(new P1());
            }
            for(int j=8;j<=10;j++)
            {
                b1[i-1][j-1] = new JButton((char)(64+i)+""+j);
                if(a[i-1][j-1]==0)
                    b1[i-1][j-1].setBackground(Color.red);
                else
                    b1[i-1][j-1].setBackground(Color.green);
                b1[i-1][j-1].setForeground(Color.white);
                p3.add(b1[i-1][j-1]);
                b1[i-1][j-1].addActionListener(new P1());
            }
        }
        f1.setResizable(false);
        f1.add(p1);
        f1.add(p2);
        f1.add(p3);
        f1.add(c1);
        f1.setLayout(null);
    }
    public class P1 implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            String s = ae.getActionCommand();
            Color c = Color.red;
            Color c1 = Color.green;
            Color c3 = Color.blue;
            int p1 =s.charAt(0)-65;
            int p2 = s.charAt(1)-49;
            if(s.length()==3)
                p2 = 9;
            Color c2 = b1[p1][p2].getBackground();
            if(!s.equals(""))
            {
                if(c2.getRGB()== c.getRGB())
                {
                    if(co<=n)
                        JOptionPane.showMessageDialog(null,"Tickets is Selected more than your limit");
                    else
                    {
                        n++;
                        b1[p1][p2].setBackground(Color.BLUE);
                        List<ValueRange> data = new ArrayList<>();
                        data.add(new ValueRange()
                                .setRange(s)
                                .setValues(Arrays.asList(Arrays.asList(1+""))));
                        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                                .setValueInputOption("USER_ENTERED")
                                .setData(data);
                        try {
                            BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets().values()
                                    .batchUpdate(SPREADSHEET_ID, batchBody)
                                    .execute();
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null,"Error"+e);
                        }
                    }
                }
                else
                if(c1.getRGB()== c2.getRGB())
                {
                    JOptionPane.showMessageDialog(null,"Ticket is Already Selected");
                }
                else
                if(c2.getRGB()==c3.getRGB())
                {
                    b1[p1][p2].setBackground(c);
                    n--;
                    try
                    {
                        List<ValueRange> data = new ArrayList<>();
                        data.add(new ValueRange()
                                .setRange(s)
                                .setValues(Arrays.asList(Arrays.asList(0+""))));
                        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                                .setValueInputOption("USER_ENTERED")
                                .setData(data);
                        try {
                            BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets().values()
                                    .batchUpdate(SPREADSHEET_ID, batchBody)
                                    .execute();
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null,"Error"+e);
                        }
                    }
                    catch(Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String a[]) throws GeneralSecurityException, IOException {
        new Button();
    }
}
