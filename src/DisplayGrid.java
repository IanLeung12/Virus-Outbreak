/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;


class DisplayGrid { 

  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Neighborhood[][] map;
  private int loops = 0;
  
  DisplayGrid(Neighborhood[][] map, int l, int w) {
    System.out.println("asdas");
    this.map = map;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / l;  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+l+" by "+w + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() {
    loops ++;
    frame.repaint();
  }

  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {        
      //super.repaint();
      
      setDoubleBuffered(true);
      for(int i = 0; i<map.length;i=i+1) {
        for(int j = 0; j<map[0].length;j=j+1) {

          if (map[i][j].getType() == Const.EMPTY) {   //This block can be changed to match character-color pairs
            g.setColor(Color.LIGHT_GRAY);
          } else if (map[i][j].getType() == Const.VACCINATED) {
            g.setColor(Color.YELLOW);
          } else if (map[i][j].getType() == Const.INFECTED) {
            g.setColor(Color.RED);
          } else if (map[i][j].getType() == Const.RESISTANT){
            g.setColor(Color.green);
          }
          g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
        }
      }
      g.setColor(Color.black);
      g.setFont(new Font("serif", Font.PLAIN, 100));
      g.drawString("ticks: " + loops, 800, 300);
    }
  }//end of GridAreaPanel
  
} //end of DisplayGrid

