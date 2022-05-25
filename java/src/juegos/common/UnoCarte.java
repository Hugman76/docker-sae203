package juegos.common;

public class UnoCarte 
{
    private char couleur;
    private int  numero; 
    

    public UnoCarte(char couleur,int numero)
    {
        this.couleur = couleur;
        this.numero  = numero;
    }
    public UnoCarte(String carte)
    {

        this.couleur = carte.charAt(0);
        if(carte.length() == 3)
        {
            this.numero  +=  Character.getNumericValue(carte.charAt(1)) *10;
            this.numero  +=  Character.getNumericValue(carte.charAt(2)) ;
        }
        else
        {
            this.numero  =  Character.getNumericValue(carte.charAt(1));
        }
       
        //System.out.println("Carte : "+carte +" num " + this.numero + " couleur "+this.couleur);
    }
    public char getCouleur()
    {
        return this.couleur;
    }
    public int getNumero()
    {
        return this.numero;
    }
    public void setCouleur(char couleur)
    {
        this.couleur = couleur;
    }
    public void setNumero(int numero)
    {
        this.numero = numero;
    }
    public String toString()
    {
        return ""+this.couleur+""+this.numero;
    }    
}
