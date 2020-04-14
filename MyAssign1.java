import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class Book extends JFrame implements ActionListener
{
	Container cont;
	MainPanel mp;
	ShowPanel sp;
	
	Book(String s)
	{
		super(s);
		cont=getContentPane();
		cont.setLayout(null);
		
		mp = new MainPanel();
		sp = new ShowPanel();
		//mp.setVisible(true);
		sp.setVisible(false);
		
		mp.setBounds(0, 0, 900, 600);
		sp.setBounds(0, 0, 900, 600);
		
		mp.cancel.addActionListener(this); mp.cancel.setFont(mp.f);
		mp.done.addActionListener(this); mp.done.setFont(mp.f);
		mp.view.addActionListener(this); mp.view.setFont(mp.f);
		sp.back.addActionListener(this); sp.back.setFont(mp.f);
		
		cont.add(mp); cont.add(sp);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String s = ae.getActionCommand();
		if(s == "CANCEL")
		{
			mp.bid.setText("");
			mp.bna.setText("");
			mp.aut.setText("");
			mp.pub.setText("");
			mp.edi.setText("");
		}
		if(s == "DONE")
		{
			try
			{	
				File f = new File("E:\\ass2\\Book.txt");
			
				if(f.exists())
				{
					BufferedReader br=new BufferedReader(new FileReader("E:\\ass2\\Book.txt"));
					while(br.readLine()!=null);
					br.close();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\ass2\\Book.txt",true));
				
				bw.write(mp.bid.getText()); bw.newLine();
				bw.write(mp.bna.getText()); bw.newLine();
				bw.write(mp.aut.getText()); bw.newLine();
				bw.write(mp.pub.getText()); bw.newLine();
				bw.write(mp.edi.getText()); bw.newLine();
				bw.close();
			}
			catch(Exception e)
			{
				System.out.print("ERROR : "+e.getMessage());
			}
			mp.bid.setText("");
			mp.bna.setText("");
			mp.aut.setText("");
			mp.pub.setText("");
			mp.edi.setText("");			
		}
		
		if(s == "VIEW")
		{
			mp.setVisible(false);
			sp.setVisible(true);
			sp.vp = new ViewPanel();
			
			int count=0,i=0;
			
			String head[]={"BID","BNA","AUT","PUB","EDI"};
			
			try
			{
				BufferedReader br=new BufferedReader(new FileReader("E:\\ass2\\Book.txt"));
				
				while(br.readLine()!=null)
				{
					count++;
				}
				br.close();
				count = count/5;
				if(count!=0)
				{
					br=new BufferedReader(new FileReader("E:\\ass2\\Book.txt"));
				
					String data[][]=new String[count][5];
				
					while(count!=0)
					{
						data[i][0]=br.readLine();
						data[i][1]=br.readLine();
						data[i][2]=br.readLine();
						data[i][3]=br.readLine();
						data[i][4]=br.readLine();
						i++; count--;
					}
					br.close();
					
					sp.vp.jt=new JTable(data,head);
					sp.vp.jsp=new JScrollPane(sp.vp.jt);
					sp.vp.jsp.setBounds(100,50,300,150);
					sp.add(sp.vp.jsp);
					sp.vp.jsp.setVisible(true);
				}
			}
			catch(Exception e)
			{
				System.out.print("ERROR : "+e.getMessage());
			}
		}
		
		if(s == "BACK")
		{
			mp.setVisible(true);
			sp.setVisible(false);
		}
	}
}

class MainPanel extends JPanel implements ActionListener
{
	JButton cancel,done,view;
	JTextField bid,bna,aut,pub,edi;
	JLabel lbid,lbna,laut,lpub,ledi;
	Font f;
	
	MainPanel()
	{
		setLayout(null);
		f = new Font("Arial",Font.PLAIN,20);
		
		cancel = new JButton("CANCEL");
		done = new JButton("DONE");
		view = new JButton("VIEW");
		
		lbid = new JLabel("BOOK ID");
		lbna = new JLabel("BOOK NAME");
		laut = new JLabel("AUTHOR");
		lpub = new JLabel("PUBLICATION");
		ledi = new JLabel("EDITION");
		
		bid = new JTextField();
		bna = new JTextField();
		aut = new JTextField();
		pub = new JTextField();
		edi = new JTextField();
		
		cancel.setBounds(220, 400, 120, 50);
		done.setBounds(370, 400, 120, 50);
		view.setBounds(520, 400, 120, 50);
		
		lbid.setBounds(200, 100, 150, 30); lbid.setFont(f);
		lbna.setBounds(200, 150, 150, 30); lbna.setFont(f);
		laut.setBounds(200, 200, 150, 30); laut.setFont(f);
		lpub.setBounds(200, 250, 150, 30); lpub.setFont(f);
		ledi.setBounds(200, 300, 150, 30); ledi.setFont(f);
		
		bid.setBounds(400, 100, 250, 30);
		bna.setBounds(400, 150, 250, 30);
		aut.setBounds(400, 200, 250, 30);
		pub.setBounds(400, 250, 250, 30);
		edi.setBounds(400, 300, 250, 30);
		
		cancel.addActionListener(this); cancel.setFont(f);
		done.addActionListener(this); done.setFont(f);
		view.addActionListener(this); view.setFont(f);
		
		add(cancel); add(done); add(view);
		add(lbid); add(lbna); add(laut); add(lpub); add(ledi);
		add(bid); add(bna); add(aut); add(pub); add(edi);	
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}

class ShowPanel extends JPanel
{
	JButton back;
	ViewPanel vp;
	
	ShowPanel()
	{		
		setLayout(null);
		
		back = new JButton("BACK");
		back.setBounds(370, 400, 120, 50);
		
		add(back);
	}
}

class ViewPanel extends JPanel
{
	JScrollPane jsp=null;
	JTable jt=null;
	
	ViewPanel()
	{
		setLayout(null);
	}
}

public class MyAssign1
{
	public static void main(String[] args)
	{
		Book b=new Book("CSA");
		b.setVisible(true);
		b.setSize(900,600);
		b.setLocation(400,50);
	}
}
