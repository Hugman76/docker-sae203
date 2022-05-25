package juegos.client.space;

import juegos.common.SharedConstants;
import juegos.common.UnoCarte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnoClientSpace extends ClientSpace
{
	
	
	private UnoCarte cardActuelle;
	private int 	 id;
    private ArrayList<UnoCarte> tabPaquet = new ArrayList<>();
	private ArrayList<JButton> tabJButtons = new ArrayList<JButton>();
	private Image img11 = Toolkit.getDefaultToolkit().getImage("data/images/uno/B1.png");
	private JLabel lblCarteActuelle = new JLabel();
	private boolean request = false;
	private int nbCartes = 7;

	public UnoClientSpace() 
	{
		super(ClientSpaceType.UNO);
		System.out.println("UnoCLientSpace");
		
	}

	@Override
	public void handleCommand(String[] args) 
	{
		//System.out.println(args[0]);
		if(args[0].equals("sendCardActuelle"))
		{
			this.updateCarteActuelle(args[1]);
			System.out.println("YES");
		}
		if(args[0].equals("sendPaquet"))
		{
			this.updatePaquet(args[1]);
			System.out.println(args[1]);
			System.out.println("Talle du paquet"+this.tabPaquet.size());
		}
	}
	public void updateCarteActuelle(String im)
	{
		Image img = Toolkit.getDefaultToolkit().getImage("data/images/uno/"+im+".png");
		this.lblCarteActuelle.setIcon(new ImageIcon(img));		
	}
	

	public void updateCard(char newCardCar, int newCardNum) 
	{
		this.cardActuelle.setCouleur(newCardCar);
		this.cardActuelle.setNumero(newCardNum);
		
	}
	public void updatePaquet(String args)
	{
		System.out.print("update");
		String delims = "[,]";
		String[] tokens = args.split(delims);
		System.out.println("Tokens :");
		
		System.out.println("Update paquet de carte");
		//for(int i = 0;i<this.tabJButtons.size();i++) this.tabJButtons.remove(i);
		for(int i = 0; i< tokens.length;i++)
		{
			//System.out.println(tokens[i]);
			this.tabPaquet.add(new UnoCarte(tokens[i]));
			System.out.println("carte "+this.tabPaquet.get(i).toString()+" "+this.tabPaquet.size());
			
			Image img = Toolkit.getDefaultToolkit().getImage("data/images/uno/"+this.tabPaquet.get(i).toString()+".png");
			this.tabJButtons.add(new JButton());
			this.tabJButtons.get(i).setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			
		}
		
	}

	@Override
	public JPanel getUI() 
	{
		JPanel mainPanel    = new JPanel(new GridLayout(3,1));
		//JPanel plateauPanel = new JPanel();
		JPanel paquetPanel  = new JPanel();
		JButton btnOk       = new JButton();
		//this.cardActuelle = new UnoCarte("B4");
		System.out.println("getUI");
		JLabel lblCarte = this.lblCarteActuelle;
		int nb = this.tabJButtons.size();
		System.out.println(nb);
		
		
		for(int i =0; i<7;i++)
		{
			
			this.tabJButtons.add(new JButton());
			paquetPanel.add(this.tabJButtons.get(i));

		}

		btnOk.addActionListener(e -> this.sendCommand("getOK"));

		mainPanel.add(btnOk);
		mainPanel.add(lblCarte);
		mainPanel.add(paquetPanel);
		
		
		return mainPanel;
	}
}