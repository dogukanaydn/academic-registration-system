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

abstract class Idari{
    void harfNotuBelirle(){};
    void dersProgramiHazirla(){};
    void geriDon(){};
}

interface Idareci{
     void uyeOl();
     void girisYap();
}

public class IdariMemur extends Idari implements Idareci{

    private String firstName;
    private String lastName;
    private String memurId;
    private String email;
    private String password;
    private String studentId;
    private String lecture;
    private String lectureId;
    private String letterGrade;
    private String teacherName;
    private String day;
    private String time;
    private String lesson;

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

    public String getMemurId() {
        return memurId;
    }

    public void setMemurId(String memurId) {
        this.memurId = memurId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static String getUniversity() {
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

        System.out.printf("Enter memurId: ");
        memurId = scan.nextLine();

        System.out.printf("Enter a firstname: ");
        firstName = scan.nextLine();

        System.out.printf("Enter a lastname: ");
        lastName = scan.nextLine();

        System.out.printf("Enter a email: ");
        email = scan.nextLine();

        System.out.printf("Enter a password: ");
        password = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement insertData = connect.prepareStatement("INSERT INTO idariMemur VALUES(?,?,?,?,?,?)");
            insertData.setString(1, getMemurId());
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

        System.out.printf("Enter memurId: ");
        memurId = scan.nextLine();

        System.out.printf("Enter a password: ");
        password = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement showData = connect.prepareStatement("SELECT * FROM IdariMemur");
            ResultSet rs = showData.executeQuery();

            while (rs.next()) {

                if (rs.getString(1).equals(memurId) && rs.getString(5).equals(Hash(password))) {
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
    void harfNotuBelirle() {

        Scanner scan = new Scanner(System.in);

        System.out.printf("ders kodu giriniz:");
        lectureId = scan.nextLine();

        System.out.printf("Dersi alan öğrenci sayısını girin: ");
        int num = scan.nextInt();

        scan.nextLine();

        for (int i = 1; i <= num; i++) {
            System.out.printf("öğrencinin numarasını giriniz: ");
            studentId = scan.nextLine();
            try {
                Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");

                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM notlar");

                while (rs.next()) {

                    if (rs.getString(3).equals(studentId)) {

                        int score = Integer.parseInt(rs.getString(4));

                        if (score >= 85) {
                            setLetterGrade("AA");
                        } else if (score >= 75) {
                            setLetterGrade("BA");
                        } else if (score >= 65) {
                            setLetterGrade("BB");
                        } else if (score >= 55) {
                            setLetterGrade("CB");
                        } else if (score >= 40) {
                            setLetterGrade("CC");
                        } else if (score >= 30) {
                            setLetterGrade("DC");
                        } else {
                            setLetterGrade("DD");
                        }

                        PreparedStatement addEntry = connect.prepareStatement("UPDATE notlar SET letterGrade = ? WHERE studentId=? AND lectureId=?");

                        addEntry.setString(1, getLetterGrade());
                        addEntry.setString(2, getStudentId());
                        addEntry.setString(3, getLectureId());

                        addEntry.executeUpdate();
                    }
                    
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

         System.out.println("Harf notu belirlendi!!!!");
         geriDon();
    }

    @Override
    void dersProgramiHazirla() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Kaçıncı dönem olduğunu giriniz: ");
        lesson = scan.nextLine();

        System.out.print("Ders adını girin: ");
        lecture = scan.nextLine();

        System.out.print("Ders kodunu girin: ");
        lectureId = scan.nextLine();

        System.out.print("Dersi veren öğretim üyesinin adını girin: ");
        teacherName = scan.nextLine();

        System.out.print("Günü girin: ");
        day = scan.nextLine();

        System.out.print("Saati girin: ");
        time = scan.nextLine();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/otomasyon", "", "");
            PreparedStatement insertData = connect.prepareStatement("INSERT INTO DersProgrami VALUES(?,?,?,?,?,?)");
            insertData.setString(1, getLesson());
            insertData.setString(2, getLecture());
            insertData.setString(3, getLectureId());
            insertData.setString(4, getTeacherName());
            insertData.setString(5, getDay());
            insertData.setString(6, getTime());

            insertData.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Ders programı hazırlandı!!!!");
        geriDon();
    }

    @Override
    void geriDon() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Harf notu belirlemek için 1'e basın: ");
        System.out.println("Ders programı hazırlamak için 2'e basın: ");
        System.out.println("Çıkmak için 0'a basın: ");

        int number = sc.nextInt();

        if (number == 1) {
            harfNotuBelirle();
        } else if (number == 2) {
            dersProgramiHazirla();
        } else if (number == 0) {
            System.out.println("Çıktınız!!!");
        } else {
            System.out.println("Geçersiz seçim girdiniz!!!");
        }
    }

}
