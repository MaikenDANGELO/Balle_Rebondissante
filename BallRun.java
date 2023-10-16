import java.util.concurrent.ThreadLocalRandom;

public class BallRun {
    public static void main(String[] args){
        StdDraw.enableDoubleBuffering();
        final double minspeed = -0.015;
        final double maxspeed = 0.015;
        final int BALLNUMBER = 175;

        StdDraw.setXscale(-Ball.XSCALE,Ball.XSCALE);
        StdDraw.setYscale(-Ball.YSCALE,Ball.YSCALE);
        Ball[] balls = new Ball[BALLNUMBER];
        for(int i = 0; i < balls.length; i++){
            balls[i] = new Ball(ThreadLocalRandom.current().nextDouble(minspeed, maxspeed));
        }
        while(true){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0, 0, Ball.XSCALE, Ball.YSCALE);

            for(int i = 0; i < balls.length; i++){
                balls[i].checkCollision();
                for(int j = i+1; j < balls.length; j++){
                    balls[i].checkCollisionBall(balls[i], balls[j]);
                }
                balls[i].BallUpdate();
            }

            if(StdDraw.isMousePressed()){
                for(Ball ball : balls){
                    ball.jumpBall();
                }
            }
            StdDraw.pause(25);
            StdDraw.show();
        }
    }
}
