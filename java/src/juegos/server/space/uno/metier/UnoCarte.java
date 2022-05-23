package juegos.server.space.uno.metier;

public class UnoCarte 
{
    private char couleur;
    private int  numero; 
    

    public UnoCarte(char couleurs,int numero)
    {
        this.couleur = couleurs;
        this.numero  = numero;
    }
    public char getCouleur()
    {
        return this.couleur;
    }
    public int getNumero()
    {
        return this.numero;
    }
    public String toString()
    {
        return ""+this.couleur+this.numero;
    }    
}
