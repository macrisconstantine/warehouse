package warehouse;

// imports for setting the look and feel of the swing app
import com.formdev.flatlaf.*;
import javax.swing.*;

// this class contains the main method that calls the login class to open the app
public class WarehouseApp {
    public static void main (String[] args) {
        /*
         try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Windows look and feel is not available, you can set the GUI to another look and feel.
        }*/
        try {
            // setting look and feel
            //UIManager.setLookAndFeel(new FlatDarculaLaf());
        }
        // in case of failure for any reason, will use default swing gui look and feel
        catch (Exception ignored){}

        // calls login dialog and initiates program
        new LoginDialog();
    }

}
