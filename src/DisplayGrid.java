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
  private int targetTicks;
  private int ticks = 0;
  private int infected = 0;
  private long startTime;
  
  DisplayGrid(Neighborhood[][] map, int l, int targetTicks) {
    this.map = map;
    this.targetTicks = targetTicks;
    this.startTime = System.currentTimeMillis();

    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / l;  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+l+" by "+l + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh(int infected) {
    this.ticks = ticks + 1;
    this.infected = infected;
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
      g.setFont(new Font("serif", Font.PLAIN, 56));
      g.drawString("Target ticks: " + targetTicks, 1100, 200);
      g.drawString("Ticks: " + ticks, 1100, 300);
      g.drawString("Infected Neighborhoods: " + infected, 1100, 500);
      g.drawString("Time Elapsed: " + (System.currentTimeMillis() - startTime)/1000.0, 1100, 600);

      double pct = Math.round(ticks*10000.0/targetTicks)/100.0;
      g.drawString("% Of Target: ", 1100, 400);
      g.setColor(new Color((int) (2.55 * Math.abs(100 - pct)), (int) (200 - 2*Math.abs(100 - pct)),20));
      g.drawString(pct + "%", 1415, 400);
    }
  }//end of GridAreaPanel
  
} //end of DisplayGrid

