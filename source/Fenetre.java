import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Fenetre implements ActionListener{

  JFrame fenetre = new JFrame();
  JPanel p = new JPanel(new BorderLayout());                                    //JPanel principal contenant les deux sous JPanel.
  Pan1 p1 = new Pan1();                                                         //JPanel supérieur, affichage de la carte.
  Pan2 p2 = new Pan2();                                                         //JPanel inférieur, affichage des stats + Jauge.
  int cpt = 0;                                                                  //nombre d'arbre brulant.
  int s = 0;                                                                    //score.
  int s1 = p1.f.length+p1.r.length;                                             //nombre d'arbres en vie.
  int s2 = 0;                                                                   //nombre d'arbres morts.
  int s3 = p1.nbrpom+1;                                                         //nombre de pompiers en vie.
  int s4 = 0;                                                                   //nombre de pompiers morts.
  long start;                                                                   //reference pour le calcul de temps ecoule.
  long second;                                                                  //secondes ecoules depuis le debut de l'execution.
  JLabel jld = new JLabel("Firefighters");                                      //JLabel de l'ecran de demarrage.
  JLabel jls = new JLabel("Score: "+s);                                         //JLabel du score.
  JLabel jlend = new JLabel("Score final: "+s);                                 //JLabel du score final.
  JLabel jlt = new JLabel("Time: "+ second);                                    //JLabel du temps ecoule.
  JLabel jl1 = new JLabel("Nombre d'arbres en vie: "+s1);                       //JLabel des arbres en vie.
  JLabel jl2 = new JLabel("Nombre d'arbres brulés: "+s2);                       //JLabel des arbres morts.
  JLabel jl3 = new JLabel("Nombre de pompiers en vie: "+s3);                    //JLabel des pompiers en vie.
  JLabel jl4 = new JLabel("Nombre de pompiers mort: "+s4);                      //JLabel des pompiers morts.
  JButton jbs = new JButton("Lancer une partie !");                             //JButton pour changer l'affichage du JPanel principal.
  Timer actualize = new Timer();                                                //Timer permettant l'actualisation des JPanel.
  boolean end = false;                                                          //renseigne sur l'etat de la partie.

  Fenetre(){

    //initialise la JFrame et la definit.

    fenetre.setTitle("Firefighters");
    fenetre.setSize(900,800);
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fenetre.setLocationRelativeTo(null);
    fenetre.setResizable(false);
    fenetre.setContentPane(p);
    fenetre.setVisible(true);

    //creer un ecran de demarrage en utilisant le JPanel principal.

    p.setBackground(new Color(41,31,71));
    p.setBorder(BorderFactory.createLineBorder(new Color(20,15,36),20));
    p.setLayout(null);
    jld.setForeground(Color.white);
    jld.setFont(new Font("Serif", Font.BOLD, 40));
    jld.setBounds(310,250,400,100);
    p.add(jld);
    jbs.setBackground(new Color(250,10,10));
    jbs.setForeground(Color.white);
    jbs.setBounds(340,550,200,50);
		jbs.addActionListener(this);
    p.add(jbs);

    //actualise le Timer permettant l'evolution des elements et la mise a jour des JPanel.

    actualize.schedule(new Refresh(), 1000, 1000);

  }

  //change le JPanel principal pour afficher le jeu et ses elements.

  void launch(){

    //definit les JLabel + ajoute les deux JPanel a la fenetre.

    JPanel p = new JPanel(new BorderLayout());
    p1 = new Pan1();
    p2 = new Pan2();


    p.add(p1, BorderLayout.CENTER);

    p2.setLayout(null);
    jls.setForeground(Color.white);
    jls.setFont(new Font("Serif", Font.BOLD, 16));
    jls.setBounds(700,35,150,100);
    p2.add(jls);
    jlt.setForeground(Color.white);
    jlt.setFont(new Font("Serif", Font.BOLD, 16));
    jlt.setBounds(700,65,150,100);
    p2.add(jlt);
    jl1.setForeground(Color.gray);
    jl1.setFont(new Font("Serif", Font.BOLD, 15));
    jl1.setBounds(200,50,300,20);
    p2.add(jl1);
    jl2.setForeground(Color.gray);
    jl2.setFont(new Font("Serif", Font.BOLD, 15));
    jl2.setBounds(200,75,300,20);
    p2.add(jl2);
    jl3.setForeground(Color.gray);
    jl3.setFont(new Font("Serif", Font.BOLD, 15));
    jl3.setBounds(200,100,300,20);
    p2.add(jl3);
    jl4.setForeground(Color.gray);
    jl4.setFont(new Font("Serif", Font.BOLD, 15));
    jl4.setBounds(200,125,300,20);
    p2.add(jl4);
    p.add(p2, BorderLayout.PAGE_END);

    end=false;
    start=System.currentTimeMillis();
    fenetre.setContentPane(p);
  }

  //ecran de fin de partie.

  void endgame(){

    fenetre.remove(p);
    fenetre.revalidate();
    JPanel p = new JPanel(new BorderLayout());
    p.setBackground(new Color(41,31,71));
    p.setBorder(BorderFactory.createLineBorder(new Color(20,15,36),20));
    p.setLayout(null);
    p.add(jld);
    p.add(jbs);
    jlend.setForeground(Color.white);
    jlend.setFont(new Font("Serif", Font.BOLD, 20));
    jlend.setBounds(351,350,400,100);
    jlend.setText("Score final: "+s);
    p.add(jlend);
    fenetre.setContentPane(p);
    fenetre.setVisible(true);
    fenetre.repaint();
  }

  //gestion d'evenement: appuie sur le bouton de l'ecran d'accueil.

  public void actionPerformed(ActionEvent e){

    launch();

  }

  //classe encapsulant la tache a execute par le Timer.

  class Refresh extends TimerTask {

    public void run(){
        if(end==false){

            //gere l'evolution des arbres resineux.

            for(int i=0;i<p1.r.length;i++){
              if(p1.r[i].etat=="mort"){
                s1--;
                s2++;
                s=s+50;
              }
              if(p1.r[i].etat=="brulant"){
                cpt++;
                p1.r[i].setOnFire();
                if(p1.r[i].etat=="mort"){
                  if(!p1.r[i].arrose){
                    for(int j=0;j<p1.r.length;j++){
                      if (contact(p1.r[i],p1.r[j])){
                        p1.r[j].setOnFire();
                      }
                    }
                    for(int j=0;j<p1.f.length;j++){
                      if (contact(p1.r[i],p1.f[j])){
                        p1.f[j].setOnFire();
                      }
                    }
                  }
                }
              }
            }

            //gere l'evolution des arbres feuillus.

            for(int i=0;i<p1.f.length;i++){
              if(p1.f[i].etat=="mort"){
                s1--;
                s2++;
                s=s+30;
              }
              if(p1.f[i].etat=="brulant"){
                cpt++;
                p1.f[i].setOnFire();
                if(p1.f[i].etat=="mort"){
                  if(!p1.f[i].arrose){
                    for(int j=0;j<p1.r.length;j++){
                      if (contact(p1.f[i],p1.r[j])){
                        p1.r[j].setOnFire();
                      }
                    }
                    for(int j=0;j<p1.f.length-5;j++){
                      if (contact(p1.f[i],p1.f[j])){
                        p1.f[j].setOnFire();
                      }
                    }
                  }
                }
              }
            }

            //gere l'evolution des pompiers et de la jauge.

            for (int i=0;i<p1.pom.length;i++){
              if(contact(p1.pom[i],p1.l)){
                if(p2.ecart>0){
                  p2.j.h2++;
                  p2.ecart--;
                }
              }
              if (!p1.pom[i].vie){
                s3--;
                s4++;
                s=s+500;
              }
              for(int j=0;j<p1.f.length;j++){
                if (contact(p1.pom[i],p1.f[j])){
                  if (p1.f[j].etat=="brulant"){
                    if (p2.j.h2>0){
                      p1.f[j].durability--;
                      p2.j.h2--;
                      p2.ecart++;
                      if (p1.f[j].durability<3){
                        p1.f[j].arrose=true;
                      }
                    }else if (p2.j.h2>-4){
                      p2.j.h2--;
                      p2.ecart++;
                    }
                    if (brulure(p1.pom[i],p1.f[j])){
                      p1.pom[i].pv--;
                      p1.pom[i].c = new Color(250,50+p1.pom[i].pv*4,0);
                      if (p1.pom[i].pv<=0){
                        p1.pom[i].vie=false;
                        p1.pom[i].x=-100;
                        p1.pom[i].y=-100;
                      }
                    }
                  }
                }
              }
              for(int k=0;k<p1.r.length;k++){
                if (contact(p1.pom[i],p1.r[k])){
                  if (p1.r[k].etat=="brulant"){
                    if (p2.j.h2>0){
                      p1.r[k].durability--;
                      p2.j.h2--;
                      p2.ecart++;
                      if (p1.r[k].durability<3){
                        p1.r[k].arrose=true;
                      }
                    }else if (p2.j.h2>-4){
                      p2.j.h2--;
                      p2.ecart++;
                    }
                    if (brulure(p1.pom[i],p1.r[k])){
                      p1.pom[i].pv--;
                      p1.pom[i].c = new Color(250,50+p1.pom[i].pv*4,0);
                      if (p1.pom[i].pv<=0){
                        p1.pom[i].vie=false;
                        p1.pom[i].x=-100;
                        p1.pom[i].y=-100;
                      }
                    }
                  }
                }
              }
            }

            //actualise les JLabel.

            second = (System.currentTimeMillis()-start)/1000;
            jlt.setText("Time: "+second);
            jls.setText("Score: "+s);
            jl1.setText("Nombre d'arbres en vie: "+s1);
            jl2.setText("Nombre d'arbres brulés: "+s2);
            jl3.setText("Nombre de pompiers en vie: "+s3);
            jl4.setText("Nombre de pompiers mort: "+s4);

            //mise a jour des jPanel.

            p1.repaint();
            p2.repaint();

            //si le nombre d'arbre brulant est 0, affiche l'ecran de fin de partie.

            if (cpt<=0){
              end=true;
              endgame();
            }

            //reinitialise les variables des stats pour le calcul de la prochaine actualisation.

            s1 = p1.f.length+p1.r.length;
            s2 = 0;
            s3 = p1.nbrpom+1;
            s4 = 0;
            s = 0;
            cpt = 0;
      }
    }

    //gere les CONTACTS entre pompiers et arbres\lac => remplissage jauge + arrosage des arbres.

    boolean contact(Element e1, Element e2){

      Rectangle r1;
      if (e1 instanceof Pompier){
        r1 = e1.firefighterbounds();
      }else{
        r1 = e1.extendedbounds();
      }
      Rectangle r2 = e2.bounds();
      if (r1.equals(r2)){
        return false;
      }else if (r1.intersects(r2)){
        return true;
      }else{
        return false;
      }
    }

    //gere les COLLISIONS entre pompiers et arbres => baisse de vie des pompiers.

    boolean brulure(Element e1, Element e2){

      Rectangle r1 = e1.bounds();
      Rectangle r2 = e2.bounds();

      if (r1.intersects(r2)){
        return true;
      }else{
        return false;
      }
    }

  }

  public static void main(String[] args){
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Fenetre f = new Fenetre();
        f.second = (System.currentTimeMillis()-f.start)/1000;
      }
    });
  }
}
