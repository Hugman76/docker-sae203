package juegos.server.space.uno;

import juegos.server.ServerPlayer;
import juegos.server.space.ServerSpace;
import juegos.server.space.ServerSpaceType;
import juegos.common.UnoCarte;
import juegos.common.UnoPlayer;

import java.util.ArrayList;

public class UnoServerSpace extends ServerSpace
{
    private static final int NB_JOUEUR_MAX = 2;
    private static final int NB_CARTES     = 108;
    private final ServerPlayer[] players;
    private int nbJoueur = 2;
    private int cpt = 0;
    private boolean ok = false;
    private UnoCarte cardActuelle;
    private ArrayList<UnoPlayer> unoPlayers = new ArrayList<>();
    private ArrayList<UnoCarte> tabCartes = new ArrayList<>();

    public UnoServerSpace() 
    {
        this(2);
    }

    public UnoServerSpace(int nbJoueur) 
    {
        super(ServerSpaceType.UNO);
        this.players = new ServerPlayer[nbJoueur];
        this.getPioche();
        this.cardActuelle = getNewCard();
        
    }


    @Override
    public void handleJoin(ServerPlayer player) 
    {
        
        super.handleJoin(player);
         
        this.unoPlayers.add(new UnoPlayer(player, this.players.length, this)); 
        this.players[this.cpt] = this.unoPlayers.get(this.cpt).getPlayer();
        this.sendCommand(this.unoPlayers.get(cpt).getPlayer(), "sendCardActuelle",""+cardActuelle.getCouleur()+""+cardActuelle.getNumero());
        this.sendCommand(this.unoPlayers.get(cpt).getPlayer(), "sendPaquet",this.unoPlayers.get(cpt).getPaquet());
        System.out.println(this.unoPlayers.get(cpt).getPaquet());
        this.cpt++;
    }

    @Override
    public boolean canAccept(ServerPlayer player) 
    {
         
        return this.getPlayers().size() < this.nbJoueur;
    }
    

    @Override
    public void handleCommand(ServerPlayer player, String[] args) 
    {
        if(args[0].equals("getOK"))
        {
            this.startTour(getIndexPlayers(player), this.cardActuelle);
            System.out.println("OKKKKK");
        }
    }
    @Override
	public void handleLeave(ServerPlayer player) 
    {
		super.handleLeave(player);
		
	}
    public int getIndexPlayers(ServerPlayer player)
    {
        int index =0;
        for(int i = 0;i<this.players.length;i++)
        {
            if(player==this.players[i]  )return i;
        }
        return 3;
    }
    public String paquetToString(int cpt)
    {
        String ch ="";

        for(int i = 0;i<this.unoPlayers.get(this.cpt).getNbCartes();i++)
        {
            ch += this.unoPlayers.get(this.cpt).getCard(i).toString()+",";
        }
        return ch;
    }
    
    public void startTour(int cpt, UnoCarte cardActuelle)
    {
        System.out.println("TOUR");
        this.sendCommand(this.unoPlayers.get(cpt).getPlayer(), "sendPaquet",this.unoPlayers.get(cpt).getPaquet());
        //this.sendCommand(this.unoPlayers.get(cpt).getPlayer(), "sendCardActuelle",""+cardActuelle.getCouleur()+""+cardActuelle.getNumero());

    }

    public UnoCarte getNewCard()
    {
        int index = (int)(Math.random()*this.tabCartes.size());
        UnoCarte card = this.tabCartes.get(index);
        this.tabCartes.remove(index);
        return card;
    }
    private void getPioche()
    {
        char[] couleurs = {'R','G','B','J','N'};

        for(int i = 0; i<couleurs.length-1;i++)
        {
            //Il y a qu'un seul 0 par couleur
            this.tabCartes.add(new UnoCarte(couleurs[i], 0));

            //On ajoute les numéros de 1 à 9 puis on les duplique
            for(int j = 1; j<10;j++)
            {
                this.tabCartes.add(new UnoCarte(couleurs[i], j));
                this.tabCartes.add(new UnoCarte(couleurs[i], j));
            }
            //On double les trois cartes
            for(int j = 0; j<2;j++)
            {
                //La carte numéro 10 pour l'interdiction de jouer
                this.tabCartes.add(new UnoCarte(couleurs[i], 10));
                //La carte numéro 11 pour le changement de sens
                this.tabCartes.add(new UnoCarte(couleurs[i], 11));
                //La carte numéro 12 pour le +2
                this.tabCartes.add(new UnoCarte(couleurs[i], 12));
            }

            //Numéro 14 avec une couleur noire c'est le +4 avec choix des couleurs
            this.tabCartes.add(new UnoCarte(couleurs[4], 14));

            //On prend comme numéro de carte 15 pour le choix des couleurs
            this.tabCartes.add(new UnoCarte(couleurs[4], 15));


        }
    }

    public void giveCard(int numero, int cpt)
    {
        if(cpt == 0) {
            for(int i = 0; i < numero;i++) {
                this.unoPlayers.get(1).addCard();
            }
        }
        else {
            for(int i = 0; i < numero;i++) {
                this.unoPlayers.get(0).addCard();
            }
        }
    }
}