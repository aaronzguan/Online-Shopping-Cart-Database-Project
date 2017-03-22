package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;

public class addressFrame extends JPanel 
{
	int userid;
	int orderNumber;
	SQL sql;
	Object[][] data = null;
	
	JTable table = null;
	JButton ConfirmButton = new JButton("Confirm");
	MainFrame mainFrame = null;
	
	java.sql.ResultSet rs = null;
	
	public addressFrame(int id, SQL sql,int orderNumber,MainFrame mainFrame) throws SQLException
	{
		userid = id;
		this.orderNumber = orderNumber;
		this.sql = sql;
		this.mainFrame= mainFrame;

	
		String sqlCode = "select (addrid, name, province, city, streetaddr, postCode) from address where userid = " + userid;
		rs = sql.QueryExchte(sqlCode);
        table = new JTable(new AddressModule());
        
        JScrollPane scrollPane= new JScrollPane(table);
        initColumnSizes(table);
        add(scrollPane);
     }
	
	private void initColumnSizes(JTable table)
	{
		AddressModule model = (AddressModule)table.getModel();
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		
		Object longValue[] = model.longValue;
		
		TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
		
		for(int i = 0; i<5; i++)
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
	
	 public class AddressModule extends AbstractTableModel
	{
	   String[] columnName = {"Address", "select"};
	   Object[][] data = null;
	   Object[] longValue = {"Kitra N. Cabrera", "New Brunswick", "Campbellton", "P.O. Box 268, 4081 Suspendisse Street", "E2J 3G6", new Boolean(false)};
	   

	   
	   public AddressModule() throws SQLException
		{
		super();
		data = (Object[][]) initData();
		}

		@Override
		public int getColumnCount() {
		  return columnName.length;	
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		 public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
		 
		 private Object[] initData() throws SQLException
		 {
			 int count = 0;
			 Object[][] data = null;
			 if(rs != null)
			 {
				 data = new Object[rs.getFetchSize()][4];
				 while(rs.next())
				 {
					 data[count][0] = rs.getString(2);      //name
					    data[count][1] = rs.getString(3);	//province
					    data[count][2] = rs.getString(4);   //city
					    data[count][3] = rs.getString(5);	//street address 
					    data[count][4] = rs.getString(6);   //postCode
					    data[count][5] = new Boolean(false);//checkbox
					    count++;
				 }
			 }
			 return data;
		 }
		 }
	
	
	public  void createUI(int id, SQL sqlo) throws SQLException
	{
		
		JPanel titlePanel = new JPanel(new GridLayout(1,0));
		titlePanel.add(new JLabel("Address list"));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2,25,25));
	    Listener listener = new Listener();
		ConfirmButton.addActionListener(listener);
		
		
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(this,BorderLayout.CENTER);
        frame.add(buttonPanel,BorderLayout.SOUTH);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
        
	static public void invoke(int id, SQL sql,int orderNumber,MainFrame mainFrame) throws SQLException
	{
		addressFrame frame = new addressFrame(id,sql,orderNumber,mainFrame);
		frame.createUI(id, sql);
	}

	







	private class Listener implements ActionListener
{
     @Override
      public void actionPerformed(ActionEvent event) 
  {
	
     if(event.getSource() ==  ConfirmButton)
    	 {
    	 
    	 //insert into Deliver_To
    	 //values(addrid, orderNumber, TimeDelivered);
    	 String sqlCode ="";
    	 sqlCode += "insert into Deliver_To values(";
    	 sqlCode += rs.getInt(1);
    	 sqlCode += frame.orderNumber;
    	 Date d = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         sqlCode += sdf.format(d)+ ");";
    	 sql.WriteExcute(sqlCode);
    	 }
     
  }
}
}
