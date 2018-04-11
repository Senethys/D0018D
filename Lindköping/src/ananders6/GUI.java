package ananders6;

import javax.swing.JFrame;
public class GUI
{
 public static void main(String[] args)
 {
 final int FRAME_WIDTH = 300;
 final int FRAME_HEIGHT = 400;

 JFrame frame = new JFrame(); // 1
 frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); // 2
 frame.setTitle("Ett enkelt fönster"); // 3
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 4
 frame.setVisible(true); // 5
 }
}