package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.*;

import comp421.SaveToCartFrame.goodsTableModule;


public class SetUpOrderFrame extends JPanel{
	
	int userid;
	SQL sql;
	Object[][] data = null;
	
	JTable table = null;
	JButton submitButton = new JButton("Submit");
	JButton BackButton = new JButton("Back");
	
	java.sql.ResultSet rs = null;
	
	public SetUpOrderFrame(int id,SQL sql) throws SQLException
	{
	 userid = id;
	 this.sql = sql;
     String sqlCode = "";
     rs = sql.QueryExchte(sqlCode);
     table = new JTable(new OrderModule());
	
     JScrollPane scrollPane = new JScrollPane(table);
     initColumnSizes(table);
	 add(scrollPane);
	}
	
	private void initColumnSizes(JTable table) {
	     OrderModule model = (OrderModule)table.getModel();
	     TableColumn column = null;
	     Component comp = null;
	     int headerWidth = 0;
	     int cellWidth = 0;
	     
	     Object[] longValue = model.longValue;
	     
	     TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
	     for(int i = 0; i<4; i++)
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
		
	
	public class OrderModule extends AbstractTableModel
	{
		String[] columnName = {"Product¡¡Name","Added Time","Quantity","Selcted"};
		Object [][] data = null;
		Object[] longValue = {"DELL Ultra HD 4k Monitor P2715Q 27-Inch Screen LED-Lit Monitor","2017-02-21","100",new Boolean(true)};
		
		public OrderModule() throws SQLException
		{
		super();
		data = initData();
		}

		@Override
		public int getColumnCount() {
		  return columnName.length;	
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		 public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
	
	private Object[][] initData() throws SQLException
	{
		int count = 0;
		Object[][] data = null;
		if(rs != null)
		{
		  data = new Object[rs.getFetchSize()][4];
		while(rs.next())
		{
		    data[count][0] = rs.getString(1);//product name
		    data[count][1] = rs.getString(2);	//last added date 
		    data[count][2] = rs.getInt(3);	 // quantity
		    data[count][3] = new Boolean(true);
		    count++;
		}
		}	
		return data;
	}
	
	} 
	
	
    	
	
	
	public  void createUI(int id, SQL sqlo) throws SQLException
	{
		
		JPanel titlePanel = new JPanel(new GridLayout(1,0));
		titlePanel.add(new JLabel("Cart list"));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2,25,25));
	    Listener listener = new Listener();
		submitButton.addActionListener(listener);
		BackButton.addActionListener(listener);
	    buttonPanel.add(submitButton);
	    buttonPanel.add(BackButton);
		
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        SetUpOrderFrame tablePanel = new SetUpOrderFrame(id,sqlo);
        
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(tablePanel,BorderLayout.CENTER);
        frame.add(buttonPanel,BorderLayout.SOUTH);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	
	private class Listener implements ActionListener
	{
		
	

	@Override
	public void actionPerformed(ActionEvent event){
		
		// to be implemented
		// pass the order id 
	}
  
	}
}
