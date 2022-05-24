package juegos.client.space;

import juegos.common.SharedConstants;
import juegos.common.UnoCarte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnoClientSpace extends ClientSpace
{
	
	
	private UnoCarte cardActuelle;
    private ArrayList<UnoCarte> tabPaquet = new ArrayList<>();
	private final ArrayList<JButton> tabJButtons = new ArrayList<>();
	private JLabel lblCarteActuelle;

	public UnoClientSpace() 
	{
		super(ClientSpaceType.UNO);
	}

	@Override
	public void handleCommand(String[] args) 
	{
		//this.updateCard(args[2]);
		if(args[1].equals("setCardActuelle"))
		{
			this.updateCard(args[2].charAt(0), Integer.parseInt(args[3]));
		}
		if(args[1].equals("sendPaquet"))
		{
			this.updatePaquet(args[2]);
		}
	}
	

	public void updateCard(char newCardCar, int newCardNum) 
	{
		this.cardActuelle.setCouleur(newCardCar);
		this.cardActuelle.setNumero(newCardNum);
		
	}
	private void updatePaquet()
	{
		this.sendCommand("getPaquet");
	}
	public void updatePaquet(String args)
	{
		String delims = "[,]";
		String[] tokens = args.split(delims);

		for(int i = 0;i< this.tabPaquet.size();i++) this.tabPaquet.remove(i);
		for(int i = 0; i< tokens.length;i++)
		{
			this.tabPaquet.add(new UnoCarte(tokens[i]));
			Image img = Toolkit.getDefaultToolkit().getImage("data/images/uno/"+this.tabPaquet.get(i).toString()+".png");
			//paquetPanel.add(new JButton(new ImageIcon(img)));
			this.tabJButtons.add(new JButton(new ImageIcon(img)));
		}
		
	}

	@Override
	public JPanel getUI() 
	{
		JPanel mainPanel    = new JPanel(new GridLayout(3,1));
		JPanel plateauPanel = new JPanel();
		JPanel paquetPanel  = new JPanel();
		this.cardActuelle = new UnoCarte("B4");
		this.lblCarteActuelle = new JLabel(new ImageIcon("data/images/uno/"+this.cardActuelle.toString()+".png"));
		//JPanel actionPanel  = new JPanel();
		
	
		plateauPanel.setLayout(new BorderLayout());
		paquetPanel.setLayout(new FlowLayout());

		plateauPanel.add(this.lblCarteActuelle);
		updatePaquet();
		for(int x = 0; x < this.tabJButtons.size();x++)
		{
			int finalX = x;
			
			this.tabJButtons.get(x).addActionListener(e -> this.sendCommand("sendCard",String.valueOf(finalX)));
			paquetPanel.add(this.tabJButtons.get(x));
		}
		mainPanel.add(new JLabel("Uno"));
		mainPanel.add(plateauPanel);
		mainPanel.add(paquetPanel);

		

		return mainPanel;
	}
}