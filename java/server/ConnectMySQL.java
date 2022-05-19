import java.sql.*;
public class ConnectMySQL
{
  public ConnectMySQL()
  {
    
  }
  public static void getTable()
  {
    try
    {
      //étape 1: charger la classe de driver
      Class.forName("com.mysql.jdbc.Driver");
      //étape 2: créer l'objet de connexion
      Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/menu", "sae", "sae");
      //étape 3: créer l'objet statement 
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM menu_items ");
      //étape 4: exécuter la requête
      while(res.next())
        System.out.println(res.getInt(1)+"  "+res.getString(2));
      //étape 5: fermez l'objet de connexion
      conn.close();
    }
    catch(Exception e){ 
      System.out.println(e);
    }
  }
}