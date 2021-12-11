import java.awt.Color;

class Arbre extends Element{

  final Color cb = new Color(250,10,10);                                        //couleur des arbres brulants.
  final Color cm = new Color(30,30,30);                                         //couleur des arbres morts.
  Color c;                                                                      //couleur active des arbres.
  String etat;                                                                  //renseigne sur l'etat des l'arbres: vivant, brulant, mort.
  int durability;                                                               //renseigne sur la durabilite des arbres.
  boolean arrose = false;                                                       //permet de savoir si l'arbre a ete arrose par un pompier ou non.
  final int d = 30;                                                             //diametre des arbres.

  Arbre(){
    super.setRandPosL();
    this.etat="vivant";
  }

  //permet d'actualiser les arbres brulants + l'initialisation de l'incendie.

  void setOnFire(){
    this.etat="brulant";
    this.c=this.cb;
    this.durability=this.durability-1;
    if (this.durability<=0){
      this.etat="mort";
      this.c=this.cm;
    }
  }

}
