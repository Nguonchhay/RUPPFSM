package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FSMDemo {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new DogForm().setVisible(true);
	}

}
