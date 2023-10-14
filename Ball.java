import java.util.concurrent.ThreadLocalRandom;

public class Ball {
    static final int XSCALE = 1;
    static final int YSCALE = 1;

    static final double GRAVITYPULL = 0.0004905;
    static final double FRICTION = 0.0000004;
    static final double BALLDENSITY = 0.00221043324;
    static final double IMPACTINERTIALOSS = 0.001;

    double ballY;
    double ballX;

    double minSpeed; double maxSpeed;
    double initialRandom;
    int color1; int color2; int color3;
    double ballYvel; double ballXvel;
    double ballSize; double ballMass; double ballEnergyX; double ballEnergyY;
    double ballGravityPull;

    double distanceXcollision; double distanceYcollision; double distancecollision;

    public Ball(double speed){
        minSpeed = -0.01;
        maxSpeed = 0.01;
        ballXvel = speed;
        ballYvel = speed;

        ballSize = 0.08;

        ballMass = (BALLDENSITY * (4/3*Math.PI*Math.pow(ballSize/2, 3)))/1000; // En kilogramme | Densité en g/m3 | taille arbitrairement en m.
        ballEnergyX = 1/2*(ballMass)*Math.pow((ballXvel*100),2); // En Joule | Vitesse en m/s (0.01*100)
        ballEnergyY = 1/2*(ballMass)*Math.pow((ballYvel*100),2); // En Joule | Vitesse en m/s (0.01*100)
        ballGravityPull = ballMass*GRAVITYPULL; // En Newton

        color1 = ThreadLocalRandom.current().nextInt(0, 255);
        color2 = ThreadLocalRandom.current().nextInt(0, 255);
        color3 = ThreadLocalRandom.current().nextInt(0, 255);
        ballY = ThreadLocalRandom.current().nextDouble(-YSCALE+ballSize, YSCALE-ballSize);
        ballX = ThreadLocalRandom.current().nextDouble(-XSCALE+ballSize, XSCALE-ballSize);
    }

    public void checkCollision(){
        if(ballY - ballSize < -YSCALE){
                ballYvel += IMPACTINERTIALOSS;
                ballY = -YSCALE + ballSize;
                ballYvel = -ballYvel;
            }else if(ballY + ballSize > YSCALE){
                ballYvel -= IMPACTINERTIALOSS;
                ballY = YSCALE - ballSize;
                ballYvel = -ballYvel;
            }
        if(ballX - ballSize < -XSCALE){
                ballXvel += IMPACTINERTIALOSS;
                ballX = -XSCALE + ballSize;
                ballXvel = -ballXvel;
            }else if(ballX + ballSize > XSCALE){
                ballXvel -= IMPACTINERTIALOSS;
                ballX = XSCALE - ballSize;
                ballXvel = -ballXvel;
            }
    }

    public void checkCollisionBall(Ball ball1, Ball ball2){
        distanceXcollision = ball1.ballX - ball2.ballX;
        distanceYcollision = ball1.ballY - ball2.ballY;
        distancecollision = Math.abs(distanceXcollision+distanceYcollision);

        if(distancecollision <= ballSize){
            if(distanceYcollision <= ball1.ballY && distanceYcollision >= ball2.ballY){
                ball1.ballYvel += ball2.ballYvel*ball1.ballYvel - IMPACTINERTIALOSS;
                //ball1.ballY = ball2.ballY + ball2.ballSize;
                ball1.ballYvel = -ball1.ballYvel;

                ball2.ballYvel -= ball2.ballYvel*ball1.ballYvel - IMPACTINERTIALOSS;
                ball2.ballYvel = -ball2.ballYvel;

            }else if(distanceYcollision >= ball1.ballY && distanceYcollision <= ball2.ballY){
                ball1.ballYvel -= ball2.ballYvel*ball1.ballYvel - IMPACTINERTIALOSS;
                //ball1.ballY = ball2.ballY - ball2.ballSize;
                ball1.ballYvel = -ball1.ballYvel;

                ball2.ballYvel += ball2.ballYvel*ballYvel - IMPACTINERTIALOSS;
                ball2.ballYvel = -ball2.ballYvel;
            }
            if(distanceXcollision <= ball1.ballX && distanceXcollision >= ball2.ballX){
                ball1.ballXvel += ball2.ballXvel*ball1.ballXvel - IMPACTINERTIALOSS;
                //ball1.ballX = ball2.ballX + ball2.ballSize;
                ball1.ballXvel = -ball1.ballXvel;

                ball2.ballXvel -= ball2.ballXvel*ball1.ballXvel - IMPACTINERTIALOSS;
                ball2.ballXvel = -ball2.ballXvel;

            }else if(distanceXcollision >= ball1.ballX && distanceXcollision <= ball2.ballX){
                ball1.ballXvel -= ball2.ballXvel*ball1.ballXvel - IMPACTINERTIALOSS;
                //ball1.ballX = ball2.ballX - ball2.ballSize;
                ball1.ballXvel = -ball1.ballXvel;

                ball2.ballXvel += ball2.ballXvel*ball1.ballXvel - IMPACTINERTIALOSS;
                ball2.ballXvel = -ball2.ballXvel;
            }

        }
    }

    public void BallUpdate(){
        StdDraw.setPenColor(this.color1, this.color2, this.color3);
        StdDraw.setPenRadius(ballSize);
        if(ballY - ballSize > -YSCALE) ballYvel -= GRAVITYPULL;
        if(ballY - ballSize == -YSCALE && ballXvel > 0) ballXvel -= FRICTION;
        if(ballY - ballSize == -YSCALE && ballXvel < 0) ballXvel += FRICTION;
        ballX += ballXvel; 
        ballY += ballYvel;
        StdDraw.point(ballX, ballY);
    }

    public void jumpBall(){
        ballYvel += ThreadLocalRandom.current().nextDouble(0.0, maxSpeed/2);
    }
}
