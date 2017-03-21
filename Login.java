package comp421;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame{
	JTextField userid = new JTextField ("user id");
	JTextField phonenumber = new JTextField("Phone number");
	JButton login = new JButton("Log in");
	
	SQL loginsql;
	ResultSet rs;
	String sqlcode;
	
	public Login(SQL sqlo){
		loginsql=sqlo;
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,2));
		panel1.add(new JLabel("User id: ")); panel1.add (userid);
		panel1.add(new JLabel("Phone Number: "));panel1.add(phonenumber);
		this.add(panel1, BorderLayout.CENTER);
		login.addActionListener(new loginListener());
		login.setPreferredSize(new Dimension(20,40));
		this.add(login,BorderLayout.SOUTH);
	}
	public static void invoke(SQL sqlo){
		JFrame login = new Login(sqlo);
		login.setTitle("User log in");
		login.setLocationRelativeTo(null);
		login.setSize(400,150);
		login.setVisible(true);
		login.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class loginListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String uid = userid.getText();
			String pnum = phonenumber.getText();
			sqlcode="";// Check the pnum based on the uid
			rs=loginsql.QueryExchte(sqlcode);
			String pnumindb = rs.toString();
			
			if(uid.isEmpty() & pnum.isEmpty())
				JOptionPane.showMessageDialog(null,"User id and phone number cannot leave blank ","Error", JOptionPane.ERROR_MESSAGE);
			else if(uid.isEmpty())
				JOptionPane.showMessageDialog(null, "You must input your user id","Error", JOptionPane.ERROR_MESSAGE);
			else if(pnum.isEmpty())
				JOptionPane.showMessageDialog(null, "You must input your phone number","Error", JOptionPane.ERROR_MESSAGE);
			else if(pnum.equals(pnumindb)) // The information is correct
			{
				JOptionPane.showMessageDialog(null, "You have logged in successfully", "Log in successfully",JOptionPane.INFORMATION_MESSAGE);
				//  Pass the sql object with current user id
				
			}
			else
				JOptionPane.showMessageDialog(null, "The user id or Phone number is not correct", "Log in failed",JOptionPane.ERROR_MESSAGE);
		}
	}
}
