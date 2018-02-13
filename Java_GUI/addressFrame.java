package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;

public class addressFrame extends JPanel 
{
	int userid;
	int orderNumber;
	int addrid[] = null;
	SQL sql;
	Object[][] data = null;
	
	JTable table = null;
	JButton ConfirmButton = new JButton("Confirm");
	MainFrame mainFrame = null;
	JFrame frame = null;
	java.sql.ResultSet rs = null;
	
	public addressFrame(int id, SQL sql,int orderNumber,MainFrame mainFrame) throws SQLException
	{
		userid = id;
		this.orderNumber = orderNumber;
		this.sql = sql;
		this.mainFrame= mainFrame;

	
		String sqlCode = "select addrid, name, province, city, streetaddr, postCode from address where userid = " + userid;
		rs = sql.QueryExchte(sqlCode);
        table = new JTable(new AddressModule());
        
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 1){
                    int columnIndex = table.columnAtPoint(e.getPoint()); //获取点击的列
                    int rowIndex = table.rowAtPoint(e.getPoint()); //获取点击的行
                   
                    if(columnIndex == 5) {//第0列时，执行代码
                        if(table.getValueAt(rowIndex,columnIndex) == null){ //如果未初始化，则设置为false
                              table.setValueAt(false, rowIndex, columnIndex);
                          }
                       
                        if(((Boolean)table.getValueAt(rowIndex,columnIndex)).booleanValue()){ //原来选中
                               table.setValueAt(false, rowIndex ,5); //点击后，取消选中
                          }
                        else {//原来未选中
                              table.setValueAt(true, rowIndex, 5);
                          }
                     }

                }
            }
        });
        
        initColumnSizes(table);
        JScrollPane scrollPane= new JScrollPane(table);
        
        add(scrollPane);
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

	
	private void initColumnSizes(JTable table)
	{/*
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
	    	 /*comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(
                       table, longValue[i],
                       false, false, 0, i);
	    	 
        cellWidth = comp.getPreferredSize().width;
       
        column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	}*/
	}
	
	 public class AddressModule extends AbstractTableModel
	{
	   String[] columnName = {"Name","Province","City","Street Address","Post Code", "select"};
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
		
		public boolean isCellEditable(int row, int column)  
        {  
            // 带有按钮列的功能这里必须要返回true不然按钮点击时不会触发编辑效果，也就不会触发事件。   
            if (column == 5)  
            {  
                return true;  
            }  
            else  
            {  
                return false;  
            }  
        } 
		
		@Override
		 public String getColumnName(int col) {
           return columnName[col];
       }

		public void setValueAt(Object o, int rowIndex, int columnIndex)
		{
			data[rowIndex][columnIndex] = o;
			table.repaint();
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
				 data = new Object[getSize(rs)][6];
				 addrid = new int[getSize(rs)];
				if(data.length!=0 && rs.first())
					do
				 {
						addrid[count] = rs.getInt(1);
					    data[count][0] = rs.getString(2);      //name
					    data[count][1] = rs.getString(3);	//province
					    data[count][2] = rs.getString(4);   //city
					    data[count][3] = rs.getString(5);	//street address 
					    data[count][4] = rs.getString(6);   //postCode
					    data[count][5] = new Boolean(false);//checkbox
					    
					    count++;
				 }while(rs.next());
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
		buttonPanel.add(ConfirmButton);
		
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame = frame;
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
         int flag = 0;
         int selected = -1;
    	 // add jusdgement if there is two selected; 
    	 for (int i =0;i <getSize(rs);i++)
    		 if((boolean) table.getValueAt(i, 5))
    		 {
    			 flag ++;
    			 selected = i;
    		 }
    	 if(flag != 1)
    		 JOptionPane.showMessageDialog(null, "You must select one and only one address to confirm the delivery", "Error",JOptionPane.OK_OPTION);
    	 else
    	 {
    		 Date d = new Date();  
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             String sqlCode = "insert into Deliver_To values("+addrid[selected]+","+orderNumber+", \'"+sdf.format(d)+"\');";
        	 sql.WriteExcute(sqlCode);
        	 JOptionPane.showMessageDialog(null, "Your ordered is confirmed. Thank you for your purchase!", "Success", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(addressFrame.class.getResource("success.png")));
        	 mainFrame.setVisible(true);
        	 frame.dispose();
        	 
    	 }
    	 }
     
  }
}
}