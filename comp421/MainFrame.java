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
	JButton addAddressButton = new JButton("Add a new Address");
	JButton QuitButton = new JButton("Quit");
	MainFrame mainFrame = this;
	SQL sql = null;
	int userid;
	public MainFrame() throws SQLException
	{
		// run initial the sql, build connection 
		sql = new SQL();
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
		
		addAddressButton.addActionListener(listener);
        addAddressButton.setEnabled(false);
		mainArc.add(addAddressButton);
		searchProductButton.addActionListener(listener);
		searchProductButton.setEnabled(true);
		mainArc.add(searchProductButton);
		buyButton.addActionListener(listener);
		buyButton.setEnabled(false);
		mainArc.add(buyButton);
		
		QuitButton.addActionListener(listener);
	    mainArc.add(QuitButton); 
	    
	    
	    
	    this.add(mainArc,BorderLayout.WEST);
		
	}
	
	public void setUserid(int id)
	{
		userid = id;
	}
	
	public void setAddAddressButtonEnable(boolean b)
	{
		addAddressButton.setEnabled(b);
	}
	public void setSearchAndBuyButtonEnable(boolean b)
	{
		searchProductButton.setEnabled(b);
		buyButton.setEnabled(b);
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
			
			
			public void actionPerformed(ActionEvent event ) {
				
				if(event.getSource() == loginButton)
				{
					//mainFrame to be passed 
				  Login.invoke(sql,mainFrame);
				  mainFrame.setVisible(false);
				}
				else if(event.getSource() == registerButton)
				{
					//mainFrame to be passed 
					Register.invoke(sql,mainFrame);
					mainFrame.setVisible(false);
				}
				else if (event.getSource() == addAddressButton)
				{
					//mainFrame to be passed 
					AddAddress.invoke(userid,sql,mainFrame);
					mainFrame.setVisible(false);
				}
				else if (event.getSource() == searchProductButton)
				{
					//done 
					mainFrame.setVisible(false);
					SearchFrame.invoke(userid,sql,mainFrame);
					
					
				}
				else if(event.getSource() == buyButton)
				{
				//done 
					try {
						SetUpOrderFrame.invoke(userid, sql,mainFrame);
						mainFrame.setVisible(false);
					} catch (SQLException e) {
					}
				}
				else if(event.getSource() == QuitButton)
				{
                    //done 
					System.exit(0);
				}
			
					
			}

		}

	    
	}

	

