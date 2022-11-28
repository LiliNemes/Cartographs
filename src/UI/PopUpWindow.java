package UI;
import javax.swing.*;

public class PopUpWindow {
    JFrame jFrame;

    public String getName() {
        return name;
    }

    String name;

    public PopUpWindow(String s) {
        jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, s);
    }

    public PopUpWindow() {
        String getMessage = JOptionPane.showInputDialog(jFrame, "Enter your name");
        JOptionPane.showMessageDialog(jFrame, "Your name: "+getMessage);
        name=getMessage;
    }
}
