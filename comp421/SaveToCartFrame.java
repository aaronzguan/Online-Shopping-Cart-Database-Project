package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.*;



//need to add button back -done 
//sql code to be implemented in button editor -done
// case no result is empty, will not have table, but implement an panel with notice 
public class SaveToCartFrame extends JPanel {
	
	int userid; 
	SQL sql;
	java.sql.ResultSet rs = null;
	JTable table = null;
	JButton backButton = new JButton("Back");
	SearchFrame searchFrame = null;
	
     public SaveToCartFrame(int id,SQL sqlo,java.sql.ResultSet rs,SearchFrame searchFrame) throws SQLException{
    	 super(new GridLayout(1,0));
    	
    	 userid = id;
    	 sql = sqlo;
    	 this.rs = rs;
    	 this.searchFrame = searchFrame;
    	
    	 table = new JTable(new goodsTableModule());
    	 table.setPreferredSize(new Dimension(500,70));
    	 table.setFillsViewportHeight(true);
    	 
    	 JScrollPane scrollPane = new JScrollPane(table); 
    	 initColumnSizes(table);
    	 setUpAddCartColumn(table,table.getColumnModel().getColumn(8));
    	 add(scrollPane);
    	 
  
    	// to be editted  
    	 
     }
     
     
     private void setUpAddCartColumn(JTable table, TableColumn column) throws SQLException {
	     
         column.setCellEditor(new MyButtonEditor());
		
	}
     
     
     public class MyButtonEditor extends DefaultCellEditor
     {
    	 private JButton button;
         private JPanel panel;
         
		public MyButtonEditor() 
		{
			super(new JTextField());
			 this.setClickCountToStart(1);
			 
	            this.initButton();
	 
	            this.initPanel();
	            
	            this.panel.add(this.button);
	       
		}
		  private void initButton()
          {
              this.button = new JButton("Add");
   
              // set size and positon 
              this.button.setBounds(0, 0, 50, 15);
   
              
              this.button.addActionListener(new ActionListener()
              {   
              	
              	
                  public void actionPerformed(ActionEvent e)
                  {
                   
                      int row = table.getSelectedRow();
                     
                      /*insert into Save_To_Shopping_Cart
                      values(userid, pid, addTime, quantity);*/
                        
                      String code = "select quantity from Save_To_Shopping_Cart Where userid = "+userid+" and pid = "+table.getValueAt(row, 0)+";";
                      java.sql.ResultSet result = sql.QueryExchte(code);
                    
                      String sqlCode = "";
                      sqlCode += "insert into Save_To_Shopping_Cart values(";
                      sqlCode += userid + ", ";
                      sqlCode += table.getValueAt(row, 0)+", ";// pid 
                      Date d = new Date();  
                      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                      sqlCode +="\'"+ sdf.format(d)+"\'"+ ", ";
                      if(getSize(result)==0)
                    	  sqlCode += "1);";
					else
						try {
							sqlCode += (result.getInt(1)+1)+");";
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                      sql.WriteExcute(sqlCode);
     
                  }
              });
   
          }
		  
		  private void initPanel()
	        {
	            this.panel = new JPanel();
	 
	            
	            this.panel.setLayout(null);
	        }
	        @Override
	        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	        {
               return this.panel;
	        }
	 

		

		
     }

    private int getSize(java.sql.ResultSet resultSet)
    {
    	int rowCount = 0;
        try {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.first();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return rowCount;
    }

	private void initColumnSizes(JTable table) {
	     goodsTableModule model = (goodsTableModule)table.getModel();
	     TableColumn column = null;
	     Component comp = null;
	     int headerWidth = 0;
	     int cellWidth = 0;
	     
	     Object[] longValue = model.longValue;
	     
	     TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
	     for(int i = 0; i<10; i++)
	     {
	    	 column = table.getColumnModel().getColumn(i);
	    	 
	    	 comp = headerRenderer.getTableCellRendererComponent(table, longValue[i], false, false, 0, 0);
	    	 headerWidth = comp.getPreferredSize().width;
	    	 comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(
                         table, longValue[i],
                         false, false, 0, i);
             cellWidth = comp.getPreferredSize().width;
             
             column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	    	 
	     }
	}


	class goodsTableModule extends AbstractTableModel{

		
		private String[] columnName={"product ID","Shop ID","brand","Product Name","Type","Model Number","color"
         ,"amount","Price","Add to Cart"};
    	 private Object[][] data;
    	 public final Object[] longValue = {new Integer(10),new Integer(100),"Microsoft","'DELL Ultra HD 4k Monitor P2715Q 27-Inch Screen LED-Lit Monitor",
    			           "computer accessories","759944-0010","black","100","1000","100000"};
    	 
    	 
    	 public goodsTableModule() throws SQLException
    	 {
    	 super();
    	 data = initData();
    	 }
    	 
    	 
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return  columnName.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
        
		@Override
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			return data[row][col];
		}
		
		public Object[][] initData() throws SQLException
		{
			int count = 0;
			Object[][] data  = null;
			if(rs != null)
			{
			   data = new Object[getSize(rs)][9];
			while(rs.next())
			{
			    data[count][0] = rs.getInt(1);
			    data[count][1] = rs.getInt(2);
			    data[count][2] = rs.getString(3);	
			    data[count][3] = rs.getString(4);	
			    data[count][4] = rs.getString(5);
			    data[count][5] = rs.getString(6);
			    data[count][6] = rs.getString(7);
			    data[count][7] = rs.getInt(8);
			    data[count][8] = rs.getDouble(9);
			    count++;
			}
			}
			return data;
		}
		
		 public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
		 	
		
   	 
     }

	private void createUI(int id, SQL sqlo,java.sql.ResultSet rs) throws SQLException
	{
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        /*//Create and set up the content pane.
        SaveToCartFrame newContentPane = new SaveToCartFrame(id, sqlo,rs);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 */
        JPanel titlePanel = new JPanel(new GridLayout(1,0));
        titlePanel.add(new JLabel("Slelect products to build order"));
        JPanel buttonPanel = new JPanel(new GridLayout(1,0));
        
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				searchFrame.setVisible(true);
				frame.dispose();
				//other implements on close 
			}
        });
        backButton.setBounds(0, 0, 50, 20);
        buttonPanel.add(backButton);
        
        frame.add(titlePanel);
        frame.add(this);
        frame.add(buttonPanel);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	public static void invoke(int id,SQL sql, java.sql.ResultSet rs,SearchFrame searchFrame) throws SQLException
	{
		SaveToCartFrame frame = new SaveToCartFrame(id,sql,rs,searchFrame);
		frame.createUI(id, sql, rs);
	}
	
	
	
	
}
