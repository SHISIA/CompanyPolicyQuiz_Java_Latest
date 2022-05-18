package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
/*
public class quizTable extends AbstractTableModel {

    ArrayList<Object[]> al;

        // the headers
            //String[] header;

        // to hold the column index for the Sent column
        int col;

        // constructor
        //quizTable(ArrayList<Object[]> obj, String[] header)
        quizTable(ArrayList<Object[]> obj)
        {
            // save the header
                //this.header = header;

            // and the data
            al = obj;
            // get the column index for the Sent column
            col = this.findColumn("Sent");
        }

        // method that needs to be overload. The row count is the size of the ArrayList

        public int getRowCount()
        {
            return al.size();
        }


    @Override
    public int getColumnCount() {
        return 0;
    }

    // method that needs to be overload. The column count is the size of our header
        //public int getColumnCount()
        //{
        //    return header.length;
        //}

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name
        //public String getColumnName(int index)
        //{
        //    return header[index];
        //}

        public Class getColumnClass(int columnIndex)
        {
            if (columnIndex == col)
            {
                return Boolean.class; // For every cell in column 7, set its class to Boolean.class
            }
            return super.getColumnClass(columnIndex); // Otherwise, set it to the default class
        }

        // a method to add a new line to the table
        void add(String word1, String word2, boolean sent)
        {
            // make it an array[3] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            Object[] item = new Object[3];
            item[0] = word1;
            item[1] = word2;
            item[2] = sent;
            al.add(item);
            // inform the GUI that I have change
            fireTableDataChanged();
        }


    public void WordAssociationTable(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all other components
        //JPanel topPanel = new JPanel();
        //topPanel.setLayout(new BorderLayout());
        //add(topPanel);

        // Create column names
        //String columnNames[] =
               // { "Word 1", "Word 2", "Sent"};

        // Create some data
        ArrayList<Object[]> dataValues = new ArrayList();
        dataValues.add(new Object[] {"Yes","No",true});
        dataValues.add(new Object[] {"Hi","there",true});
        dataValues.add(new Object[] {"True","False",true});
        dataValues.add(new Object[] {"Cat","Dog",false});

        // constructor of JTable model
        //wordModel = new quizTable(dataValues, columnNames);


        // Create a new table instance
        //table = new JTable(wordModel);

        // Configure some of JTable's paramters
        //table.isForegroundSet();
        //table.setShowHorizontalLines(false);
        //table.setRowSelectionAllowed(true);
        //table.setColumnSelectionAllowed(true);
        //add(table);


        // Change the text and background colours
        //table.setSelectionForeground(Color.white);
        //table.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        //JScrollPane scrollPane = table.createScrollPaneForTable(table);
        //topPanel.add(scrollPane, BorderLayout.CENTER);
        //topPanel.setPreferredSize(new Dimension(172, 115));
        //myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 280, SpringLayout.WEST, this);
        //myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 40, SpringLayout.NORTH, this);
*/
//    }

//}
