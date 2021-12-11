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
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

class Pan1 extends JPanel implements MouseListener, MouseMotionListener, ActionListener{

	private Color c = new Color(255,204,128);																			//couleur de fond.
	private Color c1 = new Color(20,15,36);																				//couleur des bords.
	private Color c3 = new Color(115,40,40);																			//couleur du bouton de base.
	private Color c4 = new Color(250,250,0);																			//couleur du bouton quand pompier disponible.
	public Lac l = new Lac();																											//lac du JPanel.
	public Plaine p = new Plaine();																								//plaine du JPanel.
	public Feuillus[] f = new Feuillus[350];																			//tableau des arbres feuillus.
	public Resineux[] r = new Resineux[250];																			//tableau des arbres resineux.
	public Pompier[] pom = new Pompier[10];																				//tableau des pompiers.
	public Icon icone = new ImageIcon("Pompier.png");															//image du bouton.
	public JButton jb = new JButton(icone);																				//bouton du JPanel permettant l'apparition d'autres pompiers.
	public boolean available = false;																							//permet de gerer la disponibilite de pompier.
	public int nbrpom = 0;																												//nombre de pompier affiches.
	public int check = -1;																												//permet l'identification d'un pompier sur lequel on a clique.
	public Timer timer = new Timer();																							//Timer permettant d'espacer la disponibilite des pompiers.
	public int RNG = (int)(Math.random()*r.length);																//nombre aleatoire, position dans le tableau de l'arbre resineux commençant l'incendie.

	Pan1(){

		//initialise le JPanel et le definit.

		super();
		this.setBorder(BorderFactory.createLineBorder(c1,20));
		this.setPreferredSize(new Dimension(900,600));
		this.init();
		this.addMouseListener(this);
    this.addMouseMotionListener(this);
    jb.setBounds(30,440,100,100);
    jb.setBackground(c3);
		jb.addActionListener(this);
    this.setLayout(null);
    this.add(jb);

		//actualise le Timer permettant d'espacer la dispo. de pompiers.

		timer.schedule(new Cooldown(), 10000, 10000);
	}

	public void paintComponent(Graphics g){

		//peint le fond, le lac, et la plaine + gere les collisions.

		g.setColor(c);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(l.c);
		g.fillOval(l.x,l.y,l.d,l.d);
		g.setColor(p.c);
		g.fillOval(p.x,p.y,p.a1,p.a2);
		while (collision(l,p,p)){
			p.setRandPosB();
		}

		//peint les arbres + gere les collisions.

		for (int i=0;i<f.length;i++){
			g.setColor(f[i].c);
			while (collision(f[i],l,p)){
				f[i].setRandPosL();
			}
			g.fillOval(f[i].x,f[i].y,f[i].d,f[i].d);
		}

		for (int i=0;i<r.length;i++){
			g.setColor(r[i].c);
			while (collision(r[i],l,p)){
				r[i].setRandPosL();
			}
			int[] xt={r[i].x,r[i].x+15,r[i].x+30};
			int[] yt={r[i].y,r[i].y-30,r[i].y};
			g.fillPolygon(xt,yt,3);
		}

		//replace les pompiers venant d'etre appeles par le bouton dans la fenetre.

		if (pom[nbrpom].x == -30){
			pom[nbrpom].x = p.x+85;
			pom[nbrpom].y = p.y+60;
		}

		//peint les pompiers.

		for (int i=0;i<nbrpom+1;i++){
			if(pom[i].vie){
				g.setColor(pom[i].c);
				g.fillRect(pom[i].x,pom[i].y,pom[i].l,pom[i].l);
			}
		}

		//peint la limite de la zone d'arrosage des pompiers.

			g.setColor(new Color(150,150,150));
		for (int i=0;i<nbrpom+1;i++){
		 	g.drawRect((int)pom[i].firefighterbounds().getX(),(int)pom[i].firefighterbounds().getY(),(int)pom[i].firefighterbounds().getWidth(),(int)pom[i].firefighterbounds().getHeight());
		}
  }

	//initialise les elements + arbre commençant l'incendie, en le rendant presque mort.

	void init(){
		for (int i=0;i<f.length;i++){
			f[i] = new Feuillus();
		}
		for (int i=0;i<r.length;i++){
			r[i] = new Resineux();
		}
		for (int i=0;i<pom.length;i++){
			pom[i] = new Pompier(-30,-30);
		}

		r[RNG].setOnFire();
		r[RNG].durability=3;
	}

	//gere les collisions pour qu'il n'y ait pas de superposition non voulues.

	boolean collision(Element e1, Element e2, Element e3){
		Rectangle r1 = e1.bounds();
		Rectangle r2 = e2.bounds();
		Rectangle r3 = e3.bounds();
		Rectangle r4 = new Rectangle(30,440,100,100);

		if (r1.intersects(r2)){
			return true;
		}
		else if (r1.intersects(r3)){
			return true;
		}
		else if (r1.intersects(r4)){
			return true;
		}
		else{
			return false;
		}
	}

	//gere les utilisations du bouton.

	public void actionPerformed(ActionEvent e){
				if(nbrpom<9){
					if(available){
            this.nbrpom=this.nbrpom+1;
						jb.setBackground(c3);
						this.repaint();
						available = false;
					}
				}
  }

	// gere les appuis souris.

	private void pressed(MouseEvent e){
			this.check = -1;
			if(e.getButton()==MouseEvent.BUTTON1){
				for(int i=0;i<pom.length;i++){
					int dx = (e.getX()-pom[i].x)-15, dy = (e.getY()-pom[i].y)-15;
					if(Math.sqrt(dx*dx+dy*dy)<=20) this.check = i;
				}
			}
	}

	//gere le relachement de la souris.

	private void released(){
     this.check = -1;
  }

	//gere les deplacement de la souris quand elle est appuyee.

  private void dragged(MouseEvent e){
      if (this.check!= -1) {
				if (pom[this.check].vie){
        	pom[this.check].x = e.getX();
        	pom[this.check].y = e.getY();

					for (int i=0;i<pom.length;i++){
						if (pom[this.check].x>=850){
							pom[this.check].x=849;
						}
						if (pom[this.check].x<=20){
							pom[this.check].x=21;
						}
						if (pom[this.check].y>=520){
							pom[this.check].y=519;
						}
						if (pom[this.check].y<=20){
							pom[this.check].y=21;
						}
					}
				}

            this.repaint();
      }
  }

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {pressed(e);}
	public void mouseReleased(MouseEvent e) {released();}
	public void mouseDragged(MouseEvent e) {dragged(e);}

	//classe encapsulant la tache a execute par le Timer.

	class Cooldown extends TimerTask {

		public void run(){
				available=true;
				jb.setBackground(c4);
		}

	}

}
