public class JuegosServer
{
	private static final boolean DEBUG = false;
	public static void main(String[] args)
	{
		System.out.println("Serveur démarré !");
		ConnectMySQL.test();
		for(int i = 0; i < 100; i++)
		{
			System.out.print("aaaaaa");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}