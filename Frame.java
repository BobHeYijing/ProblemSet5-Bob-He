package doublesnake;

import javax.swing.JFrame;

public class Frame extends JFrame {

    // Create the basic setting such as background scale, game tile, and add RenderPanel, and so the Frame help to set the drawing correctly and precisely.
    public Frame() {
        add(new RenderPanel());
        setTitle("Double Snake");
        setVisible(true);
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}