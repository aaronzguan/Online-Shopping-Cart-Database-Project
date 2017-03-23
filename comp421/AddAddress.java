package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddAddress extends JFrame {
	JTextField name= new JTextField("Name");
	JTextField phonenum=new JTextField("Phone Number");
	JTextField streetaddr = new JTextField("Street Address");
	JTextField city = new JTextField("City");
	JTextField province = new JTextField("Province");
	JTextField postalcode = new JTextField("Postal Code");
	JButton add = new JButton("Add");
	MainFrame mainFrame = null;
	AddAddress frame = this;
	
	ResultSet rs;
	String sqlcode;
	SQL newaddr;
	int userid;
	int addrid;
	
	public AddAddress(int uid,SQL sqlo,MainFrame mainFrame){
		this.mainFrame = mainFrame;
		userid = uid;
		System.out.println("userid = "+ userid);
		newaddr=sqlo;
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(6,2));
		jpanel.add(new JLabel("Name:")); jpanel.add(name);
		jpanel.add(new JLabel("Phone Number:")); jpanel.add(phonenum);
		jpanel.add(new JLabel("Street Address: ")); jpanel.add(streetaddr);
		jpanel.add(new JLabel("City: ")); jpanel.add(city);
		jpanel.add(new JLabel("Province:")); jpanel.add(province);
		jpanel.add(new JLabel("Postal Code:")); jpanel.add(postalcode);
		this.add(jpanel,BorderLayout.CENTER);
		add.addActionListener(new addrlistener());
		add.setPreferredSize(new Dimension(20,40));
		this.add(add,BorderLayout.SOUTH);
	}
	public static void invoke (int uid, SQL sqlo,MainFrame  mainFrame){
		JFrame address = new AddAddress(uid,sqlo,mainFrame);
		address.setTitle("Add a new address");
		address.setVisible(true);
		address.setLocationRelativeTo(null);
		address.setSize(400,220);
		address.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	class addrlistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			sqlcode="select max(addrid) from address";
			rs=newaddr.QueryExchte(sqlcode);
			try {	
				rs.next();
				addrid= rs.getInt(1)+1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String Name= name.getText();
			String Pnum= phonenum.getText();
			String Streetaddr= streetaddr.getText();
			String City = city.getText();
			String Province = province.getText();
			String Postalcode = postalcode.getText();
			
			if(Name.trim().isEmpty()||Pnum.trim().isEmpty()||Streetaddr.trim().isEmpty()||City.trim().isEmpty()||Province.trim().isEmpty()||Postalcode.trim().isEmpty())
				JOptionPane.showMessageDialog(null, "It is required to fill in every blank","Error",JOptionPane.ERROR_MESSAGE);
			else
			{
				sqlcode="insert into address values ("+addrid+","+userid+", \'"+Name+"\', \'"+Pnum+"\', \'"+Province+"\', \'"+City+"\', \'"+Streetaddr+"\', \'"+Postalcode+"\')"; // Insert a new address of this user to the table address
				newaddr.WriteExcute(sqlcode);
				JOptionPane.showMessageDialog(null, "You have successfully added a new address", "Success", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(AddAddress.class.getResource("success.png")));
				
				
				mainFrame.setVisible(true);
				mainFrame.setSearchAndBuyButtonEnable(true);
				frame.dispose();
			}   
		}
	}
}