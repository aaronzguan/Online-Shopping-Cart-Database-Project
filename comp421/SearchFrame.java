package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;


//sql operation in button submit to be implemented  --done


public class SearchFrame extends JFrame{
	
	JButton submitButton = new JButton("Submit");
	JButton backButton = new JButton("Back");
	
	JTextField nameTF = new JTextField();
	JTextField typeTF = new JTextField();
	JTextField ModuleNumTF = new JTextField();
	JTextField BrandTF = new JTextField();
	SearchFrame frame = this;
	int userid; 
	SQL sql;
	MainFrame mainFrame;
	
	public SearchFrame(int id,SQL sqlo,MainFrame mainFrame)
	{
		userid = id;
		sql = sqlo;
		this.mainFrame = mainFrame;
		
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
	    SearchListener listener = new SearchListener();
	    submitButton.addActionListener(listener);
	    backButton.addActionListener(listener);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
	    this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	
	
	   private void setUpNewUI(int id, SQL sql,MainFrame mainFrame)
	    {
	        
	        SearchFrame frame = new SearchFrame(id,sql,mainFrame);
	        
	        frame.setTitle("C2C Online Electronic Shop");
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setSize(500, 400);
	       frame.setVisible(true);
	       // frame.pack();
	    }
	   
	   static public void invoke(int id, SQL sql, MainFrame mainFrame)
	   {
		   SearchFrame frame = new SearchFrame(id,sql,mainFrame);
		   frame.setUpNewUI(id,sql,mainFrame);
	   }
	    
	   
	   public class SearchListener implements ActionListener{
		    

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				// System.out.println("Back Button clicked ");
				if(event.getSource() ==  submitButton)
			    	 {
			    	 // to be implemented 
					String name = nameTF.getText().trim();
				    String type = typeTF.getText().trim();
				    String modelNum = ModuleNumTF.getText().trim();
				    String brand = BrandTF.getText().trim();
				    String sqlCode = ""; 
				    
				    /* select *
				    from product
				    where name = name and type = type and modelNumber = modelNumber and brand = brand;
					*/
				    
				    sqlCode += "select * from product ";
					int notBlank = 0;
					if((name.length())!=0 || ( type.length() != 0) ||(modelNum.length()!= 0)|| (brand.length()!= 0))		  
					   sqlCode += "where ";
				     
					if(name.length()!=0)
					{
						notBlank = 1;
						sqlCode += "name = \'"+name+"\'";
					}
					if(type.length() != 0)
					{
						if(notBlank == 1)
							sqlCode += " And ";
						else 
							notBlank = 1;
						sqlCode += "type = "+"\'"+type+"\'";
					}
					if(modelNum.length()!= 0)
					{
						if(notBlank == 1)
							sqlCode += " And ";
						else 
							notBlank = 1;
						sqlCode += "modelNumber ="+"\'"+ modelNum+"\'";
					}
					if(brand.length()!= 0)
					{
						if(notBlank ==1)
							sqlCode += " And ";
						sqlCode += "brand = "+"\'"+brand+"\'";
					}
					sqlCode+= ";";   
					  System.out.println(sqlCode);
			    	  java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
			    	  
			    	  
			    	  int rowCount = 0;
			          try {
			              rs.last();
			              rowCount = rs.getRow();
			              rs.first();
			          } catch (Exception e) {
			              // TODO: handle exception
			              e.printStackTrace();
			          }
			          if(rowCount == 0)			   
			          	JOptionPane.showMessageDialog(null, "No Result is found", "NO Result",JOptionPane.OK_OPTION);			      	
			          else 
			    	  try {
						SaveToCartFrame.invoke(userid, sql, rs, frame);
						frame.setVisible(false);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	  //open SaveToCartFrame with rs, sql, and userid 
			    	 }
			     else if (event.getSource() == backButton)
			     {
			    	 //System.out.println("Back Button clicked ");
			    	 mainFrame.setVisible(true);
			    	 frame.dispose();
			    	 //close and invoke original one 
			    	 //to be implemented 
			     
			     }
			}

		}
	   
	   
	}