package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
public class SearchFrame extends JFrame{
	
	JButton submitButton = new JButton("Submit");
	JButton backButton = new JButton("Back");
	
	JTextField nameTF = new JTextField();
	JTextField typeTF = new JTextField();
	JTextField ModuleNumTF = new JTextField();
	JTextField BrandTF = new JTextField();
	
	int userid; 
	SQL sql;
	
	public SearchFrame(int id,SQL sqlo)
	{
		userid = id;
		sql = sqlo;
		
		Panel titlePanel = new Panel();
		Panel searchContentPanel = new Panel();
		Panel buttonPanel = new Panel();
		   // title panel 
		titlePanel.add(new JLabel("Search for the product you want!"));
		titlePanel.setSize(400,70);
		this.add(titlePanel,BorderLayout.NORTH);
		   // search content panel 
		searchContentPanel.setLayout(new GridLayout(4,2,25,25));
		searchContentPanel.setSize(400,200);
		searchContentPanel.add(new JLabel("Name :"));
		searchContentPanel.add(nameTF);
		searchContentPanel.add(new JLabel("Type :"));
		searchContentPanel.add(typeTF);
		searchContentPanel.add(new JLabel("Module Number :"));
		searchContentPanel.add(ModuleNumTF);
		searchContentPanel.add(new JLabel("Brand :"));
		searchContentPanel.add(BrandTF);
		this.add(searchContentPanel, BorderLayout.CENTER);
		   // button panel 
	    buttonPanel.setLayout(new GridLayout(1,2,25,25));	
	    SearchListener listener = new SearchListener(id,sqlo);
	    submitButton.addActionListener(listener);
	    submitButton.addActionListener(listener);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
	    this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	
	
	   static public void invoke(int id, SQL sql)
	    {
	        
	        SearchFrame frame = new SearchFrame(id,sql);
	        
	        frame.setTitle("C2C Online Electronic Shop");
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(500, 400);
	       frame.setVisible(true);
	       // frame.pack();
	    }
	    
	   
	   public class SearchListener implements ActionListener{
		    
			int userid;
			SQL sql = null;
			public SearchListener(int id,SQL sqlo)
			{
				userid = id;
				sql = sqlo;
				
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
			     if(event.getSource() ==  submitButton)
			    	 {
			    	 // to be implemented 
			    	 String sqlCode = "";
			    	  java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
			    	  //open SaveToCartFrame with rs, sql, and userid 
			    	 }
			     else
			     {
			    	 //close and invoke original one 
			    	 //to be implemented 
			     
			     }
			}

		}
	   
	   
	}
	    
	
	


