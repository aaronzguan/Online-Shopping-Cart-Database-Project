package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
public class MainFrame extends JFrame{

	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JButton searchProductButton = new JButton ("Search Product");
	JButton buyButton = new JButton ("View Shopping Cart and Purchase");
	JButton AddAddressButton = new JButton("Add a new Address");
	JButton QuitButton = new JButton("Quit");
	
	SQL sql = null;
	int userid;
	public MainFrame() throws SQLException
	{
		// run initial the sql, build connection 
		// sql = new SQL();
		 userid = 0;
		// button panel for frame
		 JPanel mainArc  = new JPanel();
	     mainArc.setLayout(new GridLayout(6,1,25,0));
	     mainArc.setSize(200, 400);
	    // listener for main frame, need sql passed to subframe 
	     MainButtonListener listener = new MainButtonListener(sql); //need to pass the sql class and userid 
	
	    loginButton.addActionListener(listener);
	    mainArc.add(loginButton);
	    registerButton.addActionListener(listener);
		mainArc.add(registerButton);
		searchProductButton.addActionListener(listener);
		mainArc.add(searchProductButton);
		buyButton.addActionListener(listener);
		mainArc.add(buyButton);
		AddAddressButton.addActionListener(listener);
		mainArc.add(AddAddressButton);
		QuitButton.addActionListener(listener);
	    mainArc.add(QuitButton); 
	    
	    this.add(mainArc,BorderLayout.WEST);
		
	}
	
	public void setUserid(int id)
	{
		userid = id;
	}
	
	  public static void main(String[] args) throws Exception
	    {
	        
	        MainFrame frame = new MainFrame();
	        
	        frame.setTitle("C2C Online Electronic Shop");
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(500, 400);
	        
	        
	       frame.setVisible(true);
	       // frame.pack();
	    }
	 
	 
	 public class MainButtonListener implements ActionListener {


			SQL sql = null;
			int userid;
			public MainButtonListener(SQL sqlpassed)
			{
			    sql = sqlpassed;	
			    userid = 0;
			}
			
			JButton loginButton = new JButton("Login");
			JButton registerButton = new JButton("Register");
			
			JButton buyButton = new JButton ("View Shopping Cart and Purchase");
			JButton AddAddressButton = new JButton("Add a new Address");
			JButton QuitButton = new JButton("Quit");
			
			
			
			
			public void actionPerformed(ActionEvent event ) {
				
				if(event.getSource() == loginButton)
				{
				  
				}
				else if(event.getSource() == registerButton)
				{
					
				}
				else if (event.getSource() == AddAddressButton)
				{
					
				}
				else if (event.getSource() == searchProductButton)
				{
					SearchFrame.invoke(userid, sql);
					
				}
				else if(event.getSource() == buyButton)
				{
					try {
						SetUpOrderFrame.invoke(userid, sql);
					} catch (SQLException e) {
					}
				}
				else if(event.getSource() == QuitButton)
				{
					
				}
			
					
			}

		}

	    
	}

	

