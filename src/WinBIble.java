import java.awt.EventQueue;


import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import com.mysql.cj.protocol.Resultset;

import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.JScrollPane;

public class WinBIble extends JDialog {
	InputStream in;
	String strChapter = "";
	String book = "";
	String strVerse ="";
	String contents="";
	int chapter;
	int verse;
	private JComboBox cbBook;
	private JComboBox cbchaptor;
	private JComboBox cbVerse;
	protected String selectchapter;
	protected String selectBook;
	private JScrollPane scrollPane;
	private JTextArea taContents;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinBIble dialog = new WinBIble();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinBIble() {
		setTitle("\uC131\uACBD\uCC45");
		setBounds(100, 100, 1177, 692);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btnBibleInsert = new JButton("\uC131\uACBD \uC785\uB825");
		btnBibleInsert.setEnabled(false);
		btnBibleInsert.addActionListener(new ActionListener() {
		

			public void actionPerformed(ActionEvent e) {
				File file = new File("D:\\java_project/bible.txt");
				
				
				
			      try {
				         Class.forName("com.mysql.cj.jdbc.Driver");
				         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
				         System.out.println("DB ���� �Ϸ�");               
				         Statement statement=conn.createStatement();
				         //=============================================
				         
				 		try {
							in = new FileInputStream(file);
							BufferedReader br = new BufferedReader(new InputStreamReader(in));
							String line ="";
							int count = 0;
							
							
							
							while((line =br.readLine())!= null) {
//								System.out.println(line);
								int col_idx = line.indexOf(":");
								int blank_idx = line.indexOf(" ");
								if(col_idx!=-1) {
									String subline= line.substring(0,col_idx);
//									System.out.println(subline +count );
									if(blank_idx!=-1) {
										strChapter = subline.replaceAll("[^0-9]", "");		
										chapter = Integer.parseInt(strChapter);
										book = subline.substring(0,subline.length()-strChapter.length()); // ó������  ��ü ���̿��� é�� ���� �� �� ��ġ����
										book = bookFullName(book);
										strVerse = line.substring(col_idx+1,blank_idx);
										verse = Integer.parseInt(strVerse);
										contents = line.substring(blank_idx+1);
//										System.out.println(count +" / " +book+" / " + chapter + " / " + verse + " / " + contents);
										String sql = "INSERT INTO bibletbl values(null,'";
										sql = sql + book +"'," + chapter + "," + verse + ",'"+ contents+"')";
//										System.out.println(sql);
										
									
										
										
										count ++;
										
//										
										if(statement.executeUpdate(sql)>0)
								            System.out.println("����");
								         else
								            System.out.println(sql);
									}
								}
								
							
							}
						}
						catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				         
				         //==============================================
				      } catch (ClassNotFoundException e1) {
				         System.out.println("JDBC ����̹� �ε� ����");
				         e1.printStackTrace();
				      } catch (SQLException e1) {
				         System.out.println("DB ���� ����");
				      }
				
				
				
				
				
//				StringBuffer sb = new StringBuffer();
		
				
			}
			
		});
		panel.add(btnBibleInsert);
		
		cbBook = new JComboBox();
		cbBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 selectBook = (String) cbBook.getSelectedItem();
				try {
			         Class.forName("com.mysql.cj.jdbc.Driver");
			         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
			                 
			         Statement statement=conn.createStatement();
			         //=============================================
			         String sql = "select  distinct chapter from bibletbl where book ='"+selectBook+"'";
//			         System.out.println(sql);
			         ResultSet rs = statement.executeQuery(sql);
			         cbchaptor.removeAllItems();
			         while(rs.next()) {
//			        	 
			        	 cbchaptor.addItem(rs.getString(1));
			         }
			 	
			         
			         //==============================================
			      } catch (ClassNotFoundException e1) {
			         e1.printStackTrace();
			      } catch (SQLException e1) {
			      }
				
				
			}
		});
		panel.add(cbBook);
		
		cbchaptor = new JComboBox();
		cbchaptor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectchapter = (String) cbchaptor.getSelectedItem();
				try {
			         Class.forName("com.mysql.cj.jdbc.Driver");
			         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
			                 
			         Statement statement=conn.createStatement();
			         //=============================================
			         String sql = "select  distinct verse from bibletbl where chapter ="+selectchapter+" and book ='"+selectBook+"'";
//			         System.out.println(sql);
			         ResultSet rs = statement.executeQuery(sql);
			         cbVerse.removeAllItems();
			         while(rs.next()) {
			        	 
			        	 cbVerse.addItem(rs.getString(1));
			        	 
			         }
			 	
			         
			         //==============================================
			      } catch (ClassNotFoundException e1) {
			         e1.printStackTrace();
			      } catch (SQLException e1) {
			      }
				
				
				
			}
		});
		panel.add(cbchaptor);
		
		cbVerse = new JComboBox();
		cbVerse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectverse = (String) cbVerse.getSelectedItem();
				try {
			         Class.forName("com.mysql.cj.jdbc.Driver");
			         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
			                 
			         Statement statement=conn.createStatement();
			         //=============================================
			         String sql = "select  contents from bibletbl where verse ="+selectverse+" and book = '"+selectBook+"' and chapter = "+selectchapter;
			         System.out.println(sql);
//			         System.out.println(sql);
			         ResultSet rs = statement.executeQuery(sql);
			      while(rs.next()){
			         taContents.setText( taContents.getText() +selectBook +" " +selectchapter +":"+selectverse+"  "+rs.getString(1)+"\r\n");
			         break;
			      }
			 	
			         
			         //==============================================
			      } catch (ClassNotFoundException e1) {
			         e1.printStackTrace();
			      } catch (SQLException e1) {
			      }
			}
		});
		
		panel.add(cbVerse);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		taContents = new JTextArea();
		scrollPane.setViewportView(taContents);
		
		try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlcb", "root","1234");
	                 
	         Statement statement=conn.createStatement();
	         //=============================================
	         String sql_book = "SELECT distinct book from bibletbl";
	         ResultSet rs = statement.executeQuery(sql_book);

	         while(rs.next()) {
	        	 cbBook.addItem(rs.getString(1));
	         }
	 	
	         
	         //==============================================
	      } catch (ClassNotFoundException e1) {
	         e1.printStackTrace();
	      } catch (SQLException e1) {
	      }
		
	

	}





	protected String bookFullName(String addr) {
		String bibleAbbr[]= {"â", "��", "��", "��", "��", "��", "��", "��", "���", "����", "�ջ�", "����", "���", "����", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ȣ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "����", "����", "��", "��", "��", "��", "����", "����", "����", "����", "��", "��", "��", "��", "����", "����", "����", "����", "���", "��", "��"};
		String bibleFull[]= {"â����", "��ֱ���", "������", "�μ���", "�Ÿ��", "��ȣ����", "����", "���", "�繫����", "�繫����", "���ձ��", "���ձ���", "�����", "������", "������", "����̾�", "������", "���", "����", "���", "������", "�ư�", "�̻��", "�����̾�", "�����̾߾ְ�", "������", "�ٴϿ�", "ȣ����", "�俤", "�Ƹ�", "���ٴ�", "�䳪", "�̰�", "����", "�Ϲڱ�", "���ٳ�", "�а�", "������", "�����", "���º���", "��������", "��������", "���Ѻ���", "�絵����", "�θ���", "��������", "�����ļ�", "�����Ƽ�", "�����Ҽ�", "��������", "��λ���", "����δϰ�����", "����δϰ��ļ�", "�������", "����ļ�", "�𵵼�", "������", "���긮��", "�߰���", "���������", "������ļ�", "�����ϼ�", "�����̼�", "���ѻＭ", "���ټ�", "���Ѱ�÷�"};
		int idx = -1;
		for(int i =0 ; i<bibleAbbr.length;i++)	{
			if((bibleAbbr[i].equals(addr))) {
				idx = i;		
				break;
			}			
		}
		return bibleFull[idx];
		
	
	}
	
	

	



	 

}

