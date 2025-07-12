import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;



public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardheight=640;
    int boardwidth=360;

    //Iimage-Section
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    int birdX= boardwidth/8;
    int birdY= boardheight/2;
    int birdWidth= 30;
    int birdHeight= 30;

    class Bird {
        int x=birdX;
        int y=birdY;
        int width=birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img=img;
        }
    }

    //Pipes
    int pipeX=boardwidth;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeigth=512;


    class Pipe {
        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeHeigth;
        Image img;
        boolean passed=false;  //To check wheather the bird has passed or not from the pipe

        Pipe(Image img){
            this.img=img;
        }
    }

    //Game logic

    Bird bird;
    int velocityX=-4; //Moves pipes to the left (stimulates bird moving toward right)
    int velocityY=0; //Moves bird up and down
    int gravity=1; //Bird falls

    ArrayList<Pipe> pipes;
    Random random=new Random();
    



    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver= false;
    double score=0;
    



    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //Load image
        backgroundImg =new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg=new ImageIcon(getClass().getResource("./Flappybird.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
    


        //bird
        bird =new Bird(birdImg);
        pipes =new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            placePipes();
            }
        });

        placePipesTimer.start();

        //game timer
        gameLoop =new Timer(1000/60,this);
        gameLoop.start();


    }

    public void placePipes(){
        int randomPipeY=(int) (pipeY-pipeHeigth/4- Math.random()*(pipeHeigth/2));
        int openingSpace= boardheight/4; 
        Pipe topPipe=new Pipe(topPipeImg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe =new Pipe(bottomPipeImg);
        bottomPipe.y= topPipe.y + pipeHeigth +openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Background
        g.drawImage(backgroundImg,0,0,boardwidth,boardheight,null);
       
        //Bird
        g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);

        //Pipes
        for(int i=0; i<pipes.size();i++){
            Pipe pipe= pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height,null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver) {
            g.drawString("Game Over: "+String.valueOf((int) score),10,35);
        }
        else {
            g.drawString(String.valueOf((int) score),10,35) ;
        }
    }

    public void move(){
        //bird
        velocityY+=gravity;
        bird.y+=velocityY;
        bird.y=Math.max(bird.y,0);

        //pipes
        for(int i=0; i<pipes.size(); i++){
            Pipe pipe=pipes.get(i);
            pipe.x +=velocityX;

            if(!pipe.passed && bird.x >pipe.x + pipe.width){
                pipe.passed=true;
                score+=0.5;

            }

            if(coollision(bird, pipe)){
                gameOver=true;
            }
        }

        if(bird.y>boardheight){
            gameOver=true;
        }

    }

    public boolean coollision(Bird a ,Pipe b){
        return a.x < b.x  + b.width &&
                a.x+ a.width >b.x  &&
                a.y< b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if( gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-9;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
      
    }
}