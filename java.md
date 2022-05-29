# 🍵 Explication du programme Java

[Revenir sur la page principale](./index.md)

## Threads
Lors du développement de Juegos, nous sommes tombés sur le problème suivant : le serveur ne pouvait accepter qu'un seul joueur.
Nous avons alors fait quelques recherches sur internet et sommes tombés sur le concept des ***threads***.

Un *thread* est un processus qui part d'un début d'algorithme et réalise toutes ses étapes une à une jusqu'à sa fin.
Tout algorithme Java que l'on exécute se retrouve donc encapsulé dans un *thread*. La subtilité est qu'il est possible de créer d'autres *threads* !

Nous avons consulté cette vidéo (en anglais) expliquant la classe `Thread` de Java afin de pouvoir l'utiliser :
<iframe width="420" height="315" src="https://www.youtube.com/embed/r_MbozD32eo"> </iframe>

Avec ces nouvelles compétences, nous avons alors dessiné l'esquisse de notre système de connections multiples :
```java
ServerSocket serverSocket = new ServerSocket(port); // création du serveur
while(true)
{
    Socket socker = serverSocket.accept();          // cette ligne attend la prochaine connexion
    ServerPlayer joueur = new ServerPlayer();       // création d'un joueur qui représentera notre nouvelle connexion
    
		joueur.writer = new PrintWriter(socket.getOutputStream(), true);
		joueur.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
    // création d'un thread qui va lire en boucle ce que le joueur lui envoie
    // cela permet de lire toutes les requêtes joueur, peut importe le nombre de joueurs connectés
    Thread thread = new Thread(() ->
    {
        while(true)
        {
            joueur.read();
        }
    });
    thread.start();
    
    // la boucle redémarre après avoir ajouté le joueur au serveur
}
```
> *Le code présenté sert de représentation du code réel. Il est possible alors que vous trouviez des différences dans le code source de Juegos.*
