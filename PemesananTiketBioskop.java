package tugas2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Aplikasi PemesananTiketBioskop.
 * Aplikasi ini menyediakan fitur pemesanan tiket bioskop dengan dua mode login, yaitu Admin dan User.
 * Admin dapat menambahkan film baru ke daftar film, sedangkan User dapat membeli tiket film yang tersedia.
 */
public class PemesananTiketBioskop {
    /**
     * Metode utama yang menjalankan aplikasi.
     *
     * @param args Argumen baris perintah (tidak digunakan dalam aplikasi ini)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        // Daftar film
        ArrayList<Film> daftarFilm = new ArrayList<>();
        daftarFilm.add(new Film("Avengers: Endgame", 180, "Action", 50000));
        daftarFilm.add(new Film("Doraemon", 103, "Animation", 40000));
        daftarFilm.add(new Film("Joker", 122, "Drama", 45000));

        // Membuat admin dan user
        Admin admin = new Admin("admin", "admin");
        User user = new User("user", "user");

        boolean running = true;
        while (running) {
            // Menampilkan menu login
            System.out.println("Pilih mode login:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Keluar");
            int pilihanLogin = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (pilihanLogin) {
                case 1 -> loginAdmin(admin, daftarFilm, scanner);
                case 2 -> loginUser(user, daftarFilm, scanner);
                case 3 -> {
                    System.out.println("Terima kasih telah menggunakan layanan ini.");
                    running = false;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    /**
     * Metode login untuk admin.
     *
     * @param admin       Objek admin yang digunakan untuk login
     * @param daftarFilm  Daftar film yang tersedia di bioskop
     * @param scanner     Scanner untuk membaca input dari pengguna
     */
    private static void loginAdmin(Admin admin, ArrayList<Film> daftarFilm, Scanner scanner) {
        System.out.println("Masukkan username admin:");
        String usernameAdmin = scanner.nextLine();
        System.out.println("Masukkan password admin:");
        String passwordAdmin = scanner.nextLine();

        if (admin.login(usernameAdmin, passwordAdmin)) {
            System.out.println("Login berhasil! Selamat datang, Admin.");
            boolean tambahLagi = true;
            while (tambahLagi) {
                admin.tambahFilm(daftarFilm, scanner);
                System.out.println("Apakah ingin menambah film lagi? (y/n):");
                String pilihanTambah = scanner.nextLine();
                tambahLagi = pilihanTambah.equalsIgnoreCase("y");
            }
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }

    /**
     * Metode login untuk user.
     *
     * @param user       Objek user yang digunakan untuk login
     * @param daftarFilm Daftar film yang tersedia di bioskop
     * @param scanner    Scanner untuk membaca input dari pengguna
     */


    private static void loginUser(User user, ArrayList<Film> daftarFilm, Scanner scanner) {
        System.out.println("Masukkan username user:");
        String usernameUser = scanner.nextLine();
        System.out.println("Masukkan password user:");
        String passwordUser = scanner.nextLine();

        if (user.login(usernameUser, passwordUser)) {
            System.out.println("Login berhasil! Selamat datang, User.");
            System.out.println("Daftar film yang tersedia:");
            for (int i = 0; i < daftarFilm.size(); i++) {
                Film film = daftarFilm.get(i);
                System.out.println((i + 1) + ". " + film.getJudul() + " - " + film.getGenre() + " - Rp" + film.getHarga());
            }
            System.out.println("Pilih nomor film yang ingin dibeli tiketnya:");
            int pilihanFilm = scanner.nextInt();
            if (pilihanFilm > 0 && pilihanFilm <= daftarFilm.size()) {
                Film filmTerpilih = daftarFilm.get(pilihanFilm - 1);
                System.out.println("Anda telah membeli tiket untuk film " + filmTerpilih.getJudul());
            } else {
                System.out.println("Pilihan film tidak valid.");
            }
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }
}

/**
 * Kelas Akun yang merepresentasikan pengguna umum dengan username dan password.
 * Menjadi superclass untuk kelas Admin dan User.
 */
class Akun {
    protected String username;
    protected String password;

    /**
     * Konstruktor Akun.
     *
     * @param username Username untuk login
     * @param password Password untuk login
     */
    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Metode login untuk memverifikasi username dan password.
     *
     * @param inputUsername Username yang dimasukkan pengguna
     * @param inputPassword Password yang dimasukkan pengguna
     * @return True jika login berhasil, false jika gagal
     */
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
}

/**
 * Kelas Admin yang memungkinkan admin untuk menambahkan film baru ke dalam daftar film.
 */
class Admin extends Akun {
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Menambah film baru ke daftar film.
     *
     * @param daftarFilm Daftar film yang ada di bioskop
     * @param scanner    Scanner untuk membaca input dari pengguna
     */
    public void tambahFilm(ArrayList<Film> daftarFilm, Scanner scanner) {
        String judul = inputFilmDetail(scanner, "Masukkan judul film:");
        int durasi = inputFilmDurasi(scanner);
        String genre = inputFilmDetail(scanner, "Masukkan genre film:");
        int harga = inputFilmHarga(scanner);
        daftarFilm.add(new Film(judul, durasi, genre, harga));
        System.out.println("Film berhasil ditambahkan!");
    }

    private String inputFilmDetail(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private int inputFilmDurasi(Scanner scanner) {
        System.out.println("Masukkan durasi film (menit):");
        return scanner.nextInt();
    }

    private int inputFilmHarga(Scanner scanner) {
        System.out.println("Masukkan harga tiket:");
        return scanner.nextInt();
    }
}

/**
 * Kelas User yang memungkinkan user untuk membeli tiket film.
 */
class User extends Akun {
    public User(String username, String password) {
        super(username, password);
    }
}

/**
 * Kelas Film yang merepresentasikan informasi dari sebuah film di bioskop.
 */
class Film {
    private final String judul;
    private final int durasi;
    private final String genre;
    private final int harga;

    /**
     * Konstruktor Film.
     *
     * @param judul  Judul film
     * @param durasi Durasi film dalam menit
     * @param genre  Genre film
     * @param harga  Harga tiket film
     */
    public Film(String judul, int durasi, String genre, int harga) {
        this.judul = judul;
        this.durasi = durasi;
        this.genre = genre;
        this.harga = harga;
    }

    /**
     * Mendapatkan judul film.
     *
     * @return Judul film
     */
    public String getJudul() {
        return judul;
    }

    /**
     * Mendapatkan genre film.
     *
     * @return Genre film
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Mendapatkan harga tiket film.
     *
     * @return Harga tiket film
     */
    public int getHarga() {
        return harga;
    }
}
