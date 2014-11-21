package application;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class openfile extends JFrame {

	public openfile() {
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}
		pnButton = new JPanel();
		add(pnButton, BorderLayout.WEST);
		btnOpen = new JButton("Open");
		pnButton.add(btnOpen);
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					open();
				} catch (Exception ex) {
				}
			}
		});
	}

	private void open() throws Exception {
		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			   System.out.println(f.getAbsolutePath());			 
		}
	}
	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new openfile().setVisible(true);
			}
		});
	}
	private JButton btnOpen;
	private JPanel pnButton;
}