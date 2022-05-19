package juegos.client.morpion.metier;

import java.util.Arrays;

public class MorpionPlateau
{
	private static final int TAILLE_G = 3;

	private final char[][] grille;

	public MorpionPlateau() {
		this.grille = new char[TAILLE_G][TAILLE_G];

		for(char[] chars : grille) Arrays.fill(chars, ' ');

		System.out.println(MorpionPlateau.afficherGrille(grille));
	}

	public static String afficherGrille(char[][] tab) {
		StringBuilder sRet;
		StringBuilder sSepHori;

		sSepHori = new StringBuilder("  +");
		sSepHori.append("---+".repeat(tab[0].length));

		sRet = new StringBuilder("    ");
		for(int cpt = 0; cpt < tab[0].length; cpt++) sRet.append((char) ('A' + cpt)).append("   ");

		sRet.append("\n").append(sSepHori).append("\n");

		for(int lig = 0; lig < tab.length; lig++) {
			sRet.append(lig + 1).append(" |");
			for(int col = 0; col < tab[lig].length; col++)
				sRet.append(" ").append(tab[lig][col]).append(" |");

			sRet.append("\n").append(sSepHori).append("\n");
		}

		return sRet.toString();
	}

	public static void main(String[] args) {new MorpionPlateau();}
}
