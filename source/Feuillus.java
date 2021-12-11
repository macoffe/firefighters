import java.awt.Color;

class Feuillus extends Arbre{

  final Color cf = new Color(97,196,91);                                        //couleur des arbres feuillus.

  Feuillus(){
    super();
    this.m1=d+5;
    this.m2=d+5;
    this.c=this.cf;
    this.durability=10;
  }

}
