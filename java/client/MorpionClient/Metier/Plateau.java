package MorpionClient.Metier;

import iut.algo.Clavier;

public class Plateau
{
    private static final int TAILLE_G = 3;

    private char[][] grille;

    public Plateau()
    {
        this.grille = new char[TAILLE_G][TAILLE_G];

        for (int lig=0; lig<grille.length; lig++)
            for (int col=0; col< grille[lig].length; col++)
                grille[lig][col] = ' ';

        System.out.println(Plateau.afficherGrille (grille));+
    }

    public static String afficherGrille(char[][] tab)
    {
        String sRet;
        String sSepHori;

        sSepHori = "  +";
        for (int cpt=0; cpt< tab[0].length;cpt++) sSepHori += "---+";

        sRet = "    ";
        for (int cpt=0; cpt< tab[0].length;cpt++) sRet += (char) ('A' + cpt) + "   ";

        sRet += "\n" + sSepHori + "\n";

        for (int lig=0; lig< tab.length;lig++)
        {
            sRet += (lig+1) + " |";
            for (int col=0; col< tab[lig].length;col++)
                sRet += " " + tab[lig][col] + " |";

            sRet += "\n" + sSepHori + "\n";
        }

        return sRet;
    }
    public static void main(String[] args) {new Plateau();}
}
