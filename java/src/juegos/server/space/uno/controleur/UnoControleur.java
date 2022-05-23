package controleur;
import java.util.ArrayList;
import iut.algo.Clavier;

import metier.*;

public class UnoControleur
{
    private static final int NB_JOUEUR_MAX = 4;
    private static final int NB_CARTES     = 108;
    private int nbJoueur = 2;
    private UnoCarte cardActuelle;
    private UnoPlayer[] tabJoueurs; 
    private ArrayList<UnoCarte> tabCartes = new ArrayList<UnoCarte>();

    public UnoControleur(int nbJoueur, String[] tabPseudos)
    {       
        if(nbJoueur >= this.nbJoueur && nbJoueur <= NB_JOUEUR_MAX) this.nbJoueur = nbJoueur;
        this.tabJoueurs = new UnoPlayer[nbJoueur];
        getPioche();
        
        for(int i = 0; i<nbJoueur;i++)
        {
            this.tabJoueurs[i] = new UnoPlayer(tabPseudos[i], i,this);
        }  
        this.startGame();
        
        System.out.println("Game terminée");        
        
    }
    public UnoControleur(){}
    public void startGame()
    {
        boolean     winner  = false;
        this.cardActuelle   = this.getNewCard();    
        int         cpt     = 0;

        while(winner == false)
        {           
            System.out.println("C'est au tour de "+tabJoueurs[cpt].getPseudo());
            System.out.println();
            System.out.println(this.tabJoueurs[cpt].getPaquet());
            startTour(cpt,this.cardActuelle);
            
            if(tabJoueurs[cpt].getNbCartes()==0)
            {
                winner = true;
                System.out.println("le gagnant est "+tabJoueurs[cpt]);
            }
            cpt++;
            if(cpt==2)cpt = 0;
        }
    }
    public void startTour(int cpt,UnoCarte cardActuelle)
    {
        boolean verif  = false;
        System.out.println("\n"+cardActuelle);
        System.out.println("------------------------------");
        
        while(verif == false )
        {
            System.out.println("Choisir une action pioche ou jouer : \n");
            String actionJoueur = Clavier.lireString();
            
            if(actionJoueur.equals("pioche"))
            {
                this.tabJoueurs[cpt].addCard();
                verif = true;
            }

            if(actionJoueur.equals("jouer"))
            {
                System.out.println("Choisissez le numéro de la carte");
                int  numero  = Clavier.lire_int();

                if(this.tabJoueurs[cpt].getCard(numero).getCouleur()==this.cardActuelle.getCouleur() || this.tabJoueurs[cpt].getCard(numero).getNumero()==this.cardActuelle.getNumero() || this.tabJoueurs[cpt].getCard(numero).getNumero() == this.cardActuelle.getNumero() )
                {
                    this.tabCartes.add(this.cardActuelle);
                    this.cardActuelle = this.tabJoueurs[cpt].getCard(numero);
                    this.tabJoueurs[cpt].cardRemove(numero);
                    verif = true;
                }

                if(this.tabJoueurs[cpt].getCard(numero).getNumero() == 12 && this.tabJoueurs[cpt].getCard(numero).getCouleur() == this.cardActuelle.getCouleur() || this.tabJoueurs[cpt].getCard(numero).getNumero() == 14 )
                {
                    powerCarte(this.tabJoueurs[cpt].getCard(numero).getNumero(), cpt);
                }
                if(this.tabJoueurs[cpt].getCard(numero).getNumero() == 10 || this.tabJoueurs[cpt].getCard(numero).getNumero() == 11)
                {
                    verif = false;
                }
            }
        }
        
    }

    public UnoCarte getNewCard()
    {
        int index = (int)(Math.random()*NB_CARTES);
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

            //On ajoute les numéro de 1 à 9 puis on les duplique
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
            
            //Numéro 4 avec une couleur noire c'est le +4 avec choix des couleurs
            this.tabCartes.add(new UnoCarte(couleurs[4], 14));

            //On prend comme numéro de carte 5 pour le choix des couleurs
            this.tabCartes.add(new UnoCarte(couleurs[4], 15));


        }
    }
    public void powerCarte(int numero, int cpt)
    {
        if(cpt == 0)
        {
            for(int i = 0; i<numero;i++)
            {
                this.tabJoueurs[1].addCard();
            }
        }
        else
        {
            for(int i = 0; i<numero;i++)
            {
                this.tabJoueurs[0].addCard();
            }
        }
    }
    public static void main(String[] args)
    {
        int nbJoueur = 2;
        String joueur1 = "Adrien";
        String joueur2 = "Hugo";
        String[] joueurs = new String[nbJoueur];
        joueurs[0]=joueur1;
        joueurs[1]=joueur2;

        new UnoControleur(nbJoueur,joueurs);
    }
}