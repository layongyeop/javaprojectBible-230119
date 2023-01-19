package teacher;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import javax.swing.JTextPane;

public class WinBible extends JDialog {

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinBible dialog = new WinBible();
               dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
               dialog.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   private JComboBox cbChapter;
   private JComboBox cbVerse;
   private JComboBox cbBook;
   private JScrollPane scrollPane;
   private JTextPane taContents;

   /**
    * Create the dialog.
    */
   public WinBible() {
      setTitle("����å");
      setBounds(100, 100, 783, 690);
      
      JPanel panel = new JPanel();
      getContentPane().add(panel, BorderLayout.NORTH);
      panel.setBounds(0, 0, 100, 100);
      JButton btnBibleInsert = new JButton("���� �Է�");
      btnBibleInsert.setEnabled(false);
      btnBibleInsert.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
               //System.out.println("DB ���� �Ϸ�");               
               Statement statement=conn.createStatement();
               //=============================================               
               // bible.txt ���پ� �о ����Ͻÿ�.       
               try {
                  FileReader fr = new FileReader("D:/Project/bible.txt");
                  BufferedReader br = new BufferedReader(fr);
                  String line = "";   
                  String strBook="";
                  String strChapter="";
                  while((line=br.readLine()) != null) {
                     String abbr = line.substring(0,2);
                     int col_idx = line.indexOf(':');
                     if(isExist(abbr)) {
                        strBook = FullName(line.substring(0, 2));
                        strChapter = line.substring(2,col_idx);
                     }else {
                        strBook = FullName(line.substring(0, 1));   
                        strChapter = line.substring(1,col_idx);
                     }
                     int blank_idx = line.indexOf(' ');
                     String strVerse = line.substring(col_idx+1,blank_idx);
                     String strContent = line.substring(blank_idx+1);
                     
                     String sql = "insert into bibleTBL values(null,'";
                     sql = sql + strBook + "'," + strChapter + "," + strVerse + ",'";
                     sql = sql + strContent.replaceAll("'", "@") +"')";
                     //System.out.println(sql);
                     
                     if(statement.executeUpdate(sql)>0)
                        ;//System.out.println("����");
                     else
                        System.out.println(sql);
                     
                  }
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }   
               
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC ����̹� �ε� ����");
            } catch (SQLException e1) {
               System.out.println("DB ���� ����");
            }         
         }
      });
      panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
      panel.add(btnBibleInsert);
      
      cbBook = new JComboBox();
      cbBook.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct chapter from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "'";
               
               cbChapter.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbChapter.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC ����̹� �ε� ����");
            } catch (SQLException e1) {
               System.out.println("DB ���� ����");
            }
         }
      });
      cbBook.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct chapter from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "'";
               
               cbChapter.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbChapter.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC ����̹� �ε� ����");
            } catch (SQLException e1) {
               System.out.println("DB ���� ����");
            }
         }
      });
      cbBook.setModel(new DefaultComboBoxModel(new String[] {"â����", "��ֱ���", "������", "�μ���", "�Ÿ��", "��ȣ����", "����", "���", "�繫����", "�繫����", "���ձ��", "���ձ���", "�����", "������", "������", "����̾�", "������", "���", "����", "���", "������", "�ư�", "�̻��", "�����̾�", "�����̾߾ְ�", "������", "�ٴϿ�", "ȣ����", "�俤", "�Ƹ�", "���ٴ�", "�䳪", "�̰�", "����", "�Ϲڱ�", "���ٳ�", "�а�", "������", "�����", "���º���", "��������", "��������", "���Ѻ���", "�絵����", "�θ���", "��������", "�����ļ�", "�����Ƽ�", "�����Ҽ�", "��������", "��λ���", "����δϰ�����", "����δϰ��ļ�", "�������", "����ļ�", "�𵵼�", "������", "���긮��", "�߰���", "���������", "������ļ�", "�����ϼ�", "�����̼�", "���ѻＭ", "���ټ�", "���Ѱ�÷�"}));
      panel.add(cbBook);
      
      cbChapter = new JComboBox();
      cbChapter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
               Statement statement=conn.createStatement();
               //=============================================               
               String sql = "select distinct verse from bibleTBL where book='";
               sql = sql + cbBook.getSelectedItem() + "' and chapter=";
               sql = sql + cbChapter.getSelectedItem();
               
               cbVerse.removeAllItems();
               
               ResultSet rs = statement.executeQuery(sql);
               while(rs.next()) {
                  cbVerse.addItem(rs.getString(1));
               }
               //==============================================
            } catch (ClassNotFoundException e1) {
               System.out.println("JDBC ����̹� �ε� ����");
            } catch (SQLException e1) {
               System.out.println("DB ���� ����");
            }
         }
      });
      panel.add(cbChapter);
      
      cbVerse = new JComboBox();
      cbVerse.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		showChapter();
      	}
      });
      panel.add(cbVerse);
      
      JButton btnShow = new JButton("���溸��");
      btnShow.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showChapter();
         }
      });
      panel.add(btnShow);
      
      JPanel panel_1 = new JPanel();
      getContentPane().add(panel_1, BorderLayout.CENTER);
      panel_1.setLayout(new BorderLayout(0, 0));
      
      scrollPane = new JScrollPane();
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      panel_1.add(scrollPane, BorderLayout.CENTER);
      
      taContents = new JTextPane();
      taContents.setContentType("text/html"); // html �±� ��� ����
      taContents.setFont(new Font("����", Font.PLAIN, 20));
      scrollPane.setViewportView(taContents);
      
      cbBook.setSelectedIndex(0);
      showChapter();
   }

   protected void showChapter() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
         Statement statement=conn.createStatement();
         //=============================================               
         String sql = "select * from bibleTBL where book='";
         sql = sql + cbBook.getSelectedItem() + "' and chapter=";
         sql = sql + cbChapter.getSelectedItem();
         
         ResultSet rs = statement.executeQuery(sql);
         taContents.setText("");
         String temp = "<span style=\" font: bold 2em malgun gothic; color:red\">";
         temp = temp + cbBook.getSelectedItem() + " " + cbChapter.getSelectedItem() + "��</span><br>";
         while(rs.next()) {
            String strBook = rs.getString("book");
            String strChapter = rs.getString("Chapter");
            String strVerse = rs.getString("Verse");
            String strContents = rs.getString("Contents");   
            
            if(strVerse.equals(cbVerse.getSelectedItem())) {
            	 temp = temp + "<span style=\" font: 1em malgun gothic; color:blue\">" ;
                 temp = temp + strVerse + "�� " + strContents + "</span><br>"; 
            	
            }
            else {
                temp = temp + "<span style=\" font: 1em malgun gothic; color:black\">" ;
                temp = temp + strVerse + "�� " + strContents + "</span><br>";   
            	
            }
            
            
         
         }         
         taContents.setText("<html><body>" + temp + "</body></html>");
         taContents.setCaretPosition(0);
         //==============================================
      } catch (ClassNotFoundException e1) {
         System.out.println("JDBC ����̹� �ε� ����");
      } catch (SQLException e1) {
         System.out.println("DB ���� ����");
      }      
   }

   protected String FullName(String abbr) {
      String bibleAbbr[]= {"â","��","��","��","��","��","��","��","���","����","�ջ�","����","���","����","��","��","��","��","��","��","��","��","��","��","��","��","��","ȣ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","����","����","��","��","��","��","����","����","����","����","��","��","��","��","����","����","����","����","���","��","��"};
      String bibleFull[]= {"â����","��ֱ���","������","�μ���","�Ÿ��","��ȣ����","����","���","�繫����","�繫����","���ձ��","���ձ���","�����","������","������","����̾�","������","���","����","���","������","�ư�","�̻��","�����̾�","�����̾߾ְ�","������","�ٴϿ�","ȣ����","�俤","�Ƹ�","���ٴ�","�䳪","�̰�","����","�Ϲڱ�","���ٳ�","�а�","������","�����","���º���","��������","��������","���Ѻ���","�絵����","�θ���","��������","�����ļ�","�����Ƽ�","�����Ҽ�","��������","��λ���","����δϰ�����","����δϰ��ļ�","�������","����ļ�","�𵵼�","������","���긮��","�߰���","���������","������ļ�","�����ϼ�","�����̼�","���ѻＭ","���ټ�","���Ѱ�÷�"};
      int idx = -1;
      for(int i=0; i<bibleAbbr.length; i++)
         if(bibleAbbr[i].equals(abbr)) {
            idx = i;
            break;
         }
      
      return bibleFull[idx];
   }

   protected boolean isExist(String abbr) {
      switch(abbr) {
      case "����":
      case "���":
      case "�ջ�":
      case "����":
      case "���":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "����":
      case "���":
         return true;
      }
      return false;
   }

}