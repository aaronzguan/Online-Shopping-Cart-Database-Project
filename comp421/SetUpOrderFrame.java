package comp421;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.*;

import comp421.SaveToCartFrame.goodsTableModule;
// sql code to be implemented in submitButton --done
//case that there is nothing in the cart 

public class SetUpOrderFrame extends JPanel{
	
	int userid;
	SQL sql;
	Object[][] data = null;
	int[] pid = null;
	int[] price = null;
	JTable table = null;
	JButton submitButton = new JButton("Submit");
	JButton backButton = new JButton("Back");
	MainFrame mainFrame;
	java.sql.ResultSet rs = null;
	JFrame frame = null;
	public SetUpOrderFrame(int id,SQL sql,MainFrame mainFrame,java.sql.ResultSet rs) throws SQLException
	{
	 userid = id;
	 this.sql = sql;
	 //to be implemented , select from Save_To_Cart 
	 /*
	  *select P.name,O.addTime,O.quantity,P.id
      * from  OrderItem O,Product P 
       where O.pid = P.pid and userid = userid  	
	  */
	 this.rs= rs; 
     table = new JTable(new OrderModule());
	  this.mainFrame = mainFrame;
	  
	  TableColumnModel tcm = table.getColumnModel();
      tcm.getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));
 
      table.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent e){
              if(e.getClickCount() == 1){
                  int columnIndex = table.columnAtPoint(e.getPoint()); //获取点击的列
                  int rowIndex = table.rowAtPoint(e.getPoint()); //获取点击的行
                  
                  if(columnIndex == 3) {//第0列时，执行代码
                      if(table.getValueAt(rowIndex,columnIndex) == null){ //如果未初始化，则设置为false
                            table.setValueAt(false, rowIndex, columnIndex);
                        }
                     
                      if(((Boolean)table.getValueAt(rowIndex,columnIndex)).booleanValue()){ //原来选中
                             table.setValueAt(false, rowIndex, 3); //点击后，取消选中
                        }
                      else {//原来未选中
                            table.setValueAt(true, rowIndex, 3);
                        }
                   }

              }
          }
      });
      
	  
	  
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
	    	/* comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(
                        table, longValue[i],
                        false, false, 0, i);
                        */
            cellWidth = comp.getPreferredSize().width;
            
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	    	 
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
	 
	public class OrderModule extends AbstractTableModel
	{
		String[] columnName = {"Product　Name","Added Time","Quantity","Selcted"};
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
		public boolean isCellEditable(int row, int column)  
        {  
            // 带有按钮列的功能这里必须要返回true不然按钮点击时不会触发编辑效果，也就不会触发事件。   
            if (column == 3)  
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

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		public void setValueAt(Object o, int rowIndex, int columnIndex)
		{
			data[rowIndex][columnIndex] = o;
			table.repaint();
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
		  data = new Object[getSize(rs)][4];
		  pid = new int[getSize(rs)];
		  price = new int[getSize(rs)];
		  if(data.length!=0)
		 do 
		{
		    data[count][0] = rs.getString(1);//product name
		    data[count][1] = rs.getString(2);	//last added date 
		    data[count][2] = rs.getInt(3);	 // quantity
		    data[count][3] = new Boolean(true);
		    pid[count] = rs.getInt(4);
		    price[count] = rs.getInt(5);
		    count++;
		}while(rs.next());
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
		backButton.addActionListener(listener);
	    buttonPanel.add(submitButton);
	    buttonPanel.add(backButton);
		
		JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
        //SetUpOrderFrame tablePanel = new SetUpOrderFrame(id,sqlo,mainFrame);
        this.frame = frame;
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(this,BorderLayout.CENTER);
        frame.add(buttonPanel,BorderLayout.SOUTH);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	
	static public void invoke(int id, SQL sql,java.sql.ResultSet rs,MainFrame mainFrame) throws SQLException
	{
		SetUpOrderFrame frame = new SetUpOrderFrame(id,sql,mainFrame,rs);
		frame.createUI(id, sql);
	}
		
	private class Listener implements ActionListener
	{
		
	@Override
	public void actionPerformed(ActionEvent event){
		
		if (event.getSource() == submitButton)
		{
			// first, insert a new order without total amount 
			//get current order number 
			String sqlCode = "select max(orderNumber) from Orders;";			
			java.sql.ResultSet result = sql.QueryExchte(sqlCode);
		    int orderId = 0;
		    try {
				result.next();
			    orderId = result.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    orderId++;
		    /*insert into Orders(orderNumber, creationTime)
             *values(new_orderNumber, creationTime);
             */
		    sqlCode = "insert into Orders(orderNumber, creationTime) values(";
		    sqlCode+= orderId+", ";
		    Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    sqlCode += "\'"+sdf.format(d)+"\'"+");";
		    sql.WriteExcute(sqlCode);
			
			// insert order Items
		    // get order Item itemId
			sqlCode = "select max(itemid) from OrderItem;";
			result = sql.QueryExchte(sqlCode);
			int totalNum = table.getRowCount();
			int itemId = 0;
			try {
					result.next();
				    itemId = result.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			itemId++;// first id to be added 
			
			int count = 0;
			int totalAmount = 0;
			for(int i =0; i<totalNum;i++)
			{
				if((boolean)table.getValueAt(i,3))
				{
					/*insert into OrderItem
                     *values(new_itemid, pid, price, creationtime);
                     */
					sqlCode = "insert into OrderItem values(";
					sqlCode += (itemId+count) + ", ";
					sqlCode += pid[i]+", ";
					sqlCode += price[i]+", ";
                     sqlCode += "\'"+sdf.format(d)+"\'"+ ");";
                  //   System.out.print(sqlCode);
                     sql.WriteExcute(sqlCode);
                     totalAmount+= price[i];
                     
                     //build contain with such orderitem 
                     
                    /* insert into Contain
                     values(new_orderNumber, itemid, quantity);
                    */
                     sqlCode = "insert into Contain values (";
                     sqlCode += orderId +", ";
                     sqlCode += (itemId + count)+", ";
                     sqlCode += table.getValueAt(i,2)+");";
                    // System.out.print(sqlCode);
                     sql.WriteExcute(sqlCode);
                     count++;
				}	     
			}
			// add orderitem and contain end
			 // add total amount 
			/*insert into Orders(totalAmount)
			 *values(totalAmount);
			 */
			sqlCode = "update Orders set totalAmount ="+totalAmount+", "+"paymentstate =\'Paid\'"+"where orderNumber ="+orderId+";";			
		    sql.WriteExcute(sqlCode);
		    
		    //add payment
		    //get credit card number 
		    String cardNumber = "";
		    sqlCode ="select cardNumber from CreditCard where userid = "+userid +";";
		    result = sql.QueryExchte(sqlCode);
		    try {
				result.next();
				cardNumber = result.getString(1);
				result = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //insert payment 
		    /*insert into Payment
             * values(orderNumber, creditcardNumber, payTime);*/
		    sqlCode = "insert into Payment values(";
		    sqlCode += orderId+", ";
		    sqlCode +="\'"+ cardNumber +"\'"+", ";
		    sqlCode += "\'"+sdf.format(d)+"\'"+");";
		    sql.WriteExcute(sqlCode);
		    //all sql done 
		    
		    try {
				addressFrame.invoke(userid, sql,orderId, mainFrame);
		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    frame.dispose();    
		    
		}
		else if(event.getSource() == backButton)
		{
			 mainFrame.setVisible(true);
	    	 frame.dispose();
		}
	}
  
	}
}