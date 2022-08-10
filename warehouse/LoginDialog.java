package warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Login dialog is he first GUI to run when program is initiated. It displays login and registration tabs,
 * and it calls the MainFrame class upon successful login.
 */
public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton login;
    private JButton register;
    private JTextField user;
    private JPasswordField pass;
    private JTextField firstname;
    private JTextField lastname;
    private JTextField email;
    private JTextField phone;
    private JTextField regUser;
    private JTextField regPass;

    // static because it must belong to the class to be called by other classes
    protected static Login log = new Login();

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        setSize(480,420);
        //Image frameImage = new ImageIcon("img.jpg").getImage();
        setTitle("Login");
        //setIconImage(frameImage);
        setLocationRelativeTo(null);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // when login button is pressed, text from the username text field and password text field is
        // checked with "database" in login class for validation.
        login.addActionListener(e -> {
            String username = user.getText();
            String password = new String(pass.getPassword());

            // if password and username are not null, both are plugged into the login class and
            // the credentials are checked
            if(!Objects.equals(username, "") && !Objects.equals(password, ""))
            {
                log.setUsername(username);
                log.setPassword(password);

                // if check credential function returns true, the current user is set, window is disposed,
                // and main frame is called
                if (log.checkCredentials())
                {
                    log.setCurrUser(username);
                    dispose();
                    new MainFrame();
                }
                else
                {
                    // if fields are non-null but also not valid, appropriate error message is displayed
                    JOptionPane.showMessageDialog(null, "Incorrect username or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                // if any fields are null, appropriate error message is displayed
                JOptionPane.showMessageDialog(null, "Please ensure all fields are filled.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // register button creates a new user and saves the login dialog to a file
        register.addActionListener(e -> {
            // if any field is empty, error message is issued
            if(Objects.equals(firstname.getText(), "") || Objects.equals(lastname.getText(), "")
                    || Objects.equals(email.getText(), "") || Objects.equals(phone.getText(), "")
                    || Objects.equals(regUser.getText(), "") || Objects.equals(regPass.getText(), ""))
            {
                JOptionPane.showMessageDialog(null, "Please ensure all fields are filled.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                // otherwise, a new user is created with the given information
                User newUser = new User(regUser.getText(), regPass.getText(),
                        firstname.getText(), lastname.getText(), email.getText(), phone.getText());
                try{
                    // the new user is then added to the list of users in Login class,
                    // and the state of Login class saved
                    log.addUsers(newUser);
                    save();

                    // informs user of successful registration
                    JOptionPane.showMessageDialog(null, firstname.getText()+
                                    " "+lastname.getText()+" registered successfully!",
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (WarehouseException ex)
                {
                    // if user info already exists in system, user is notified
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // tries to load from a file upon LoginDialog construction
        try{
            load();
        }
        catch (Exception ignore){}
        setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Save function does not call file chooser in this class, save is done automatically.
     */
    private void save(){
        try {
            FileOutputStream fileOut = new FileOutputStream("login.sav");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(log);
            out.close();
            fileOut.close();
            System.out.println("Saved in : login.sav");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load function does not call file chooser in this class, it is also done automatically.
     */
    private void load() {
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("login.sav");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            log = (Login)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("Login class not found");
            c.printStackTrace();
        }
    }
/*
    // custom create UI component used to add logo to login dialog
    private void createUIComponents() {
        picture = new JLabel(new ImageIcon("img1.png"));
    }*/


}
