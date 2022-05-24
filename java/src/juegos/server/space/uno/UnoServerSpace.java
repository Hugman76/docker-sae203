package juegos.server.space.uno;

import juegos.server.ServerPlayer;
import juegos.server.space.ServerSpace;
import juegos.server.space.ServerSpaceType;
import juegos.common.UnoCarte;
import juegos.common.UnoPlayer;

import java.util.ArrayList;

public class UnoServerSpace extends ServerSpace
{
    private static final int NB_JOUEUR_MAX = 4;
    private static final int NB_CARTES     = 108;
    private int nbJoueur = 2;
    private UnoCarte cardActuelle;
    private ArrayList<UnoPlayer> unoPlayers;
    private ArrayList<UnoCarte> tabCartes = new ArrayList<>();

    public UnoServerSpace() {
        this(2);
    }

    public UnoServerSpace(int nbJoueur) {
        super(ServerSpaceType.UNO);
        if(nbJoueur >= this.nbJoueur && nbJoueur <= NB_JOUEUR_MAX) this.nbJoueur = nbJoueur;
        getPioche();

        this.startGame();

        System.out.println("Game terminée");
    }

    @Override
    public void handleJoin(ServerPlayer player) {
        super.handleJoin(player);
        this.unoPlayers.add(new UnoPlayer(player, this.getPlayers().size(), this));
    }

    @Override
    public boolean canAccept(ServerPlayer player) {
        return this.getPlayers().size() < this.nbJoueur;
    }

    @Override
    public void handleCommand(ServerPlayer player, String[] args) {
        
    }

    public void startGame()
    {
        boolean     winner  = false;
        this.cardActuelle   = this.getNewCard();
        int         cpt     = 0;

        while(winner == false)
        {
            System.out.println("C'est au tour de "+unoPlayers.get(cpt).getPlayer().getName());
            System.out.println();
            System.out.println(this.unoPlayers.get(cpt).getPaquet());
            startTour(cpt,this.cardActuelle);

            if(unoPlayers.get(cpt).getNbCartes()==0)
            {
                winner = true;
                System.out.println("le gagnant est "+unoPlayers.get(cpt));
            }
            cpt++;
            if(cpt==2)cpt = 0;
        }
    }
    public void startTour(int cpt, UnoCarte cardActuelle)
    {
        boolean verif  = false;
        System.out.println("\n"+cardActuelle);
        System.out.println("------------------------------");

        while(verif == false )
        {
            System.out.println("Choisir une action pioche ou jouer : \n");
            String actionJoueur = "Clavier.lireString()";

            if(actionJoueur.equals("pioche"))
            {
                this.unoPlayers.get(cpt).addCard();
                verif = true;
            }

            if(actionJoueur.equals("jouer"))
            {
                System.out.println("Choisissez le numéro de la carte");
                int  numero  = 0; //Clavier.lire_int()

                if(this.unoPlayers.get(cpt).getCard(numero).getCouleur()==this.cardActuelle.getCouleur() || this.unoPlayers.get(cpt).getCard(numero).getNumero()==this.cardActuelle.getNumero() || this.unoPlayers.get(cpt).getCard(numero).getNumero() == this.cardActuelle.getNumero() )
                {
                    this.tabCartes.add(this.cardActuelle);
                    this.cardActuelle = this.unoPlayers.get(cpt).getCard(numero);
                    this.unoPlayers.get(cpt).cardRemove(numero);
                    verif = true;
                }
                else if(this.unoPlayers.get(cpt).getCard(numero).getNumero() == 12 && this.unoPlayers.get(cpt).getCard(numero).getCouleur() == this.cardActuelle.getCouleur() || this.unoPlayers.get(cpt).getCard(numero).getNumero() == 14 )
                {
                    giveCard(this.unoPlayers.get(cpt).getCard(numero).getNumero(), cpt);
                    verif = true;
                }
                else if(this.unoPlayers.get(cpt).getCard(numero).getNumero() == 10 || this.unoPlayers.get(cpt).getCard(numero).getNumero() == 11)
                {
                    verif = false;
                }
            }
        }

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