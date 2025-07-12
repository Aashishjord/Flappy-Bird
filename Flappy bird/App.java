import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception{
        int boardwidth=360;  //Width of the board
        int boardheight=640;    //Height of the board

        JFrame frame=new JFrame("Flappy Bird");//The freame where all the game run
    
        frame.setSize(boardwidth,boardheight);//Size of the board
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);//      We cannot resize the board size and the height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //it will add the close button and when we click it will clsoe

        FlappyBird flappy =new FlappyBird();
        frame.add(flappy);
        frame.pack();
        flappy.requestFocus();
        frame.setVisible(true);  //It will shows the visiblity 
    
        
             
    }
}

  