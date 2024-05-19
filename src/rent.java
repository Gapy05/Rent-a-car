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

public class rent extends JFrame implements ActionListener {
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

    public rent() {
        setTitle("Najem Vozila");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        naslov = new JLabel("Najem Vozila");
        naslov.setFont(new Font("Arial", Font.PLAIN, 30));
        naslov.setSize(300, 30);
        naslov.setLocation(300, 30);
        c.add(naslov);

        seznamVozil = new JComboBox<>();
        seznamVozil.setFont(new Font("Arial", Font.PLAIN, 15));
        seznamVozil.setSize(200, 20);
        seznamVozil.setLocation(250, 100);
        napolniSeznamVozil();
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

        podjetjeLabel = new JLabel("Podjetje:"); // Initialize the new JLabel
        podjetjeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        podjetjeLabel.setSize(200, 20);
        podjetjeLabel.setLocation(50, 250);
        c.add(podjetjeLabel);

        podjetjeDropdown = new JComboBox<>(); // Dropdown za izbiro podjetja
        podjetjeDropdown.setFont(new Font("Arial", Font.PLAIN, 15));
        podjetjeDropdown.setSize(200, 20);
        podjetjeDropdown.setLocation(300, 250);
        napolniSeznamPodjetij(); // Metoda za polnjenje dropdowna s podjetji
        c.add(podjetjeDropdown);

        cenaLabel = new JLabel("Cena:");
        cenaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cenaLabel.setSize(200, 20);
        cenaLabel.setLocation(5, 300);
        c.add(cenaLabel);

        cenaLabel1 = new JLabel(izracunajCenoNajema(zacetniDatumNajemaDropdown.getSelectedItem().toString(), (String) koncniDatumNajemaDropdown.getSelectedItem().toString()) + "€");
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

        setVisible(true);
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
                statement.setString(2, (String) koncniDatumNajemaDropdown.getSelectedItem()); // Uporabi izbran končni datum
                statement.setLong(3, izracunajCenoNajema(izbranDatum, (String) koncniDatumNajemaDropdown.getSelectedItem())); // Izračunaj ceno najema

                statement.setString(4, "Najeto");
                statement.setInt(5, podjetjeDropdown.getSelectedIndex() + 1); // Uporabi ID izbranega podjetja
                statement.setInt(6, idVozila);
                statement.executeUpdate();

                povezava.close();
                rezultatObvestila.setText("Vozilo je bilo uspešno najeto!\n");
                rezultatObvestila.append("Podrobnosti:\n");
                rezultatObvestila.append("Vozilo: " + izbranoVozilo + "\n");
                rezultatObvestila.append("Začetni Datum: " + izbranDatum + "\n");
                rezultatObvestila.append("Končni Datum: " + koncniDatumNajemaDropdown.getSelectedItem() + "\n");
                rezultatObvestila.append("Podjetje: " + podjetjeDropdown.getSelectedItem() + "\n");
                rezultatObvestila.append("Cena: " + izracunajCenoNajema(izbranDatum, (String) koncniDatumNajemaDropdown.getSelectedItem()));

                cenaLabel1.setText(izracunajCenoNajema(zacetniDatumNajemaDropdown.getSelectedItem().toString(), (String) koncniDatumNajemaDropdown.getSelectedItem().toString()) + "€");;

            } catch (NumberFormatException | SQLException izjema) {
                rezultatObvestila.setText("Napaka pri najemu vozila: " + izjema.getMessage());
            }
        } else if (e.getSource() == ponastaviButton) {
            rezultatObvestila.setText("");
        }
    }

    // Metoda za izračun cene najema (implementirate jo sami)
    private long izracunajCenoNajema(String zacetniDatum, String koncniDatum) {
        LocalDate zacetniDatumLD = LocalDate.parse(zacetniDatum);
        LocalDate koncniDatumLD = LocalDate.parse(koncniDatum);
        long stDni = ChronoUnit.DAYS.between(zacetniDatumLD, koncniDatumLD);
        return 150 * stDni;
}

    // Metoda za polnjenje seznama vozil iz baze podatkov
    private void napolniSeznamVozil() {
        try {
            DatabaseConnection povezavaVBazo = new DatabaseConnection();
            Connection povezava = povezavaVBazo.getConnection();

            // Izvedi poizvedbo za pridobitev vseh vozil
            Statement statement = povezava.createStatement();
            ResultSet rezultat = statement.executeQuery("SELECT ID, Registracija FROM Vozilo");

            // Dodaj vozila v seznam
            while (rezultat.next()) {
                int id = rezultat.getInt("ID");
                String registracija = rezultat.getString("Registracija");
                seznamVozil.addItem(id + " - " + registracija);
            }

            // Zapri povezavo
            povezava.close();

        } catch (SQLException ex) {
            rezultatObvestila.setText("Napaka pri pridobivanju seznama vozil: " + ex.getMessage());
        }
    }

    // Metoda za polnjenje seznama podjetij iz baze podatkov
    private void napolniSeznamPodjetij() {
        try {
            DatabaseConnection povezavaVBazo = new DatabaseConnection();
            Connection povezava = povezavaVBazo.getConnection();

            // Izvedi poizvedbo za pridobitev vseh podjetij
            Statement statement = povezava.createStatement();
            ResultSet rezultat = statement.executeQuery("SELECT ID, ime FROM Podjetje");

            // Dodaj podjetja v seznam
            while (rezultat.next()) {
                int id = rezultat.getInt("ID");
                String ime = rezultat.getString("ime");
                podjetjeDropdown.addItem(id + " - " + ime);
            }

            // Zapri povezavo
            povezava.close();

        } catch (SQLException ex) {
            rezultatObvestila.setText("Napaka pri pridobivanju seznama podjetij: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Tukaj bi lahko preverili, če je uporabnik prijavljen kot administrator
        // Za demonstracijske namene bomo preprosto prikazali okno za upravljanje z vozili
        EventQueue.invokeLater(() -> {
            JFrame okno = new JFrame("Upravljanje z avtomobili");
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setContentPane(new Admin());
            okno.pack();
            okno.setVisible(true);
        });
    }
}