import java.awt.Color;

class Resineux extends Arbre{

  final Color cr = new Color(56,117,52);                                        //couleur des arbres resineux.

  Resineux(){
    super();
    this.m1=d+5;
    this.m2=d+5;
    this.c=this.cr;
    this.durability=15;
  }

}
