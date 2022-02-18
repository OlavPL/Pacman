package Main.Moveables;

public class Blinky extends Ghost{
    public Blinky(int scale, int speed, Player player) {
        super("/Images/RedGhostSprite.png", scale, speed, player);

        //Center pos is x96 y92
        setTranslateX(96*scale);
        setTranslateY(92*scale);
    }
}
