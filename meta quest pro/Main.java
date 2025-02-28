package javiergs;

import javax.swing.*;

public class Main extends JFrame {
	
	public Main() {
		MQTTPanel panel = new MQTTPanel();
		add(panel);
	}
	
	public static void main(String[] args) {
    Main frame = new Main();
    frame.setTitle("Eyes, Hands, and Object Visualizer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1024, 768);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
	}
	
}

