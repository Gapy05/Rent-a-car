import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class AdminFrame extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel registracija;
    private JTextField tregistracija;
    private JLabel cena;
    private JTextField tcena;
    private JLabel model;
    private JComboBox<String> tmodel;
    private JLabel barva;
    private JComboBox<String> tbarva;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;

    public AdminFrame() {
        setTitle("Dodajanje avtomobila");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Dodajanje avtomobila");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        registracija = new JLabel("Registracija");
        registracija.setFont(new Font("Arial", Font.PLAIN, 20));
        registracija.setSize(150, 20);
        registracija.setLocation(100, 100);
        c.add(registracija);

        tregistracija = new JTextField();
        tregistracija.setFont(new Font("Arial", Font.PLAIN, 15));
        tregistracija.setSize(190, 20);
        tregistracija.setLocation(250, 100);
        c.add(tregistracija);

        cena = new JLabel("Cena");
        cena.setFont(new Font("Arial", Font.PLAIN, 20));
        cena.setSize(100, 20);
        cena.setLocation(100, 150);
        c.add(cena);

        tcena = new JTextField();
        tcena.setFont(new Font("Arial", Font.PLAIN, 15));
        tcena.setSize(190, 20);
        tcena.setLocation(250, 150);
        c.add(tcena);

        model = new JLabel("Model");
        model.setFont(new Font("Arial", Font.PLAIN, 20));
        model.setSize(100, 20);
        model.setLocation(100, 200);
        c.add(model);

        tmodel = new JComboBox<>();
        tmodel.setFont(new Font("Arial", Font.PLAIN, 15));
        tmodel.setSize(190, 20);
        tmodel.setLocation(250, 200);
        c.add(tmodel);

        barva = new JLabel("Barva");
        barva.setFont(new Font("Arial", Font.PLAIN, 20));
        barva.setSize(100, 20);
        barva.setLocation(100, 250);
        c.add(barva);

        tbarva = new JComboBox<>();
        tbarva.setFont(new Font("Arial", Font.PLAIN, 15));
        tbarva.setSize(190, 20);
        tbarva.setLocation(250, 250);
        c.add(tbarva);

        // Populate model and barva comboboxes from database
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // Populate model combobox
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ime FROM Model");
            while (resultSet.next()) {
                tmodel.addItem(resultSet.getString("ime"));
            }

            // Populate barva combobox
            resultSet = statement.executeQuery("SELECT ime FROM Barva");
            while (resultSet.next()) {
                tbarva.addItem(resultSet.getString("ime"));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sub = new JButton("Dodaj avtomobil");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(200, 20);
        sub.setLocation(150, 350);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(380, 350);
        reset.addActionListener(this);
        c.add(reset);

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
        res.setLocation(100, 500);
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
            String registracija = tregistracija.getText();
            String cena = tcena.getText();
            String model = (String) tmodel.getSelectedItem();
            String barva = (String) tbarva.getSelectedItem();

            try {
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                // Get model ID and barva ID
                int modelId = getIdFromName("Model", model, connection);
                int barvaId = getIdFromName("Barva", barva, connection);

                String sql = "INSERT INTO Vozilo (Registracija, Cena, model_ID, barva_ID) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, registracija);
                statement.setString(2, cena);
                statement.setInt(3, modelId);
                statement.setInt(4, barvaId);

                statement.executeUpdate();
                connection.close();

                tout.setText("Avtomobil uspešno dodan!\n");
                tout.append("Podrobnosti:\n");
                tout.append("Registracija: " + registracija + "\n");
                tout.append("Cena: " + cena + "\n");
                tout.append("Model: " + model + "\n");
                tout.append("Barva: " + barva + "\n");
                res.setText("Avtomobil uspešno dodan!");

            } catch (SQLException ex) {
                ex.printStackTrace();
                res.setText("Napaka pri dodajanju avtomobila!");
            }
        } else if (e.getSource() == reset) {
            tregistracija.setText("");
            tcena.setText("");
            tmodel.setSelectedIndex(0);
            tbarva.setSelectedIndex(0);
            tout.setText("");
            res.setText("");
        }
    }

    private int getIdFromName(String table, String name, Connection connection) throws SQLException {
        String sql = "SELECT ID FROM " + table + " WHERE ime = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ID");
        } else {
            throw new SQLException("No such entry in table " + table);
        }
    }
}

public class Admin {
    public static void main(String[] args) {
        // Here you need to check if the user is admin
        // For demonstration purposes, we'll just directly show the admin frame
        boolean isAdmin = true; // Replace with actual admin check

        if (isAdmin) {
            new AdminFrame();
        } else {
            JOptionPane.showMessageDialog(null, "You are not authorized to access this page.");
        }
    }
}