package juegos.server.space;

import juegos.server.ServerPlayer;

import java.util.Arrays;

public class TTTServerSpace extends ServerSpace
{
    private char[][] tabChar;
    private ServerPlayer[] player;
    private int turn;

    public TTTServerSpace() {
        super(ServerSpaceType.TIC_TAC_TOE);

        this.tabChar = new char[3][3];
        for(char[] line : tabChar)
            Arrays.fill(line, ' ');

        this.player = new ServerPlayer[2];
        this.turn =0;
    }

    @Override
    public boolean canAccept(ServerPlayer player) {
        return this.getPlayers().size() <= this.player.length;
    }

    @Override
    public void handleCommand(ServerPlayer player, String[] args)
    {
        if(args[0].equals("set"))
        {
            int lig = Integer.parseInt(args[1]);
            int col = Integer.parseInt(args[2]);
            if(canPlace(lig, col))
                place(lig, col, getPlayerChar(player));
        }
    }

    public char getPlayerChar(ServerPlayer player) {
        return this.getPlayers().get(0) == player ? 'X' : 'O';
    }

    public boolean canPlace(int lig, int col) {
        return this.tabChar[lig][col] == ' ';
    }

    public void place(int lig, int col, char c)
    {
        this.tabChar[lig][col] = c;
        for (ServerPlayer player : this.getPlayers()) {
            this.sendCommand(player, "set", String.valueOf(lig), String.valueOf(col), String.valueOf(this.tabChar[lig][col]));
        }
    }

    public void nextTurn()
    {
        this.turn = (this.turn + 1) % this.player.length;
    }

    public int getNbVide()
    {
        int nbVide = 0;

        for (char[] line : this.tabChar)
            if (Arrays.toString(line).equals(" "))
            {
                nbVide++;
            }
        return nbVide;
    }

    /*public boolean checkWin() {
        boolean win = false;
        if (
                        tabChar[1][0] == tabChar[1][1] && tabChar[1][0] == tabChar[1][2] && tabChar[1][0] == this.getPlayerChar() ||
                        tabChar[2][0] == tabChar[2][1] && tabChar[1][0] == tabChar[2][2] && tabChar[2][0] == this.getPlayerChar() ||
                        tabChar[0][0] == tabChar[0][1] && tabChar[1][0] == tabChar[0][2] && tabChar[0][0] == this.getPlayerChar() ||

                        tabChar[0][0] == tabChar[1][0] && tabChar[1][0] == tabChar[2][0] && tabChar[0][0] == this.getPlayerChar() ||
                        tabChar[0][1] == tabChar[1][1] && tabChar[1][0] == tabChar[2][1] && tabChar[0][1] == this.getPlayerChar() ||
                        tabChar[0][2] == tabChar[1][2] && tabChar[1][0] == tabChar[2][2] && tabChar[0][2]== this.getPlayerChar() ||

                        tabChar[0][0] == tabChar[1][1] && tabChar[0][0] == tabChar[2][2] && tabChar[0][0] == this.getPlayerChar() ||
                        tabChar[2][0] == tabChar[1][1] && tabChar[2][0] == tabChar[0][2] && tabChar[2][0] == this.getPlayerChar()
        )
        {
            win = true;
        }
        else
        {
            if (getNbVide() == 0) {win = false;}
        }

        for(ServerPlayer player : this.getPlayers())
            player.leave();

        return win;
    }*/
}

