package otomasyon;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

abstract class OgretimUye{
    void notGir(){};
    void ogrenciNotGoruntule(){};
    void yoklama(){};
    void geriDon(){};
}

interface Ogretim{
     void uyeOl();
     void girisYap();
}

public class OgretimUyesi extends OgretimUye implements Ogretim{

    private String firstName;
    private String lastName;
    private String teacherId;
    private String email;
    private String password;
    private String lecture;
    private String lectureId;
    private String studentId;
    private String letterGrade;
    private String studentNote;
    private String durum;
    private static String university = "Istanbul University";

    public boolean girisYap = false;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getStudentNote() {
        return studentNote;
    }

    public void setStudentNote(String studentNote) {
        this.studentNote = studentNote;
    }

    public static String getUniversity() {
        return university;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String Hash(String Password) {

        String passwordToHash = Password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;

    }

    @Override
    public void uyeOl() {
        Scanner scan = new Scanner(System.in);

        System.out.printf("Enter teacherId: ");
        teacherId = scan.nextLine();

        System.out.printf("Enter a firstname: ");
        firstName = scan.nextLine();

        System.out.printf("Enter a lastname: ");
        lastName = scan.nextLine();

        System.out.printf("Enter a email: ");
        email = scan.nextLine();

        System.out.printf("Enter a password: ");
        password = scan.nextLine();

        Connection connect = null;
        PreparedStatement insertData = null;

        try {
            connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            insertData = connect.prepareStatement("INSERT INTO ogretimUyesi VALUES(?,?,?,?,?,?)");
            insertData.setString(1, getTeacherId());
            insertData.setString(2, getFirstName());
            insertData.setString(3, getLastName());
            insertData.setString(4, getEmail());
            insertData.setString(5, Hash(getPassword()));
            insertData.setString(6, getUniversity());

            insertData.execute();

            System.out.println("Üyeliğiniz başarıyla gerçekleşti!!");

            Otomasyon.main(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Otomasyon.main(null);

    }

    @Override
    public void girisYap() {
        Scanner scan = new Scanner(System.in);

        System.out.printf("Enter teacherId: ");
        teacherId = scan.nextLine();

        System.out.printf("Enter a password: ");
        password = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM ogretimUyesi");
            ResultSet rs = showData.executeQuery();

            while (rs.next()) {

                if (rs.getString(1).equals(teacherId) && rs.getString(5).equals(Hash(password))) {
                    girisYap = true;
                    break;
                }

            }
            if (girisYap == false) {
                System.out.println("Giriş başarısız oldu!!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    void notGir() {
        Scanner sc = new Scanner(System.in);

        System.out.printf("Dersi alan öğrenci sayısını giriniz: ");
        int x = sc.nextInt(); //Dersi alan öğrenci sayısı
        sc.nextLine();

        System.out.printf("Dersin adını giriniz: ");
        lecture = sc.nextLine();

        System.out.printf("Dersin kodunu giriniz: ");
        lectureId = sc.nextLine();

        for (int i = 1; i <= x; i++) {

            System.out.printf("Öğrencinin numarasını giriniz: ");
            studentId = sc.nextLine();

            System.out.printf("Öğrencinin notunu giriniz: ");
            studentNote = sc.nextLine();

            Connection connect = null;
            PreparedStatement insertData = null;

            try {
                connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
                insertData = connect.prepareStatement("INSERT INTO notlar VALUES(?,?,?,?,?)");
                insertData.setString(1, getLecture());
                insertData.setString(2, getLectureId());
                insertData.setString(3, getStudentId());
                insertData.setString(4, getStudentNote());
                insertData.setString(5, null);

                insertData.execute();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        System.out.println("Notlar başarıyla girildi!!!!");

        geriDon();

    }

    @Override
    void ogrenciNotGoruntule() {
        boolean dersleriGor = false;

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM notlar");
            ResultSet myResult = showData.executeQuery();

            System.out.printf("%-20s%-20s%-20s%-20s%-20s%n", "LectureName", "LectureId", "StudentId", "StudentNote", "LetterGrade");

            while (myResult.next()) {
                dersleriGor = true;
                System.out.printf("%-20s%-20s%-20s%-20s%-20s%n", myResult.getString(1), myResult.getString(2),
                        myResult.getString(3), myResult.getString(4), myResult.getString(5));
            }
            if (dersleriGor == false) {
                System.out.println("Dersler görüntülenemedi!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        geriDon();
    }

    @Override
    void yoklama() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Dersin adını girin: ");
        lecture = sc.nextLine();

        System.out.print("Dersin kodunu girin: ");
        lectureId = sc.nextLine();

        System.out.println("Dersi alan öğrenci sayısını girin: ");
        int number = sc.nextInt();

        sc.nextLine();

        for (int i = 1; i <= number; i++) {

            System.out.printf("Öğrencinin numarasını giriniz: ");
            studentId = sc.nextLine();

            System.out.printf("Öğrencinin durumunu( + veya - olarak ) giriniz: ");
            durum = sc.nextLine();

            Connection connect = null;
            PreparedStatement insertData = null;

            try {
                connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
                insertData = connect.prepareStatement("INSERT INTO yoklama VALUES(?,?,?,?)");
                insertData.setString(1, getStudentId());
                insertData.setString(2, getLecture());
                insertData.setString(3, getLectureId());
                insertData.setString(4, getDurum());

                insertData.execute();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        System.out.println("Yoklama alındi!!!");

        geriDon();
    }

    @Override
    void geriDon() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Not girmek için 1'e basın: ");
        System.out.println("Öğrenci notları görüntülemek için 2'e basın: ");
        System.out.println("Yoklama almak için 3'e basın: ");
        System.out.println("Çıkmak için 0'a basın: ");

        int number6 = sc.nextInt();

        if (number6 == 1) {
            notGir();
        } else if (number6 == 2) {
            ogrenciNotGoruntule();
        } else if (number6 == 3) {
            yoklama();
        } else if (number6 == 0) {
            System.out.println("Çıktınız!!!");
        } else {
            System.out.println("Geçersiz seçim girdiniz!!!");
        }
    }

}
