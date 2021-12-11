import java.awt.Color;

class Plaine extends Element{

  final Color c = new Color(234,177,47);                                        //couleur de la plaine.
  final int a1 = 200;                                                           //largeur de la plaine.
  final int a2 = 150;                                                           //hauteur de la plaine.

  Plaine(){
    super.setRandPosB();
    this.m1=a1+5;
    this.m2=a2+5;
  }

}
