import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class adminrent extends JFrame implements ActionListener {
    private Container c;
    private JLabel naslov;
    private JComboBox<String> seznamVozil;
    private JLabel zacetniDatumNajemaLabel;
    private JComboBox<String> zacetniDatumNajemaDropdown;
    private JButton najemiButton;
    private JButton ponastaviButton;
    private JTextArea rezultatObvestila;
    private JLabel koncniDatumNajemaLabel;
    private JComboBox<String> koncniDatumNajemaDropdown;
    private JComboBox<String> podjetjeDropdown;
    private JLabel podjetjeLabel;
    private JLabel cenaLabel;
    private JLabel cenaLabel1;
    private JButton urediButton;
    private JButton izbrisiButton;
    private JButton dodajButton;

    public adminrent() {
        setTitle("Upravljanje z avtomobili");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        naslov = new JLabel("Upravljanje z avtomobili");
        naslov.setFont(new Font("Arial", Font.PLAIN, 30));
        naslov.setSize(300, 30);
        naslov.setLocation(300, 30);
        c.add(naslov);

        seznamVozil = new JComboBox<>();
        seznamVozil.setFont(new Font("Arial", Font.PLAIN, 15));
        seznamVozil.setSize(200, 20);
        seznamVozil.setLocation(250, 100);
        c.add(seznamVozil);

        zacetniDatumNajemaLabel = new JLabel("Začetni Datum Najema:");
        zacetniDatumNajemaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        zacetniDatumNajemaLabel.setSize(220, 20);
        zacetniDatumNajemaLabel.setLocation(50, 150);
        c.add(zacetniDatumNajemaLabel);

        zacetniDatumNajemaDropdown = new JComboBox<>();
        zacetniDatumNajemaDropdown.setFont(new Font("Arial", Font.PLAIN, 15));
        zacetniDatumNajemaDropdown.setSize(200, 20);
        zacetniDatumNajemaDropdown.setLocation(300, 150);
        napolniDropdownZDatumom(zacetniDatumNajemaDropdown);
        c.add(zacetniDatumNajemaDropdown);

        koncniDatumNajemaLabel = new JLabel("Končni Datum Najema:");
        koncniDatumNajemaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        koncniDatumNajemaLabel.setSize(200, 20);
        koncniDatumNajemaLabel.setLocation(50, 200);
        c.add(koncniDatumNajemaLabel);

        koncniDatumNajemaDropdown = new JComboBox<>();
        koncniDatumNajemaDropdown.setFont(new Font("Arial", Font.PLAIN, 15));
        koncniDatumNajemaDropdown.setSize(200, 20);
        koncniDatumNajemaDropdown.setLocation(300, 200);
        napolniDropdownZDatumom(koncniDatumNajemaDropdown);
        c.add(koncniDatumNajemaDropdown);

        podjetjeLabel = new JLabel("Podjetje:");
        podjetjeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        podjetjeLabel.setSize(200, 20);
        podjetjeLabel.setLocation(50, 250);
        c.add(podjetjeLabel);

        podjetjeDropdown = new JComboBox<>();
        podjetjeDropdown.setFont(new Font("Arial", Font.PLAIN, 15));
        podjetjeDropdown.setSize(200, 20);
        podjetjeDropdown.setLocation(300, 250);
        c.add(podjetjeDropdown);

        cenaLabel = new JLabel("Cena:");
        cenaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cenaLabel.setSize(200, 20);
        cenaLabel.setLocation(50, 300);
        c.add(cenaLabel);

        cenaLabel1 = new JLabel("0€");
        cenaLabel1.setFont(new Font("Arial", Font.PLAIN, 20));
        cenaLabel1.setSize(200, 20);
        cenaLabel1.setLocation(300, 300);
        c.add(cenaLabel1);

        najemiButton = new JButton("Najemi Vozilo");
        najemiButton.setFont(new Font("Arial", Font.PLAIN, 15));
        najemiButton.setSize(150, 20);
        najemiButton.setLocation(150, 350);
        najemiButton.addActionListener(this);
        c.add(najemiButton);

        ponastaviButton = new JButton("Ponastavi");
        ponastaviButton.setFont(new Font("Arial", Font.PLAIN, 15));
        ponastaviButton.setSize(100, 20);
        ponastaviButton.setLocation(350, 350);
        ponastaviButton.addActionListener(this);
        c.add(ponastaviButton);

        rezultatObvestila = new JTextArea();
        rezultatObvestila.setFont(new Font("Arial", Font.PLAIN, 15));
        rezultatObvestila.setSize(300, 400);
        rezultatObvestila.setLocation(500, 100);
        rezultatObvestila.setLineWrap(true);
        rezultatObvestila.setEditable(false);
        c.add(rezultatObvestila);

        urediButton = new JButton("Uredi Vozilo");
        urediButton.setFont(new Font("Arial", Font.PLAIN, 15));
        urediButton.setSize(150, 20);
        urediButton.setLocation(150, 400);
        urediButton.addActionListener(this);
        c.add(urediButton);

        izbrisiButton = new JButton("Izbriši Vozilo");
        izbrisiButton.setFont(new Font("Arial", Font.PLAIN, 15));
        izbrisiButton.setSize(150, 20);
        izbrisiButton.setLocation(350, 400);
        izbrisiButton.addActionListener(this);
        c.add(izbrisiButton);

        dodajButton = new JButton("Dodaj vozilo");
        dodajButton.setFont(new Font("Arial", Font.PLAIN, 15));
        dodajButton.setSize(150, 20);
        dodajButton.setLocation(350, 450);
        dodajButton.addActionListener(this);
        c.add(dodajButton);

        napolniSeznamVozil();
        napolniSeznamPodjetij();
    }


    private void napolniDropdownZDatumom(JComboBox<String> dropdown) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> datumi = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < 60; i++) {
            Date datum = calendar.getTime();
            datumi.add(dateFormat.format(datum));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (String datum : datumi) {
            dropdown.addItem(datum);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == najemiButton) {
            String izbranoVozilo = (String) seznamVozil.getSelectedItem();
            String izbranDatum = (String) zacetniDatumNajemaDropdown.getSelectedItem();

            try {
                DatabaseConnection povezavaVBazo = new DatabaseConnection();
                Connection povezava = povezavaVBazo.getConnection();

                String[] deli = izbranoVozilo.split(" - ");
                int idVozila = Integer.parseInt(deli[0]);
                String sql = "INSERT INTO Najem (Datum_zacetka, Datum_Konca, Cena, Stanje, podjetje_ID, vozilo_ID) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = povezava.prepareStatement(sql);
                statement.setString(1, izbranDatum);
                statement.setString(2, (String) koncniDatumNajemaDropdown.getSelectedItem());
                statement.setLong(3, izracunajCenoNajema(izbranDatum, (String) koncniDatumNajemaDropdown.getSelectedItem()));
                statement.setString(4, "Najeto");
                statement.setInt(5, podjetjeDropdown.getSelectedIndex() + 1);
                statement.setInt(6, idVozila);
                statement.executeUpdate();

                // Retrieve the image from the database
                String sqlImage = "SELECT slika FROM Vozilo WHERE ID = ?";
                PreparedStatement statementImage = povezava.prepareStatement(sqlImage);
                statementImage.setInt(1, idVozila);
                ResultSet resultSetImage = statementImage.executeQuery();

                if (resultSetImage.next()) {
                    byte[] imgBytes = resultSetImage.getBytes("slika");
                    if (imgBytes != null && imgBytes.length > 0) {
                        // Convert the bytes to an ImageIcon
                        ImageIcon imageIcon = new ImageIcon(imgBytes);

                        // Create a JLabel to display the image
                        JLabel imageLabel = new JLabel(imageIcon);

                        // Add the JLabel to the container
                        c.add(imageLabel);
                    }
                }

                povezava.close();
                rezultatObvestila.setText("Vozilo je bilo uspešno najeto!");
            } catch (Exception ex) {
                ex.printStackTrace();
                rezultatObvestila.setText("Prišlo je do napake pri najemu vozila.");
            }
        } else if (e.getSource() == ponastaviButton) {
            seznamVozil.setSelectedIndex(0);
            zacetniDatumNajemaDropdown.setSelectedIndex(0);
            rezultatObvestila.setText("");
        } else if (e.getSource() == urediButton) {
            String izbranoVozilo = (String) seznamVozil.getSelectedItem();
            if (izbranoVozilo != null) {
                String[] deli = izbranoVozilo.split(" - ");
                int idVozila = Integer.parseInt(deli[0]);
                new uredi(idVozila).setVisible(true);
            } else {
                rezultatObvestila.setText("Prosim, izberite vozilo za urejanje.");
            }
        } else if (e.getSource() == izbrisiButton) {
            String izbranoVozilo = (String) seznamVozil.getSelectedItem();
            if (izbranoVozilo != null) {
                String[] deli = izbranoVozilo.split(" - ");
                int idVozila = Integer.parseInt(deli[0]);
                try {
                    DatabaseConnection povezavaVBazo = new DatabaseConnection();
                    Connection povezava = povezavaVBazo.getConnection();

                    String sql = "DELETE FROM Vozilo WHERE ID = ?";
                    PreparedStatement statement = povezava.prepareStatement(sql);
                    statement.setInt(1, idVozila);
                    statement.executeUpdate();
                    povezava.close();
                    rezultatObvestila.setText("Vozilo je bilo uspešno izbrisano!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    rezultatObvestila.setText("Prišlo je do napake pri brisanju vozila.");
                }
            } else {
                rezultatObvestila.setText("Prosim, izberite vozilo za brisanje.");
            }
        } else if (e.getSource() == dodajButton) {
            new Admin().setVisible(true);
        }
    }



    private void napolniSeznamVozil() {
        try {
            DatabaseConnection povezavaVBazo = new DatabaseConnection();
            Connection povezava = povezavaVBazo.getConnection();

            Statement statement = povezava.createStatement();
            ResultSet rezultat = statement.executeQuery("SELECT ID, Registracija FROM Vozilo");

            while (rezultat.next()) {
                int id = rezultat.getInt("ID");
                String registracija = rezultat.getString("Registracija");
                seznamVozil.addItem(id + " - " + registracija);
            }

            povezava.close();

        } catch (SQLException ex) {
            rezultatObvestila.setText("Napaka pri pridobivanju seznama vozil: " + ex.getMessage());
        }
    }

    private void napolniSeznamPodjetij() {
        try {
            DatabaseConnection povezavaVBazo = new DatabaseConnection();
            Connection povezava = povezavaVBazo.getConnection();

            Statement statement = povezava.createStatement();
            ResultSet rezultat = statement.executeQuery("SELECT ID, ime FROM Podjetje");

            while (rezultat.next()) {
                int id = rezultat.getInt("ID");
                String ime = rezultat.getString("ime");
                podjetjeDropdown.addItem(id + " - " + ime);
            }

            povezava.close();

        } catch (SQLException ex) {
            rezultatObvestila.setText("Napaka pri pridobivanju seznama podjetij: " + ex.getMessage());
        }
    }

    private long izracunajCenoNajema(String zacetniDatum, String koncniDatum) {
        LocalDate zacetni = LocalDate.parse(zacetniDatum);
        LocalDate koncni = LocalDate.parse(koncniDatum);
        long stDni = ChronoUnit.DAYS.between(zacetni, koncni);
        String[] deli = ((String) seznamVozil.getSelectedItem()).split(" - ");
        int cenaNaDan = Integer.parseInt(deli[2].replace("€/dan", ""));
        return stDni * cenaNaDan;
    }

    public static void main(String[] args) {
        adminrent frame = new adminrent();
        frame.setVisible(true);
    }
}
