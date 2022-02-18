package Main.Moveables;

import Main.OriginalLevel;
import Main.SpriteAnimation;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

@Setter
@Getter

public class MoveableImgView extends ImageView {
    protected int spriteOffsetY;
    protected int width;
    protected int height;
    protected int speed;
    protected int SCALE;
    protected SpriteAnimation animation;
    public static ArrayList<Point3D> horizontalPaths;
    public static ArrayList<Point3D> verticalPaths;

    public MoveableImgView(String path, int scale, int offsetY, int width, int height, int speed) {
        super(path);
        this.spriteOffsetY = offsetY;
        this.SCALE = scale;
        this.speed = speed;
        this.width = width;
        this.height = height;

        //Centering and sizing image
        setFitHeight(height);
        setFitWidth(width);
        setLayoutX(-getFitWidth()/2);
        setLayoutY(-getFitHeight()/2);
    }


    //    Just movement
    protected void moveLeft(){
        if(canMove(KeyCode.A)){
            if(getTranslateX() == 0) {
                setTranslateX(OriginalLevel.WIDTH-SCALE);
                return;
            }
            setTranslateX(getTranslateX() - speed);
        }
    }
    protected void moveRight(){
        if(canMove(KeyCode.D)) {
            if((int)getTranslateX() == OriginalLevel.WIDTH-SCALE) {
                setTranslateX(speed);
                return;
            }
            setTranslateX(getTranslateX() + speed);
        }
    }
    protected void moveUp(){
        if(canMove(KeyCode.W)) {
            setTranslateY(getTranslateY() - speed);
        }
    }
    protected void moveDown(){
        if(canMove(KeyCode.S)){
            setTranslateY(getTranslateY() + speed);
        }
    }

    protected boolean canMove( KeyCode key){
        if (key == KeyCode.A)
            for (Point3D point : horizontalPaths) {
                if(getTranslateY() == point.getY()) {
                    if(getTranslateX() == 0)
                        return true;
                    if(getTranslateX() >= point.getX()+speed && getTranslateX()<= point.getX()+point.getZ()) {
                        return true;
                    }
                }
            }
        if(key == KeyCode.D)
            for (Point3D point : horizontalPaths) {
                if(getTranslateY() == point.getY()){
                    if(getTranslateX() == OriginalLevel.WIDTH-SCALE) {
                        return true;
                    }
                    if(getTranslateX()>= point.getX() && getTranslateX()<=point.getX()+point.getZ()-speed ){
                        return true;
                    }
                }
            }

        if(key == KeyCode.W)
            for (Point3D point : verticalPaths) {
                if(getTranslateX() == point.getX()){
                    if(getTranslateY()>= point.getY()+speed && getTranslateY()<=point.getY()+point.getZ()){
                        return true;
                    }
                }
            }

        if(key == KeyCode.S)
            for (Point3D point : verticalPaths) {
                if(getTranslateX() == point.getX()) {
                    if(getTranslateY()>= point.getY() && getTranslateY()<=point.getY()+point.getZ()-speed ) {
                        return true;
                    }
                }
            }
        return false;
    }

    protected void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                "    Point: "+ (int)pointX+", "+(int)pointY);
    }


}
