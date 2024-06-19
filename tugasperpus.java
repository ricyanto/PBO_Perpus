package tugasperpus;

import java.sql.*;
import java.util.Scanner;

public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/perpustakaan";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Edit Buku");
            System.out.println("3. Hapus Buku");
            System.out.println("4. Tampilkan Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    tambahBuku(scanner);
                    break;
                case 2:
                    editBuku(scanner);
                    break;
                case 3:
                    hapusBuku(scanner);
                    break;
                case 4:
                    tampilkanBuku();
                    break;
                case 5:
                    System.out.println("Keluar dari program.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahBuku(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Masukkan judul buku: ");
            String judul = scanner.nextLine();

            System.out.print("Masukkan tahun terbit: ");
            int tahun = scanner.nextInt();

            System.out.print("Masukkan stok: ");
            int stok = scanner.nextInt();

            scanner.nextLine(); 
            System.out.print("Masukkan penulis: ");
            String penulis = scanner.nextLine();

            String sql = "INSERT INTO buku (judul_buku, tahun_terbit, stok, penulis) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, judul);
            pstmt.setInt(2, tahun);
            pstmt.setInt(3, stok);
            pstmt.setString(4, penulis);
            pstmt.executeUpdate();

            System.out.println("Buku berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editBuku(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Masukkan ID buku yang akan diedit: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Masukkan judul buku baru: ");
            String judul = scanner.nextLine();

            System.out.print("Masukkan tahun terbit baru: ");
            int tahun = scanner.nextInt();

            System.out.print("Masukkan stok baru: ");
            int stok = scanner.nextInt();

            scanner.nextLine();
            System.out.print("Masukkan penulis baru: ");
            String penulis = scanner.nextLine();

            String sql = "UPDATE buku SET judul_buku = ?, tahun_terbit = ?, stok = ?, penulis = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, judul);
            pstmt.setInt(2, tahun);
            pstmt.setInt(3, stok);
            pstmt.setString(4, penulis);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();

            System.out.println("Buku berhasil diupdate.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void hapusBuku(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Masukkan ID buku yang akan dihapus: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 

            String sql = "DELETE FROM buku WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Buku berhasil dihapus.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tampilkanBuku() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM buku";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String judul = rs.getString("judul_buku");
                int tahun = rs.getInt("tahun_terbit");
                int stok = rs.getInt("stok");
                String penulis = rs.getString("penulis");

                System.out.println("ID: " + id);
                System.out.println("Judul Buku: " + judul);
                System.out.println("Tahun Terbit: " + tahun);
                System.out.println("Stok: " + stok);
                System.out.println("Penulis: " + penulis);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


