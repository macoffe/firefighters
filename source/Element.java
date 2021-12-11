import java.awt.Rectangle;
//import java.io.Serializable;

 class Element implements Spawnable{

   int x, y, m1, m2;                                                            // coordonnees de l'element: x > abscisses, y > ordonnees, m1 > largeur, m2 > hauteur.

   //randomise les coordonnees x et y pour les grands elements.

   public void setRandPosB(){
     this.x=(int)((Math.random()*650)+50);
     this.y=(int)((Math.random()*350)+50);
   }

   //randomise les coordonnees x et y pour les petits elements.

   public void setRandPosL(){
     this.x=(int)((Math.random()*830)+20);
     this.y=(int)((Math.random()*480)+40);
   }

   //definit les coordonnees x et y de l'element selon les parametres donnes.

   public void setPos(int x1, int y1){
     this.x=x1;
     this.y=y1;
   }

   //renvoi les bornes precises d'un element.

   public Rectangle bounds(){
     return (new Rectangle(this.x,this.y,this.m1,this.m2));
   }

   //renvoi les bornes etendues d'un element.

   public Rectangle extendedbounds(){
     return (new Rectangle(this.x-20,this.y-20,this.m1+35,this.m2+35));
   }

   //renvoi les bornes de la zone d'arrosage des pompiers.

   public Rectangle firefighterbounds(){
     return (new Rectangle(this.x-50,this.y-50,this.m1+95,this.m2+95));
   }

 }
