import java.awt.Color;

class Lac extends Element{

  final Color c = new Color(76,146,195);                                        //couleur du lac.
  final int d = 150;                                                            //diametre du lac.

  Lac(){
    super.setRandPosB();
    this.m1=d+5;
    this.m2=d+5;
  }

}
