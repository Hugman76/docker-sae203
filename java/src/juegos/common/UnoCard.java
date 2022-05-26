package juegos.common;

import java.util.ArrayList;

public class UnoCard
{
	public static final char[] NORMAL_COLORS = {'R', 'G', 'B', 'J'};
	public static final char NO_COLOR = 'N';

	private char color;
	private int i;

	/**
	 * Constructeur de carte Uno
	 * @param color couleur de la carte
	 * @param i numéro de la carte, compris de 0 à 99
	 */
	private UnoCard(char color, int i) {
		this.color = color;
		this.i = i;
	}

	public String toString() {
		return "" + this.color + "" + this.i;
	}

	public static UnoCard fromString(String card) {
		char couleur = card.charAt(0);
		int numero = 0;
		if(card.length() == 3) {
			numero += Character.getNumericValue(card.charAt(1)) * 10;
			numero += Character.getNumericValue(card.charAt(2));
		}
		else {
			numero = Character.getNumericValue(card.charAt(1));
		}
		return new UnoCard(couleur, numero);
	}

	public static String listToString(ArrayList<UnoCard> list) {
		StringBuilder ch = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			ch.append(list.get(i).toString());
			if(i != list.size() - 1) {
				ch.append(SharedConstants.ARGUMENT_DELIMITER);
			}
		}
		return ch.toString();
	}

	public static ArrayList<UnoCard> listFromString(String s) {
		ArrayList<UnoCard> list = new ArrayList<>();
		for(String card : s.split(SharedConstants.ARGUMENT_DELIMITER)) {
			list.add(UnoCard.fromString(card));
		}
		return list;
	}

	public boolean isSpecial() {
		return this.i >= 10;
	}

    public static ArrayList<UnoCard> createInitialDrawCards() {
        ArrayList<UnoCard> pioche = new ArrayList<>();

		for(char couleur : NORMAL_COLORS) {
			pioche.add(new UnoCard(couleur, 0)); //Il y a qu'un seul 0 par couleur

			// Toutes les cartes de couleur sont présentes en double
			for(int i = 0; i < 2; i++) {
				for(int j = 1; j < 10; j++) {
					pioche.add(new UnoCard(couleur, j));
				}
				pioche.add(new UnoCard(couleur, 10)); // interdiction de jouer
				pioche.add(new UnoCard(couleur, 11)); // changement de sens
				pioche.add(new UnoCard(couleur, 12)); // +2
			}
		}

        for(int i = 0; i < 4; i++) {
            pioche.add(new UnoCard(NO_COLOR, 14)); // +4
            pioche.add(new UnoCard(NO_COLOR, 15)); // Choix de couleurs
        }
        return pioche;
    }
}
