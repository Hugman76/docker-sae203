package morpion.vue;

import javax.swing.*;

public class plateauFrame extends JFrame
{
    private plateauPanel panel;

    public plateauFrame()
    {
        //Création des composants
        this.panel = new plateauPanel();

        //Positionnement des composants
        this.add(this.panel);

        this.setVisible(true);
    }

    public static void main(String[] args) {new plateauFrame();}
}
