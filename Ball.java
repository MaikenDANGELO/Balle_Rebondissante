import java.util.concurrent.ThreadLocalRandom;

public class Ball {
    static final int XSCALE = 1;
    static final int YSCALE = 1;

    static final double GRAVITYPULL = 9.81;
    static final double FRICTION = 0.0000004;
    static final double IMPACTINERTIALOSS = 0.001;
    static final double BALLDENSITY = 0.00221043324;

    double ballY;
    double ballX;

    double minSpeed; double maxSpeed;
    double initialRandom;
    int color1; int color2; int color3;
    double ballYvel; double ballXvel;
    double ballSize; double ballMass; double ballEnergy;
    double ballGravityPull;

    public Ball(double speed){
        minSpeed = -0.01;
        maxSpeed = 0.01;
        ballXvel = speed;
        ballYvel = speed;

        ballSize = 0.08;
        ballMass = (BALLDENSITY * (4/3*Math.PI*Math.pow(ballSize/2, 3)))/1000; // En kilogramme | Densité en g/m3 | taille arbitrairement en m.
        ballEnergy = 0.5* (ballMass)*Math.pow(ballXvel*100+ballYvel*100,2); // En Joule | Vitesse en m/s (0.01*100)
        ballGravityPull = (ballMass)*GRAVITYPULL; // En Newton

        color1 = ThreadLocalRandom.current().nextInt(0, 255);
        color2 = ThreadLocalRandom.current().nextInt(0, 255);
        color3 = ThreadLocalRandom.current().nextInt(0, 255);
        ballY = ThreadLocalRandom.current().nextDouble(-YSCALE+ballSize, YSCALE-ballSize);
        ballX = ThreadLocalRandom.current().nextDouble(-XSCALE+ballSize, XSCALE-ballSize);
    }

    public void checkCollision(){
        if(ballY - ballSize < -YSCALE){
                ballYvel += IMPACTINERTIALOSS+ballYvel/1000;
                ballY = -YSCALE + ballSize;
                ballYvel = -ballYvel;
            }else if(ballY + ballSize > YSCALE){
                ballYvel -= IMPACTINERTIALOSS+ballYvel/1000;
                ballY = YSCALE - ballSize;
                ballYvel = -ballYvel;
            }
        if(ballX - ballSize < -XSCALE){
                ballXvel += IMPACTINERTIALOSS+ballXvel/1000;
                ballX = -XSCALE + ballSize;
                ballXvel = -ballXvel;
            }else if(ballX + ballSize > XSCALE){
                ballXvel -= IMPACTINERTIALOSS+ballXvel/1000;
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
        if(ballY - ballSize == -YSCALE && ballXvel > 0) ballXvel -= FRICTION;
        if(ballY - ballSize == -YSCALE && ballXvel < 0) ballXvel += FRICTION;
        ballX += ballXvel; 
        ballY += ballYvel-ballGravityPull;
        StdDraw.point(ballX, ballY);
    }

    public void jumpBall(){
        ballYvel += ThreadLocalRandom.current().nextDouble(0.0, 0.0005);
    }
}
