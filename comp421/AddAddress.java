package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddAddress extends JFrame {
	JTextField name= new JTextField("Name");
	JTextField phonenum=new JTextField("Phone Number");
	JTextField streetaddr = new JTextField("Street Address");
	JTextField province = new JTextField("Province");
	JTextField postalcode = new JTextField("Postal Code");
	JButton add = new JButton("Add");
	MainFrame mainFrame = null;
	AddAddress frame = this;
	SQL newaddr;
	int userid;
	
	public AddAddress(int uid,SQL sqlo,MainFrame mainFrame){
		this.mainFrame = mainFrame;
		userid = uid;
		newaddr=sqlo;
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(5,2));
		jpanel.add(new JLabel("Name:")); jpanel.add(name);
		jpanel.add(new JLabel("Phone Number:")); jpanel.add(phonenum);
		jpanel.add(new JLabel("Street Address: ")); jpanel.add(streetaddr);
		jpanel.add(new JLabel("Province:")); jpanel.add(province);
		jpanel.add(new JLabel("Postal Code:")); jpanel.add(postalcode);
		this.add(jpanel,BorderLayout.CENTER);
		add.addActionListener(new addrlistener());
		add.setPreferredSize(new Dimension(20,40));
		this.add(add,BorderLayout.SOUTH);
	}
	public static void invoke (int uid, SQL sqlo,MainFrame  mainFrame){
		int Userid;
		Userid = uid;
		SQL Addr=sqlo;
		JFrame address = new AddAddress(Userid,Addr,mainFrame);
		address.setTitle("Add a new address");
		address.setVisible(true);
		address.setLocationRelativeTo(null);
		address.setSize(400,200);
		address.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class addrlistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String Name= name.getText();
			String Pnum= phonenum.getText();
			String Streetaddr= streetaddr.getText();
			String Province = province.getText();
			String Postalcode = postalcode.getText();
			
			String sqlcode=""+userid+""; // Insert a new address of this user to the table address
			
			if(Name.isEmpty()||Pnum.isEmpty()||Streetaddr.isEmpty()||Province.isEmpty()||Postalcode.isEmpty())
				JOptionPane.showMessageDialog(null, "It is required to fill in every blank","Error",JOptionPane.ERROR_MESSAGE);
			else
			{
				newaddr.WriteExcute(sqlcode);
				mainFrame.setVisible(true);
				frame.dispose();
			}   
				// Insert
		}
	}
}
