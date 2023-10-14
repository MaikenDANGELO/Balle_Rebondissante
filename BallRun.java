import java.util.concurrent.ThreadLocalRandom;

public class BallRun {
    public static void main(String[] args){
        StdDraw.enableDoubleBuffering();
        final double minspeed = -0.015;
        final double maxspeed = 0.015;
        final int BALLNUMBER = 10;

        StdDraw.setXscale(-Ball.XSCALE,Ball.XSCALE);
        StdDraw.setYscale(-Ball.YSCALE,Ball.YSCALE);
        Ball[] balls = new Ball[BALLNUMBER];
        for(int i = 0; i < balls.length; i++){
            balls[i] = new Ball(ThreadLocalRandom.current().nextDouble(minspeed, maxspeed));
        }
        while(true){
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(0, 0, 1, 1);
            for(Ball ball : balls){
                ball.checkCollision();
                /*for(Ball ball2 : balls){
                    ball.checkCollisionBall(ball, ball2);
                    ball.checkCollisionBall(ball2, ball);
                }*/
                ball.BallUpdate();
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
