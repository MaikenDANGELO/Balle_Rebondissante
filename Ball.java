import java.util.concurrent.ThreadLocalRandom;

public class Ball {
    static final int XSCALE = 1;
    static final int YSCALE = 1;
    static final double GRAVITYPULL = 0.0002;
    static final double FRICTION = 0.0000004;
    static final double IMPACTINERTIALOSS = 0.001;

    double ballY;
    double ballX;

    double minSpeed;
    double maxSpeed;
    double initialRandom;
    int color1; int color2; int color3;
    double ballYvel;
    double ballXvel;
    double ballSize;
    double ballWeight;
    double randomizer1; double randomizer2;

    public Ball(double speed){
        randomizer1 = Math.random();
        randomizer2 = (Math.random()+randomizer1)/2;
        ballY = 0.0;
        ballX = 0.0;
        minSpeed = -0.01;
        maxSpeed = 0.01;
        ballSize = 0.08;
        ballWeight = ballSize*GRAVITYPULL;
        if(randomizer1 < 0.5+randomizer2/2)ballYvel = speed;
        else ballYvel = -speed;
        if(randomizer2 < 0.5+randomizer1/2)ballXvel = speed;
        else ballXvel = -speed;
        color1 = ThreadLocalRandom.current().nextInt(0, 255);
        color2 = ThreadLocalRandom.current().nextInt(0, 255);
        color3 = ThreadLocalRandom.current().nextInt(0, 255);
    }

    public void checkCollision(){
        if(ballY - ballSize < -YSCALE){
                ballYvel += IMPACTINERTIALOSS+ballWeight+ballYvel/1000;
                ballY = -YSCALE + ballSize;
                ballYvel = -ballYvel;
            }else if(ballY + ballSize > YSCALE){
                ballYvel -= IMPACTINERTIALOSS+ballWeight+ballYvel/1000;
                ballY = YSCALE - ballSize;
                ballYvel = -ballYvel;
            }
        if(ballX - ballSize < -XSCALE){
                ballXvel += IMPACTINERTIALOSS+ballWeight+ballXvel/1000;
                ballX = -XSCALE + ballSize;
                ballXvel = -ballXvel;
            }else if(ballX + ballSize > XSCALE){
                ballXvel -= IMPACTINERTIALOSS+ballWeight+ballXvel/1000;
                ballX = XSCALE - ballSize;
                ballXvel = -ballXvel;
            }
    }

    public void BallUpdate(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(ballSize+(ballSize*0.2));
        StdDraw.point(ballX, ballY);
        StdDraw.setPenColor(this.color1, this.color2, this.color3);
        StdDraw.setPenRadius(ballSize);
        if(ballY - ballSize > -YSCALE) ballYvel -= GRAVITYPULL;
        if(ballY - ballSize == -YSCALE && ballXvel > 0) ballXvel -= FRICTION+ballWeight;
        if(ballY - ballSize == -YSCALE && ballXvel < 0) ballXvel += FRICTION+ballWeight;
        ballX += ballXvel; 
        ballY += ballYvel;
        StdDraw.point(ballX, ballY);
    }

    public void jumpBall(){
        ballYvel += ThreadLocalRandom.current().nextDouble(0.0, 0.0005);
    }
}
