package juegos.server.space;

import juegos.common.CommandType;
import juegos.server.ServerPlayer;

public class TTTServerSpace extends ServerSpace
{
    private char[][] tabChar;

    public TTTServerSpace() {
        super(ServerSpaceType.TIC_TAC_TOE);
    }

    @Override
    public boolean canAccept(ServerPlayer player) {
        return true;
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
            this.sendCommand(player, "get",String.valueOf(this.tabChar[lig][col]));
        }
    }
}
