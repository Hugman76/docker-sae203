package juegos.common;

import juegos.server.ServerPlayer;
import juegos.server.space.uno.UnoServerSpace;

import java.util.ArrayList;

public class UnoPlayer 
{
    private ServerPlayer player;
    private int     numJoueur;
    private int     nbCartes = 7;
    private ArrayList<UnoCarte> tabCartes = new ArrayList<>();
    private UnoServerSpace ctrl;

    public UnoPlayer(ServerPlayer player, int numJoueur, UnoServerSpace ctrl)
    {
        this.numJoueur  = numJoueur;
        this.player     = player;
        this.ctrl       = ctrl;
        this.piocheDebut();
    }

    public ServerPlayer getPlayer(){return this.player;}
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
