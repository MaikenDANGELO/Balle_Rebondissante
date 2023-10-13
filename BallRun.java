import java.util.concurrent.ThreadLocalRandom;

public class BallRun {
    public static void main(String[] args){
        StdDraw.enableDoubleBuffering();
        final double minspeed = -0.015;
        final double maxspeed = 0.015;

        StdDraw.setXscale(-Ball.XSCALE,Ball.XSCALE);
        StdDraw.setYscale(-Ball.YSCALE,Ball.YSCALE);
        Ball[] balls = new Ball[50];
        for(int i = 0; i < balls.length; i++){
            balls[i] = new Ball(ThreadLocalRandom.current().nextDouble(minspeed, maxspeed));
        }
        while(true){
            for(Ball ball : balls){
                ball.BallUpdate();
                ball.checkCollision();
            }
            if(StdDraw.isMousePressed()){
                for(Ball ball : balls){
                    ball.jumpBall();
                }
            }
            StdDraw.show();
        }
    }
}
