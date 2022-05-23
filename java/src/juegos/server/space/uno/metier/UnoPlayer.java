package metier;
import java.util.ArrayList;
import controleur.UnoControleur;

public class UnoPlayer 
{
    private String  pseudo;
    private int     numJoueur;
    private int     nbCartes = 7;
    private ArrayList<UnoCarte> tabCartes = new ArrayList<UnoCarte>();
    private UnoControleur ctrl;

    public UnoPlayer(String pseudo, int numJoueur, UnoControleur ctrl)
    {
        this.numJoueur  = numJoueur;
        this.pseudo     = pseudo;
        this.ctrl       = ctrl;
        this.piocheDebut();
    }

    public String getPseudo(){return this.pseudo;}
    public int getNbCartes(){return this.tabCartes.size();}
    public UnoCarte getCard(int index){return tabCartes.get(index);}
    public void addCard(){this.tabCartes.add(this.ctrl.getNewCard());}
    public void cardRemove(int index){this.tabCartes.remove(index);}

    private void piocheDebut()
    {
        for(int i = 0; i<this.nbCartes;i++)
        {
            this.tabCartes.add(this.ctrl.getNewCard());
        }
    }
    public String getPaquet()
    {
        String ch = "";
        for(int i= 0;i<this.tabCartes.size();i++)
        {
            ch += ""+this.tabCartes.get(i)+"/";
        }
        return ch;
    }


    
}
