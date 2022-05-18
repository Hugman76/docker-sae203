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
            {
                grille[lig][col] = 'X';
                System.out.println(grille[lig][col]);
            }
    }

    public static void main(String[] args) {new Plateau();}
}
