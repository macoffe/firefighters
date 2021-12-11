import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.*;

class Pan2 extends JPanel{

  private Color c = new Color(41,31,71);                                        //couleur de fond du JPanel.
  private Color c1 = new Color(20,15,36);                                       //couleur des bords du JPanel.
  public Jauge j = new Jauge(75,35);                                            //jauge du JPanel.
  public int ecart = 126;                                                       //ecart entre le haut de la jauge et la partie remplie.

  Pan2(){
    super();
    this.setBorder(BorderFactory.createLineBorder(c1,20));
    this.setPreferredSize(new Dimension(900,200));

  }

 	public void paintComponent(Graphics g){
		g.setColor(c);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
    g.setColor(j.c1);
    g.fillRect(j.x,j.y,j.lar,j.h1);
    g.setColor(j.c2);
    g.fillRect(j.x,(j.y+ecart),j.lar,j.h2);
  }

}
