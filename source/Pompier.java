import java.awt.Color;

class Pompier extends Element{

  int pv = 50;                                                                  //points de vie des pompiers.
  Color c = new Color(250,250,0);                                               //couleur des pompiers.
  final int l = 30;                                                             //hauteur et largeur des pompiers.
  boolean vie = true;                                                           //permet de savoir si le pompier est vivant ou non.

  Pompier(int x, int y){
    super.setPos(x,y);
    this.m1=l+5;
    this.m2=l+5;
  }

}
