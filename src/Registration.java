import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class Kraj {
    private int id;
    private String ime;

    public Kraj(int id, String ime) {
        this.id = id;
        this.ime = ime;
    }

    public int getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    @Override
    public String toString() {
        return ime;
    }
}

class PasswordHash {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}


class MyFrame extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel ime;
    private JTextField tname;
    private JLabel priimek;
    private JTextField tpriimek;
    private JLabel username;
    private JTextField tusername;
    private JLabel geslo;
    private JPasswordField tgeslo;
    private JLabel email;
    private JTextField temail;
    private JLabel telefon;
    private JTextField ttelefon;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox<String> date;
    private JComboBox<String> month;
    private JComboBox<String> year;
    private JLabel kraj;
    private JComboBox<Kraj> tkraj;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;
    private JButton login;

    private String datum[]
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private String meseci[]
            = { "Jan", "Feb", "Mar", "Apr",
            "Maj", "Jun", "Jul", "Avg",
            "Sep", "Okt", "Nov", "Dec" };
    private String leta[]
            = { "1995", "1996", "1997", "1998",
            "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010",
            "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018",
            "2019" };

    public MyFrame() {
        setTitle("Registracija");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Registracija");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        ime = new JLabel("Ime");
        ime.setFont(new Font("Arial", Font.PLAIN, 20));
        ime.setSize(100, 20);
        ime.setLocation(100, 100);
        c.add(ime);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(250, 100);
        c.add(tname);

        priimek = new JLabel("Priimek");
        priimek.setFont(new Font("Arial", Font.PLAIN, 20));
        priimek.setSize(100, 20);
        priimek.setLocation(100, 130);
        c.add(priimek);

        tpriimek = new JTextField();
        tpriimek.setFont(new Font("Arial", Font.PLAIN, 15));
        tpriimek.setSize(190, 20);
        tpriimek.setLocation(250, 130);
        c.add(tpriimek);

        username = new JLabel("Uporabniško ime");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setSize(150, 20);
        username.setLocation(100, 160);
        c.add(username);

        tusername = new JTextField();
        tusername.setFont(new Font("Arial", Font.PLAIN, 15));
        tusername.setSize(190, 20);
        tusername.setLocation(250, 160);
        c.add(tusername);

        geslo = new JLabel("Geslo");
        geslo.setFont(new Font("Arial", Font.PLAIN, 20));
        geslo.setSize(100, 20);
        geslo.setLocation(100, 190);
        c.add(geslo);

        tgeslo = new JPasswordField();
        tgeslo.setFont(new Font("Arial", Font.PLAIN, 15));
        tgeslo.setSize(190, 20);
        tgeslo.setLocation(250, 190);
        c.add(tgeslo);

        email = new JLabel("Email");
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setSize(100, 20);
        email.setLocation(100, 220);
        c.add(email);

        temail = new JTextField();
        temail.setFont(new Font("Arial", Font.PLAIN, 15));
        temail.setSize(190, 20);
        temail.setLocation(250, 220);
        c.add(temail);

        telefon = new JLabel("Telefon");
        telefon.setFont(new Font("Arial", Font.PLAIN, 20));
        telefon.setSize(100, 20);
        telefon.setLocation(100, 250);
        c.add(telefon);

        ttelefon = new JTextField();
        ttelefon.setFont(new Font("Arial", Font.PLAIN, 15));
        ttelefon.setSize(150, 20);
        ttelefon.setLocation(250, 250);
        c.add(ttelefon);

        gender = new JLabel("Spol");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 20);
        gender.setLocation(100, 280);
        c.add(gender);

        male = new JRadioButton("Moški");
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(250, 280);
        c.add(male);

        female = new JRadioButton("Ženska");
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setLocation(325, 280);
        c.add(female);

        gengp = new ButtonGroup();
        gengp.add(male);
        gengp.add(female);

        dob = new JLabel("Datum rojstva:");
        dob.setFont(new Font("Arial", Font.PLAIN, 20));
        dob.setSize(300, 20);
        dob.setLocation(100, 330);
        c.add(dob);

        date = new JComboBox<String>(datum);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(50, 20);
        date.setLocation(250, 330);
        c.add(date);

        month = new JComboBox<String>(meseci);
        month.setFont(new Font("Arial", Font.PLAIN, 15));
        month.setSize(60, 20);
        month.setLocation(300, 330);
        c.add(month);

        year = new JComboBox<String>(leta);
        year.setFont(new Font("Arial", Font.PLAIN, 15));
        year.setSize(80, 20);
        year.setLocation(360, 330);
        c.add(year);

        kraj = new JLabel("Kraj");
        kraj.setFont(new Font("Arial", Font.PLAIN, 20));
        kraj.setSize(100, 20);
        kraj.setLocation(100, 360);
        c.add(kraj);

        tkraj = new JComboBox<Kraj>();
        tkraj.setFont(new Font("Arial", Font.PLAIN, 15));
        tkraj.setSize(190, 20);
        tkraj.setLocation(250, 360);
        c.add(tkraj);

        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, ime FROM kraj");

            while (resultSet.next()) {
                tkraj.addItem(new Kraj(resultSet.getInt("id"), resultSet.getString("ime")));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sub = new JButton("Registriraj se!");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(200, 20);
        sub.setLocation(150, 420);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(380, 420);
        reset.addActionListener(this);
        c.add(reset);

        login= new JButton("Prijava");
        login.setFont(new Font("Arial", Font.PLAIN, 15));
        login.setSize(100, 20);
        login.setLocation(300, 450);
        login.addActionListener(this);
        c.add(login);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 520);
        c.add(res);

        resadd = new JTextArea();
        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
        resadd.setSize(200, 75);
        resadd.setLocation(580, 175);
        resadd.setLineWrap(true);
        c.add(resadd);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String name = tname.getText();
            String surname = tpriimek.getText();
            String user = tusername.getText();
            String password = String.valueOf(tgeslo.getPassword());
            String email = temail.getText();
            String phone = ttelefon.getText();
            String gender = male.isSelected() ? "Moški" : "Ženska";
            String dob = date.getSelectedItem() + "/" + month.getSelectedItem() + "/" + year.getSelectedItem();
            Kraj selectedKraj = (Kraj) tkraj.getSelectedItem();

            if (selectedKraj != null) {
                try {
                    DatabaseConnection dbConnection = new DatabaseConnection();
                    Connection connection = dbConnection.getConnection();
                    String sql = "{CALL insertUser(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                    CallableStatement statement = dbConnection.prepareCall(sql, connection);

                    statement.setString(1, name);
                    statement.setString(2, surname);
                    statement.setString(3, user);
                    statement.setString(4, PasswordHash.hashPassword(password));
                    statement.setString(5, email);
                    statement.setString(6, phone);
                    statement.setString(7, gender);
                    statement.setString(8, dob);
                    statement.setInt(9, selectedKraj.getId());

                    statement.execute();
                    connection.close();

                    tout.setText("Registracija uspešna!\n");
                    tout.append("Podrobnosti:\n");
                    tout.append("Ime: " + name + "\n");
                    tout.append("Priimek: " + surname + "\n");
                    tout.append("Uporabniško ime: " + user + "\n");
                    tout.append("Email: " + email + "\n");
                    tout.append("Telefon: " + phone + "\n");
                    tout.append("Spol: " + gender + "\n");
                    tout.append("Datum rojstva: " + dob + "\n");
                    tout.append("Kraj: " + selectedKraj.getIme() + "\n");
                    res.setText("Registracija uspešna!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    res.setText("Napaka pri registraciji!");
                }
            }
        } else if (e.getSource() == reset) {
            tname.setText("");
            tpriimek.setText("");
            tusername.setText("");
            tgeslo.setText("");
            temail.setText("");
            ttelefon.setText("");
            male.setSelected(true);
            date.setSelectedIndex(0);
            month.setSelectedIndex(0);
            year.setSelectedIndex(0);
            tkraj.setSelectedIndex(0);
            tout.setText("");
            res.setText("");
        } else if (e.getSource() == login) {
            Login login = new Login();
            this.setVisible(false);
        }
    }
}

public class Registration {
    public static void main(String[] args) throws Exception {
        MyFrame f = new MyFrame();
    }
}
