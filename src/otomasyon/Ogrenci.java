package otomasyon;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

abstract class Ogr {

    void dersPrograminiGoruntule() {
    }

    ;
    void dersSecme() {
    }

    ;
    void secilenDersleriGor() {
    }

    ;
    void notGoruntule() {
    }

    ;
    void geriDon() {
    }
;

}

interface Ogren {

    void uyeOl();

    void girisYap();
}

public class Ogrenci extends Ogr implements Ogren {

    private String firstName;
    private String lastName;
    private String studentId;
    private String email;
    private String password;
    private String lecture;
    private String lectureId;
    private static String university = "Istanbul University";  //static variable
    public boolean dersSec = false;

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public static String getUniversity() {          //static method
        return university;
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

        System.out.printf("Enter studentid: ");
        studentId = scan.nextLine();

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
            insertData = connect.prepareStatement("INSERT INTO ogrenci VALUES(?,?,?,?,?,?)");
            insertData.setString(1, getStudentId());
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

    }

    @Override
    public void girisYap() {
        Scanner scan = new Scanner(System.in);

        System.out.printf("Enter studentId: ");
        studentId = scan.nextLine();

        System.out.printf("Enter a password: ");
        password = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM ogrenci");
            ResultSet rs = showData.executeQuery();

            while (rs.next()) {

                if (rs.getString(1).equals(studentId) && rs.getString(5).equals(Hash(password))) {
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
    void dersPrograminiGoruntule() {
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM dersProgrami");
            ResultSet myResult = showData.executeQuery();

            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", "Period", "LectureName", "Lectureld", "TeacherName", "LectureDay", "LectureTime");
            while (myResult.next()) {

                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", myResult.getString(1), myResult.getString(2),
                        myResult.getString(3), myResult.getString(4), myResult.getString(5), myResult.getString(6));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        geriDon();
    }

    @Override
    void dersSecme() {
        Scanner scan = new Scanner(System.in);

        System.out.printf("Enter a lecture name: ");
        lecture = scan.nextLine();

        System.out.printf("Enter a lecture id: ");
        lectureId = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM dersProgrami");
            ResultSet rs = showData.executeQuery();

            while (rs.next()) {

                if (rs.getString(2).equals(lecture) && rs.getString(3).equals(lectureId)) {
                    dersSec = true;
                    break;
                }

            }
            if (dersSec == false) {
                System.out.println("Ders seçimi başarısız oldu!!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connect = null;
        PreparedStatement insertData = null;

        if (dersSec == true) {
            try {
                connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
                insertData = connect.prepareStatement("INSERT INTO secilenDersler VALUES(?,?,?)");
                insertData.setString(1, getStudentId());
                insertData.setString(2, getLecture());
                insertData.setString(3, getLectureId());

                insertData.execute();

                System.out.println("Ders seçimi başarılı.");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        geriDon();

    }

    @Override
    void secilenDersleriGor() {
        boolean dersleriGor = false;

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM secilenDersler");
            ResultSet myResult = showData.executeQuery();

            System.out.printf("%-20s%-20s%n", "LectureName", "LectureId");

            while (myResult.next()) {
                if (myResult.getString(1).equals(studentId)) {
                    dersleriGor = true;
                    System.out.printf("%-20s%-20s%n", myResult.getString(2), myResult.getString(3));
                }
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
    void notGoruntule() {

        boolean notGor = false;

        System.out.printf("%-20s%-20s%-20s%-20s%-20s%n", "LectureName", "Lectureld", "StudentId", "StudentNote", "LetterGrade");

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM notlar");
            ResultSet myResult = showData.executeQuery();

            while (myResult.next()) {
                if (myResult.getString(3).equals(studentId)) {
                    notGor = true;
                    System.out.printf("%-20s%-20s%-20s%-20s%-20s%n", myResult.getString(1), myResult.getString(2), myResult.getString(3), myResult.getString(4),
                            myResult.getString(5));

                }
            }
            if (notGor == false) {
                System.out.println("Notlar görüntülenemedi!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        geriDon();

    }

    @Override
    void geriDon() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ders programını görmek için 1'e basın: ");
        System.out.println("Ders seçmek için 2'e basın: ");
        System.out.println("Seçilen dersleri görüntülemek için 3'e basın: ");
        System.out.println("Notları görmek için 4'e basın: ");
        System.out.println("Çıkmak için 0'a basın: ");

        int number6 = sc.nextInt();

        if (number6 == 1) {
            dersPrograminiGoruntule();
        } else if (number6 == 2) {
            dersSecme();
        } else if (number6 == 3) {
            secilenDersleriGor();
        } else if (number6 == 4) {
            notGoruntule();
        } else if (number6 == 0) {
            System.out.println("Çıktınız!!!");
        } else {
            System.out.println("Geçersiz seçim girdiniz!!!");
        }
    }

}
