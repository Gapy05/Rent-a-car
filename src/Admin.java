import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends JFrame implements ActionListener {
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
    private JButton glavniMeni;
    private JButton naloziteSlikoButton; // Gumb za nalaganje slike

    private File selectedFile; // Izbrana slika

public Admin() {
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

    glavniMeni = new JButton("Glavni meni");
    glavniMeni.setFont(new Font("Arial", Font.PLAIN, 15));
    glavniMeni.setSize(150, 20);
    glavniMeni.setLocation(250, 400);
    glavniMeni.addActionListener(this);
    c.add(glavniMeni);

    naloziteSlikoButton = new JButton("Naložite sliko");
    naloziteSlikoButton.setFont(new Font("Arial", Font.PLAIN, 15));
    naloziteSlikoButton.setSize(200, 20);
    naloziteSlikoButton.setLocation(200, 450);
    naloziteSlikoButton.addActionListener(this); // Dodajanje poslušalca dogodkov
    c.add(naloziteSlikoButton);

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

            String sql = "INSERT INTO Vozilo (Registracija, Cena, model_ID, barva_ID, slika) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, registracija);
            statement.setString(2, cena);
            statement.setInt(3, modelId);
            statement.setInt(4, barvaId);
            statement.setBytes(5, Files.readAllBytes(selectedFile.toPath())); // Nalaganje slike v bazo

            statement.executeUpdate();
            connection.close();

            tout.setText("Avtomobil uspešno dodan!\n");
            tout.append("Podrobnosti:\n");
            tout.append("Registracija: " + registracija + "\n");
            tout.append("Cena: " + cena + "\n");
            tout.append("Model: " + model + "\n");
            tout.append("Barva: " + barva + "\n");
            res.setText("Avtomobil uspešno dodan!");
            res.setText("Napaka pri dodajanju avtomobila!");
        } catch (SQLException | IOException ex) {
            res.setText("Napaka: " + ex.getMessage());
        }
    } else if (e.getSource() == reset) {
        tregistracija.setText("");
        tcena.setText("");
        tmodel.setSelectedIndex(0);
        tbarva.setSelectedIndex(0);
        tout.setText("");
        res.setText("");
    } else if (e.getSource() == glavniMeni) {
        new rent();
        this.setVisible(false);
    } else if (e.getSource() == naloziteSlikoButton) { // Če je uporabnik kliknil gumb za nalaganje slike
        JFileChooser fileChooser = new JFileChooser(); // Ustvari nov izbirnik datotek
        int response = fileChooser.showOpenDialog(null); // Odpre dialog za izbiro datoteke

        if (response == JFileChooser.APPROVE_OPTION) { // Če je uporabnik izbral datoteko
            selectedFile = fileChooser.getSelectedFile(); // Izbrano datoteko shranimo
            resadd.setText("Pot do izbrane slike: " + selectedFile.getAbsolutePath()); // Izpišemo pot do izbrane slike
        }
    }
}
    private int getIdFromName(String table, String name, Connection connection) throws SQLException {
        String sql = "SELECT ID FROM " + table + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ID");
        } else {
            throw new SQLException("No entry with name " + name + " found in table " + table);
        }
    }
}

