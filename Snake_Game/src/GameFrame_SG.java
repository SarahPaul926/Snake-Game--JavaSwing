import javax.swing.*;
import java.awt.*;

public class GameFrame_SG extends JFrame
{

    GameFrame_SG()
    {
        Game_Panel_SG game =new Game_Panel_SG();
        this.setLayout(new BorderLayout());
        this.add(game,BorderLayout.CENTER);// Putting the game on the center.

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
