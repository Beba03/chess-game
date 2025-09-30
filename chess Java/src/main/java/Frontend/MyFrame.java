package Frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener {

    JButton Undo = new JButton();

    MyFrame() {
        JPanel p1 = new JPanel();
        p1.setBackground(Color.decode("#302e2b"));
        p1.setPreferredSize(new Dimension(80, 50));

        JPanel p3 = new JPanel();
        p3.setBackground(Color.decode("#302e2b"));
        p3.setPreferredSize(new Dimension(80, 50));

        JPanel p4 = new JPanel();
        p4.setBackground(Color.decode("#302e2b"));
        p4.setPreferredSize(new Dimension(50, 50));

        JPanel p5 = new JPanel();
        p5.setBackground(Color.decode("#302e2b"));
        p5.setPreferredSize(new Dimension(50, 60));

        Undo.setBounds(5, 0, 70, 40);
        Undo.setFocusable(false);
        Undo.addActionListener(this);
        Undo.setBackground(new Color(230, 235, 208));
        Undo.setBorder(BorderFactory.createBevelBorder(0));
        Image btn_Icon = new ImageIcon("Undo.png").getImage();
        ImageIcon icon = new ImageIcon(btn_Icon.getScaledInstance(50, 40, Image.SCALE_SMOOTH));
        Undo.setIcon(icon);
        Undo.setEnabled(false);
        p3.setLayout(null);
        p3.add(Undo);

        this.setTitle("Chess Game");
        this.setLayout(new BorderLayout());
        this.setSize(813, 787);
        this.setBounds(370, 15, 813, 787);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        
        ImageIcon appIcon = new ImageIcon("PiecesImages\\WhiteKing.png");
        this.setIconImage(appIcon.getImage());
        
        this.add(p1, BorderLayout.WEST);
        this.add(p3, BorderLayout.EAST);
        this.add(p4, BorderLayout.NORTH);
        this.add(p5, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Undo) {
            ChessGame.Game.undo();
            if(ChessGame.Game.getMoves().isEmpty())
                Undo.setEnabled(false);                
            ChessGame.Expect_Promotion = false;
            ChessGame.SQUARE_2 = null;
            ChessGame.SQUARE_1 = null;
            ChessGame.SQUARE_1_PRESSED = false;
            ChessGame.validMoves.clear();
            this.repaint();
        }
    }
}
