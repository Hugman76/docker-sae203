package morpion.vue;

import javax.swing.*;

public class plateauPanel extends JPanel
{
    private JButton[][] tabBtnCase;

    public plateauPanel()
    {

        //Création des composants
        this.tabBtnCase = new JButton[3][3];

        for (int lig = 0; lig<this.tabBtnCase.length; lig++)
            for (int col = 0; col<this.tabBtnCase.length; col++)
                this.tabBtnCase[lig][col] = new JButton(new ImageIcon());

        //Positionnement des composants
        for (int lig = 0; lig<this.tabBtnCase.length; lig++)
            for (int col = 0; col<this.tabBtnCase.length; col++)
                this.add(this.tabBtnCase[lig][col]);

    }

}
