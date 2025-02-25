import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class first extends Frame implements ActionListener
{
    Button b1,b2;
    Panel mainpanel;
    CardLayout c;
    first()
    {
        setSize(500,500);
        setTitle("First page");
        setVisible(true);
        c=new CardLayout();
        mainpanel = new Panel();
        mainpanel.setLayout(c);
        Panel p1=new Panel();
        GridBagLayout cb=new GridBagLayout();
        p1.setLayout(cb);
        Color c=new Color(200,250,250);
        p1.setBackground(c);
        GridBagConstraints cbg=new GridBagConstraints();
        cbg.gridx=0;
        cbg.gridy=0;
        cbg.ipadx=20;
        cbg.ipady=20;
        
        b1=new Button("Sudoku Game");
        b1.setBackground(Color.green);
        p1.add(b1,cbg);
        cbg.gridx=1;
        cbg.gridy=0;
        cbg.ipadx=20;
        cbg.ipady=20;
       
        b2=new Button("Sudoku Solver");
        b2.setBackground(Color.yellow);
        p1.add(b2,cbg);
        add(p1);
        b1.addActionListener(this);
        b2.addActionListener(this);
        mainpanel.add(p1,"ButtonPanel");
        add(mainpanel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
            
        });
        setVisible(true);
        



    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b1)
        {
            SudokuGame sg= new SudokuGame();
            mainpanel.add(sg,"SudokuGame");
            c.show(mainpanel,"sudokuGame");

        }

        else  if(e.getSource()==b2)
        {
            demo d=new demo();
            mainpanel.add(d,"demo");
            c.show(mainpanel,"demo");
            
        }


    }

    public static void main(String args[])
    {
        first f=new first();
    }
}
 