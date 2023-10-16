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
    double ballSize;
    double ballMass; 
    double ballEnergyX; 
    double ballEnergyY;
    // double ballGravityPull;

    double distanceXcollision; double distanceYcollision; double distancecollision;
    double midpointx; double midpointy;
    double vectorNormX; double vectorNormY; double p; double w;

    public Ball(double speed){
        minSpeed = -0.01;
        maxSpeed = 0.01;
        ballXvel = speed;
        ballYvel = speed;

        ballSize = 0.005;

        ballMass = (BALLDENSITY * (4/3*Math.PI*Math.pow(ballSize/2, 3))); // En gramme | Densité en g/m3 | taille arbitrairement en m.
        // ballEnergyX = 1/2*(ballMass)*Math.pow((ballXvel*100),2); // En Joule | Vitesse en m/s (0.01*100)
        // ballEnergyY = 1/2*(ballMass)*Math.pow((ballYvel*100),2); // En Joule | Vitesse en m/s (0.01*100)
        // ballGravityPull = ballMass*GRAVITYPULL; // En Newton

        color1 = ThreadLocalRandom.current().nextInt(128, 255);
        color2 = ThreadLocalRandom.current().nextInt(128, 255);
        color3 = ThreadLocalRandom.current().nextInt(128, 255);

        ballY = ThreadLocalRandom.current().nextDouble(-YSCALE+ballSize, YSCALE-ballSize);
        ballX = ThreadLocalRandom.current().nextDouble(-XSCALE+ballSize, XSCALE-ballSize);
    }

    public void checkCollision(){
        if(ballY - ballSize < -YSCALE){
            if(ballYvel+IMPACTINERTIALOSS > 0){
                ballYvel = 0;
            }else{
                ballYvel += IMPACTINERTIALOSS;
                ballY = -YSCALE + ballSize;
                ballYvel = -ballYvel;
            }
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
        distancecollision = Math.sqrt(Math.pow(distanceXcollision,2.0)+Math.pow(distanceYcollision,2.0));

        if(Math.pow(distancecollision,2) <= Math.pow(ballSize*2,2)){
            midpointx = (ball1.ballX + ball2.ballX) / 2.0;
            midpointy = (ball1.ballY + ball2.ballY) / 2.0;
            if(distancecollision != 0){
                ball1.ballX = midpointx + ballSize * (ball1.ballX - ball2.ballX) / distancecollision;
                ball1.ballY = midpointy + ballSize * (ball1.ballY - ball2.ballY) / distancecollision;
                ball2.ballX = midpointx + ballSize * (ball2.ballX - ball1.ballX) / distancecollision;
                ball2.ballY = midpointy + ballSize * (ball2.ballY - ball1.ballY) / distancecollision;
                
                vectorNormX = (ball2.ballX - ball1.ballX) / distancecollision;
                vectorNormY = (ball2.ballY - ball1.ballY) / distancecollision;
            }else{
                ball1.ballX = midpointx + ballSize * (ball1.ballX - ball2.ballX) / 0.0000000001;
                ball1.ballY = midpointy + ballSize * (ball1.ballY - ball2.ballY) / 0.0000000001;
                ball2.ballX = midpointx + ballSize * (ball2.ballX - ball1.ballX) / 0.0000000001;
                ball2.ballY = midpointy + ballSize * (ball2.ballY - ball1.ballY) / 0.0000000001;
                
                vectorNormX = (ball2.ballX - ball1.ballX) / 0.0000000001;
                vectorNormY = (ball2.ballY - ball1.ballY) / 0.0000000001;
            }

            
            p = (2*(ball1.ballXvel * vectorNormX + ball1.ballYvel * vectorNormY - ball2.ballXvel * vectorNormX - ball2.ballYvel * vectorNormY)) / (ball1.ballMass+ball2.ballMass);

            ball1.ballXvel = (ball1.ballXvel - p * ball1.ballMass * vectorNormX) / 2;
            ball1.ballYvel = (ball1.ballYvel - p * ball1.ballMass * vectorNormY) / 2;
            ball2.ballXvel = (ball2.ballXvel + p * ball1.ballMass * vectorNormX) / 2;
            ball2.ballYvel = (ball2.ballYvel + p * ball1.ballMass * vectorNormY) / 2;

        }
    }

    public void BallUpdate(){
        StdDraw.setPenColor(this.color1, this.color2, this.color3);
        StdDraw.setPenRadius(ballSize);
        //if(ballY - ballSize > -YSCALE) ballYvel -= GRAVITYPULL;
        if(ballY - ballSize == -YSCALE && ballXvel > 0) ballXvel -= FRICTION;
        if(ballY - ballSize == -YSCALE && ballXvel < 0) ballXvel += FRICTION;



        if(ballXvel > maxSpeed){
                ballXvel = maxSpeed;
            }else if(ballXvel < -maxSpeed){
                ballXvel = -maxSpeed;
            }
        if(ballYvel > maxSpeed){
                ballYvel = maxSpeed;
            }else if(ballYvel < -maxSpeed){
                ballYvel = -maxSpeed;
            }

        ballX += ballXvel; 
        ballY += ballYvel;
        try{
            StdDraw.point(ballX, ballY);
        }catch(IllegalArgumentException e){
            System.out.println("/!\\ Erreur rencontrée /!\\");
            System.out.println("X = " + ballX + " Y= " + ballY);
            System.out.println("VitesseX = " + ballXvel + " VitesseY = " + ballYvel);
            System.out.println("Distance collision= "+distancecollision);
            System.out.println("MidpointX = " + midpointx + " MidpointY = " + midpointy);
            System.out.println("VectorNormX = " + vectorNormX + " VectorNormY = " + vectorNormY);
            System.out.println("P = " + p);
            System.out.println("Masse = " + ballMass);

            ballX = ThreadLocalRandom.current().nextDouble(-YSCALE+ballSize, YSCALE-ballSize);
            ballY = ThreadLocalRandom.current().nextDouble(-YSCALE+ballSize, YSCALE-ballSize);
            StdDraw.point(ballX, ballY);
        }
        
    }

    public void jumpBall(){
        ballYvel += ThreadLocalRandom.current().nextDouble(0.0, maxSpeed/2);
    }
}
