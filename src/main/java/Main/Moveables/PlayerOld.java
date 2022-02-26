//package Main.Moveables;
//
//import Main.panes.OriginalLevel;
//import Main.SpriteAnimation;
//import javafx.animation.Animation;
//import javafx.geometry.Point2D;
//import javafx.geometry.Point3D;
//import javafx.scene.input.KeyCode;
//import javafx.scene.media.AudioClip;
//import javafx.scene.media.MediaPlayer;
//import javafx.util.Duration;
//import lombok.Setter;
//
//import java.util.Deque;
//import java.util.Objects;
//
//public class PlayerOld extends MoveableImgView{
//    private static final String spritePath = "Images/PlayerSprite.png";
//    private AudioClip clip;
//    private Point2D gridPos;
//
//    public PlayerOld(int scale) {
//        super(spritePath, scale, 18,32, 32, 1);
//
//        clip = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/wakawakabearfast.mp3")).toString());
//        clip.setCycleCount(MediaPlayer.INDEFINITE);
//        clip.setVolume(.2);
//
//        //Center pos is x96 y92
////        setTranslateX(96*SCALE);
////        setTranslateY(188*SCALE);
//        //Centering and sizing image
//        setAnimation(new SpriteAnimation(this, 3,0,0,18, width, height, Duration.millis(100)));
//
//    }
//
//    public void move(Deque<KeyCode> moveQueue){
//        comparePosition(98*3,188*3);
//        if(moveQueue.size()==2) {
//            switch (moveQueue.peekLast()) {
//                case A -> {
//                    if(moveLeft(moveQueue)){
//                        if(!clip.isPlaying())
//                            clip.play();
//                        return;
//                    }
//                }
//                case D -> {
//                    if(moveRight(moveQueue)){
//                        if(!clip.isPlaying())
//                            clip.play();
//                        return;
//                    }
//                }
//                case W -> {
//                    if(moveUp(moveQueue)){
//                        if(!clip.isPlaying())
//                            clip.play();
//                        return;
//                    }
//                }
//                case S -> {
//                    if(moveDown(moveQueue)){
//                        if(!clip.isPlaying())
//                            clip.play();
//                        return;
//                    }
//                }
//            }
//        }
//
//        switch (Objects.requireNonNull(moveQueue.peek())){
//            case A -> {
//                if(!clip.isPlaying())
//                    clip.play();
//                moveLeft();
//            }
//            case D -> {
//                if(!clip.isPlaying())
//                    clip.play();
//                moveRight();
//            }
//            case W -> {
//                if(!clip.isPlaying())
//                    clip.play();
//                moveUp();
//            }
//            case S -> {
//                if(!clip.isPlaying())
//                    clip.play();
//                moveDown();
//            }
//        }
//    }
//
//    //    Movement for queued key
//    private boolean moveLeft(Deque<KeyCode> moveQueue){
//        if(canMove(moveQueue.peekLast())){
//            moveLeft();
//            moveQueue.removeFirst();
//            return true;
//        }
//        return false;
//    }
//    private boolean moveRight(Deque<KeyCode> moveQueue){
//        if(canMove(moveQueue.peekLast())){
//            moveRight();
//            moveQueue.removeFirst();
//            return true;
//        }
//        return false;
//    }
//    private boolean  moveUp(Deque<KeyCode> moveQueue){
//        if(canMove(moveQueue.peekLast())){
//            moveUp();
//            moveQueue.removeFirst();
//            return true;
//        }
//        return false;
//    }
//    private boolean moveDown(Deque<KeyCode> moveQueue){
//        if(canMove(moveQueue.peekLast())){
//            moveQueue.removeFirst();
//            moveDown();
//            return true;
//        }
//        return false;
//    }
//
//    //    Just movement
//    @Override
//    public void moveLeft(){
//        if(canMove(KeyCode.A)){
//            offsetAndStartAnimation(300);
//            if(getTranslateX() == 0) {
//                setTranslateX(OriginalLevel.WIDTH);
//                return;
//            }
//            setTranslateX(getTranslateX() - speed);
//            return;
//        }
//        stopPlayer();
//    }
//    @Override
//    public void moveRight(){
//        if(canMove(KeyCode.D)) {
//            offsetAndStartAnimation(0);
//            if((int)getTranslateX() == OriginalLevel.WIDTH-SCALE) {
//                setTranslateX(speed);
//                return;
//            }
//            setTranslateX(getTranslateX() + speed);
//            return;
//        }
//        stopPlayer();
//    }
//    @Override
//    public void moveUp(){
//        if(canMove(KeyCode.W)) {
//            offsetAndStartAnimation(448);
//            setTranslateY(getTranslateY() - speed);
//            return;
//        }
//        stopPlayer();
//    }
//    @Override
//    public void moveDown(){
//        if(canMove(KeyCode.S)){
//            offsetAndStartAnimation(150);
//            setTranslateY(getTranslateY() + speed);
//            return;
//        }
//        stopPlayer();
//    }
//
//
//    public boolean canMove( KeyCode key){
//        if (key == KeyCode.A)
//            for (Point3D point : horizontalPaths) {
//                if(getTranslateY() == point.getY()) {
//                    if(getTranslateX() == 0)
//                        return true;
//                    if(getTranslateX() >= point.getX()+ speed && getTranslateX()<= point.getX()+point.getZ()) {
//                        return true;
//                    }
//                }
//            }
//        if(key == KeyCode.D)
//            for (Point3D point : horizontalPaths) {
//                if(getTranslateY() == point.getY()){
//                    if(getTranslateX() == OriginalLevel.WIDTH-SCALE) {
//                        return true;
//                    }
//                    if(getTranslateX()>= point.getX() && getTranslateX()<=point.getX()+point.getZ()- speed){
//                        return true;
//                    }
//                }
//            }
//
//        if(key == KeyCode.W)
//            for (Point3D point : verticalPaths) {
//                if(getTranslateX() == point.getX()){
//                    if(getTranslateY()>= point.getY()+ speed && getTranslateY()<=point.getY()+point.getZ()){
//                        return true;
//                    }
//                }
//            }
//
//        if(key == KeyCode.S)
//            for (Point3D point : verticalPaths) {
//                if(getTranslateX() == point.getX()) {
//                    if(getTranslateY()>= point.getY() && getTranslateY()<=point.getY()+point.getZ()- speed) {
//                        return true;
//                    }
//                }
//            }
//        return false;
//    }
//
//    public void startAnimation(SpriteAnimation animation){
//        if(animation.getStatus() == Animation.Status.RUNNING)
//            return;
//
//        animation.playFromStart();
//    }
//
//    public void offsetAndStartAnimation(int offsetY){
//        if(animation.getOffsetY() !=offsetY)
//            animation.setOffsetY(offsetY);
//        if(animation.getStatus()!= Animation.Status.RUNNING)
//            startAnimation(animation);
//    }
//
//    public void comparePosition(double pointX, double pointY){
//        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
//                "Point: "+ (int)pointX+", "+(int)pointY);
//    }
//
//    public void stopPlayer(){
//        animation.setMidFrame();
//        animation.pause();
//        clip.stop();
//    }
//}
