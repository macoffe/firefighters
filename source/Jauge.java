import java.awt.Color;

class Jauge extends Element{

  final Color c1 = new Color(20,15,36);                                         //couleur de la partie vide de la jauge.
  final Color c2 = new Color(76,146,195);                                       //couleur de la partie pleine de la jauge.
  final int lar = 50;                                                           //largeur de la jauge.
  final int h1 = 126;                                                           //hauteur totale de la jauge.
  int h2 = 0;                                                                   //hauteur de la partie remplie.

  Jauge(int x, int y){
    super.setPos(x,y);
  }
}
