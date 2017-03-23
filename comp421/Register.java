package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
// need to be modified 
// card valid data should follow format strictly
// card number should be made up with only numbers and space ,but no character
// 


public class Register extends JFrame{
	JTextField name = new JTextField();
	JTextField phonenum = new JTextField();
	JTextField cardnum = new JTextField();
	JTextField expirydate = new JTextField();
	JTextField bank = new JTextField();
	JTextField organization = new JTextField();
	JButton submit = new JButton("submit");
	MainFrame mainFrame = null;
	Register frame = this;
	
	SQL adduser;
	ResultSet rs;
	int userid;
	String sqlcode;
	
	public Register(SQL sqlo,MainFrame mainFrame){
		this.mainFrame = mainFrame;
		adduser=sqlo;
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(6,2));
		jpanel.add(new JLabel("name:"));jpanel.add(name);
		jpanel.add(new JLabel("Phone Number:"));	jpanel.add(phonenum);
		jpanel.add(new JLabel("Card Number:")); 	jpanel.add(cardnum);
		jpanel.add(new JLabel("Expiry Date:")); 	jpanel.add(expirydate);
		jpanel.add(new JLabel("Bank:"));			jpanel.add(bank);
		jpanel.add(new JLabel("Card Issue Organzation")); jpanel.add(organization);
		this.add(jpanel,BorderLayout.CENTER);
		submit.addActionListener(new registerlistener());
		submit.setPreferredSize(new Dimension(20,40));
		this.add(submit, BorderLayout.SOUTH);
	}
	public static void invoke (SQL sqlo,MainFrame mainFrame){
		JFrame register = new Register(sqlo,mainFrame);
		register.setVisible(true);
		register.setSize(400, 200);
		register.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		register.setLocationRelativeTo(null);
		register.setTitle("Register a new User");
	}
	class registerlistener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sqlcode ="select max(userid) from users;";// get the max userid from user table
			rs = adduser.QueryExchte(sqlcode);
			try {
				rs.next();
				userid = rs.getInt(1)+1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String Name = name.getText();
			String Pnum = phonenum.getText();
			String Cardnum = cardnum.getText();
			String Expirydate = expirydate.getText();
			String Bank = bank.getText();
			String Org = organization.getText();
			if(Name.trim().isEmpty()||Pnum.trim().isEmpty()||Cardnum.trim().isEmpty()||Expirydate.trim().isEmpty()||Bank.trim().isEmpty()||Org.trim().isEmpty())
				JOptionPane.showMessageDialog(null, "It is required to fill in every blank","Error",JOptionPane.ERROR_MESSAGE);
			else{
				sqlcode = "insert into users values ("+userid+", \'"+Name+"\', \'"+Pnum+"\')"; // Insert a new buyer to table user and buyer.
				adduser.WriteExcute(sqlcode);
				sqlcode = "insert into bankcard values (\'"+Cardnum+"\', \'"+Expirydate+"\', \'"+Bank+"\');";
				adduser.WriteExcute(sqlcode);
				sqlcode = "insert into creditcard values (\'"+Cardnum+"\',"+userid+", \'"+Org+"\');";
				adduser.WriteExcute(sqlcode);
				sqlcode = "insert into Buyer values("+userid+");";
				adduser.WriteExcute(sqlcode);
				
				JOptionPane.showMessageDialog(null, "You have successfully registed, your unique userid is "+ userid+", please keep it for login next time","Register Successfully",JOptionPane.OK_OPTION);
				//Return back the current user id
				
					
				
				try {
					mainFrame.setUserid(userid);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.setVisible(true);
				mainFrame.setAddAddressButtonEnable(true);
				frame.dispose();
			}
		}
		
	}
}