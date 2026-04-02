import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Graphics;

public class Game_Panel_SG extends JPanel implements ActionListener
{
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;// size of our apple and snake and the grid; basically the small grid boxes
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int TOP_SPACE = 60;
    static final int DELAY=70;
    final int x[]=new int[GAME_UNITS];// movements of the snake works in an array
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;//bodyparts of the snake that makes it move
    int applesEaten;
    private Image  titleImage;
    boolean paused= false;
    boolean finished=false;
    int appleX;// x coordinate of the apple
    int appleY;// y coordinate of the apple
    char direction= 'R';
    boolean running=false;
    Timer timer;
    Random random;
    Image appleImg;
    JButton restart,pause;


    Game_Panel_SG()
    {
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyApapter());
        appleImg= new ImageIcon(getClass().getResource("/RedApple.png")).getImage();
        startGame();
    }

    public void startGame()
    {
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void drawSnakeHead(Integer i,Graphics g){
        g.setColor(Color.blue);
        g.fillRect(x[i],y[i]+TOP_SPACE,UNIT_SIZE,UNIT_SIZE);//head

        g.setColor(Color.WHITE);
        g.fillRect(x[i]+5,y[i]+5+TOP_SPACE,7,7);
        g.fillRect(x[i]+14,y[i]+5+TOP_SPACE,7,7);

    }
    public void draw(Graphics g)
    {
        //Title panel
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),60);

        g.setColor(Color.BLACK);
        g.drawRect(0,0,getWidth(),60);

        titleImage=new ImageIcon(getClass().getResource("/SNAKEGAME.png")).getImage();
        g.drawImage(titleImage, 30, 10, 300, 40, this);
        if(running)
        {
            for(int x=0;x<SCREEN_HEIGHT/UNIT_SIZE;x++)// this is to draw the graph as the background
            {
                for (int y = 0; y <SCREEN_HEIGHT/UNIT_SIZE ; y++) {
                    if((x+y)%2==0) {
                        g.setColor(new Color(32, 104, 57));
                    }
                    else {
                        g.setColor(new Color(41, 156, 82));
                    }
                    g.fillRect(x*UNIT_SIZE,y*UNIT_SIZE+TOP_SPACE,UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.drawImage(appleImg, appleX,appleY+TOP_SPACE,UNIT_SIZE,UNIT_SIZE, this);// Apple image

            // the snake drawing
            for (int i=0; i<bodyParts;i++) // bodyparts=6
            {
                if(i==0)// head
                {
                    drawSnakeHead(i,g);
                }
                else // rest of the parts
                {
                    g.setColor(new Color(0, 99, 180));
                    g.fillRect(x[i],y[i]+TOP_SPACE,UNIT_SIZE,UNIT_SIZE);
                }
            }
        }
        else  gameOver(g);

        g.setColor(Color.blue);
        g.setFont(new Font("Ink free",Font.BOLD,20));
        //g.drawString("SNAKE GAME", 200, 40);

        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Score"+applesEaten,(SCREEN_WIDTH+metrics.stringWidth("Score"+applesEaten))/2+150,40);
    }

    public void newApple()
    {
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)((SCREEN_HEIGHT-TOP_SPACE)/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move()
    {
        for(int i=bodyParts;i>0;i--)// moving the bodyParts makes the snake move
        {
            x[i]= x[i-1];
            y[i]= y[i-1];
        }
        switch(direction)// u are controling the movemnet by controlling the head
        //x increases → when you move to the right

        //y increases ↓ when you move down

        //y decreases ↑ when you move up
        {
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }
    }

    public void checkApple()
    {
        if((x[0]==appleX)&&(y[0]==appleY))
        {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisons()
    {
        // this checks if the head collides with the body
        for(int i= bodyParts;i>0;i--)
        {
            if ((x[0]==x[i])&&(y[0]==y[i]))
            {
                running=false;
            }
        }
        //check if the head touchs left border
        if(x[0]<0)
        {
            running=false;
        }
        //checks if the head touchs right border
        if(x[0]>SCREEN_WIDTH)
        {
            running=false;
        }
        // checks if the head touchs top border
        if(y[0]<0)
        {
            running=false;
        }
        // checks if the head touchs bottom border
        if(y[0]>SCREEN_HEIGHT)
        {
            running=false;
        }
        //check if the head touchs left border
        if(x[0]<0)
        {
            move();
        }

        if(!running)
        {
            timer.stop();
        }
    }

    public void gameOver(Graphics g)
    {
        // Game over text
        setBackground(Color.gray);
        g.setColor(Color.black);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
        g.setFont(new Font("Ink free",Font.PLAIN,30));
        g.drawString("Restart Press R",180,SCREEN_HEIGHT/2+100);
        // THIS BASICALLY PUTS THE "GAME OVER" IN THE CENTER OF THE PAGE
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (running && !paused)
        {
            move();
            checkApple();
            checkCollisons();
        }
        repaint();
    }
    public class MyKeyApapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyCode()==KeyEvent.VK_R){
                setVisible(false);
                new GameFrame_SG();
            }
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                paused=!paused;
            }
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if (direction!='R')
                    {
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction!='L')
                    {
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction!='D')
                    {
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction!='U')
                    {
                        direction='D';
                    }
                    break;
            }
        }
    }
}
