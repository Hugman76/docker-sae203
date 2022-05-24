package juegos.server.space;

import juegos.server.ServerPlayer;

import java.util.Arrays;

public class TTTServerSpace extends ServerSpace
{
    private char[][] tabChar;

    public TTTServerSpace() {
        super(ServerSpaceType.TIC_TAC_TOE);

        this.tabChar = new char[3][3];
        for(char[] line : tabChar)
            Arrays.fill(line, ' ');
    }

    @Override
    public boolean canAccept(ServerPlayer player) {
        return this.getPlayers().size() <= 2;
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
    /*public boolean checkWin()
    {
        boolean win = false;
        ServerPlayer player;
        for (int lig=0; lig < this.tabChar.length; lig++)
            for (int col=0; col < this.tabChar.length; col++)
            {
                win =   tabChar[0][0] == tabChar [0][1] == tabChar[0][2] == this.getPlayerChar(player)||
                        tabChar[1][0] == tabChar [1][1] == tabChar[1][2] == this.getPlayerChar(player) ||
                        tabChar[2][0] == tabChar [2][1] == tabChar[2][2] == this.getPlayerChar(player) ||

                        tabChar[0][0] == tabChar [1][0] == tabChar[2][0] == this.getPlayerChar(player) ||
                        tabChar[0][1] == tabChar [1][1] == tabChar[2][1] == this.getPlayerChar(player) ||
                        tabChar[0][2] == tabChar [1][2] == tabChar[2][2] == this.getPlayerChar(player) ||

                        tabChar[0][0] = tabChar[1][1] && tabChar[0][0] = tabChar[2][2] && tabChar[0][0] = this.getPlayerChar(player) ||
                        tabChar[2][0] == tabChar[1][1] && tabChar[2][0] == tabChar[0][2] && tabChar[2][0] = this.getPlayerChar(player);
            }
        return win;*/
}

