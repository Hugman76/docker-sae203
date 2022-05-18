import java.sql.*;
public class ConnectMySQL
{
  public static void test()
  {
    try
    {
      //étape 1 : charger la classe de driver
      Class.forName("org.mariadb.jdbc.Driver");
      //étape 2 : créer l'objet de connexion

      Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/menu", "review_site", "JxSLRkdutW");
      //étape 3 : créer l'objet statement
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM menu_items");
      //étape 4 : exécuter la requête
      while(res.next())
        System.out.println(res.getInt(1)+"  "+res.getString(2)+"  "+res.getString(3)+"  "+res.getString(4));
      //étape 5 : fermez l'objet de connexion
      conn.close();
    }
    catch(Exception e){ 
      System.out.println(e);
    }
  }
}