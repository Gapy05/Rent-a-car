import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel username;
    private JTextField tusername;
    private JLabel password;
    private JPasswordField tpassword;
    private JButton login;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JButton register;

    public Login() {
        setTitle("Prijava");
        setBounds(300, 90, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Prijava");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(175, 30);
        c.add(title);

        username = new JLabel("Uporabniško ime");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setSize(150, 20);
        username.setLocation(50, 100);
        c.add(username);

        tusername = new JTextField();
        tusername.setFont(new Font("Arial", Font.PLAIN, 15));
        tusername.setSize(190, 20);
        tusername.setLocation(200, 100);
        c.add(tusername);

        password = new JLabel("Geslo");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100, 20);
        password.setLocation(50, 130);
        c.add(password);

        tpassword = new JPasswordField();
        tpassword.setFont(new Font("Arial", Font.PLAIN, 15));
        tpassword.setSize(190, 20);
        tpassword.setLocation(200, 130);
        c.add(tpassword);

        login = new JButton("Prijava");
        login.setFont(new Font("Arial", Font.PLAIN, 15));
        login.setSize(100, 20);
        login.setLocation(125, 180);
        login.addActionListener(this);
        c.add(login);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(225, 180);
        reset.addActionListener(this);
        c.add(reset);


        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(300, 25);
        res.setLocation(50, 150);
        c.add(res);

        register = new JButton("Registracija");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setSize(150, 20);
        register.setLocation(150, 210);
        register.addActionListener(this);
        c.add(register);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String username = tusername.getText();
            String password = String.valueOf(tpassword.getPassword());

            try {
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();
                // Include "username" in your SQL query
                String sql = "SELECT id, username FROM uporabnik WHERE username = ? AND geslo = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, PasswordHash.hashPassword(password));
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("username");

                    if (userId == 5) {
                        adminrent adminrent = new adminrent();
                        this.setVisible(false);
                    } else  {
                        rent rentPage = new rent();
                        this.setVisible(false);
                    }
                } else {
                    res.setText("Napačno uporabniško ime ali geslo!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                res.setText("Napaka pri prijavi!");
            }

        } else if (e.getSource() == reset) {
            String def = "";
            tusername.setText(def);
            tpassword.setText(def);
            res.setText(def);
            tout.setText(def);
        } else if (e.getSource() == register) {
            MyFrame register = new MyFrame();
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        Login f = new Login();
    }
}



