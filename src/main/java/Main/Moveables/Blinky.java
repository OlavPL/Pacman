package Main.Moveables;

import Main.panes.OriginalLevel;

public class Blinky extends Ghost{
    static int N;
    public Blinky(int scale, int speed, Player player, OriginalLevel parent) {
        super("/Images/RedGhostSprite.png", scale, speed, player, parent);


    }



}
