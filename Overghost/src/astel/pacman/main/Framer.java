package astel.pacman.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Framer extends JFrame{
	GamePanel label;
    private Canvas canvas;
	
    public Framer() {
	init();
    }
    
    private void init() {
    	

    label = new GamePanel(this);
	
	getContentPane().add(label);
	add(label);
	pack();
	setSize(353,600);
    
	setVisible(true);
	setLocationRelativeTo(null);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Pac Man");
	addKeyListener(label);
	setIconImage(new ImageIcon("C:\\Users\\nachv\\Desktop\\PacMan\\res\\cherryicon.png").getImage());
	canvas = new Canvas();
	canvas.setPreferredSize(new Dimension(353, 600));
	canvas.setMaximumSize(new Dimension(353, 600));
	canvas.setMinimumSize(new Dimension(353, 600));
	canvas.setFocusable(false);
	
	
	add(canvas);
	
	label.start();
	
    }
}
