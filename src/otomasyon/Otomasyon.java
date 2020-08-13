package otomasyon;

import java.util.Scanner;

public class Otomasyon {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Üye olmak için 1'e basınız: ");
        System.out.println("Giriş yapmak için 2'ye basınız: ");

        int number1 = scan.nextInt();

        if (number1 == 1) {
            System.out.println("Öğrenci olarak üye olmak için 1'e basınız: ");
            System.out.println("Öğretim üyesi olarak üye olmak için 2'e basınız: ");
            System.out.println("İdari memur olarak üye olmak için 3'e basınız: ");

            int number2 = scan.nextInt();

            if (number2 == 1) {
                Ogrenci o = new Ogrenci();                //öğrenci üye ol
                o.uyeOl();

            } else if (number2 == 2) {
                OgretimUyesi oy = new OgretimUyesi();                //Akademisyen üye ol
                oy.uyeOl();

            } else if (number2 == 3) {
                IdariMemur im = new IdariMemur();                //İdari memur üye ol
                im.uyeOl();

            } else {
                System.out.println("Geçersiz seçim girdiniz!!!!");
            }

        } else if (number1 == 2) {
            System.out.println("Öğrenci olarak giriş yapmak için 1'e basınız: ");
            System.out.println("Öğretim üyesi olarak giriş yapmak için 2'e basınız: ");
            System.out.println("İdari memur olarak giriş yapmak için 3'e basınız: ");

            int number3 = scan.nextInt();

            if (number3 == 1) {
                Ogrenci o = new Ogrenci();
                o.girisYap();                //öğrenci girişi

                if (o.girisYap) {
                    System.out.println("Ders programını görmek için 1'e basın: ");
                    System.out.println("Ders seçmek için 2'e basın: ");
                    System.out.println("Seçilen dersleri görüntülemek için 3'e basın: ");
                    System.out.println("Notları görmek için 4'e basın: ");
                    System.out.println("Çıkmak için 0'a basın: ");

                    int number4 = scan.nextInt();

                    if (number4 == 1) {
                        o.dersPrograminiGoruntule();
                    } else if (number4 == 2) {
                        o.dersSecme();
                    } else if (number4 == 3) {
                        o.secilenDersleriGor();
                    } else if (number4 == 4) {
                        o.notGoruntule();
                    } else if (number4 == 0) {
                        System.out.println("Çıktınız!!!");
                    }
                }

            } else if (number3 == 2) {
                Scanner sc = new Scanner(System.in);
                OgretimUyesi oy = new OgretimUyesi();
                oy.girisYap();                                  //öğretim üyesi girişi

                if (oy.girisYap) {
                    System.out.println("Not girmek için 1'e basın: ");
                    System.out.println("Öğrenci notları görüntülemek için 2'e basın: ");
                    System.out.println("Yoklama almak için 3'e basın: ");
                    System.out.println("Çıkmak için 0'a basın: ");

                    int number = sc.nextInt();

                    if (number == 1) {
                        oy.notGir();
                    } else if (number == 2) {
                        oy.ogrenciNotGoruntule();
                    }
                    else if (number == 3) {
                        oy.yoklama();
                    }
                    else if (number == 0) {
                        System.out.println("Çıktınız!!!");
                    } else {
                        System.out.println("Geçersiz seçim girdiniz!!!");
                    }
                }

            } else if (number3 == 3) {
                Scanner sc = new Scanner(System.in);
                IdariMemur im = new IdariMemur();
                im.girisYap();

                if (im.girisYap) {
                    System.out.println("Harf notu belirlemek için 1'e basın: ");
                    System.out.println("Ders programı hazırlamak için 2'e basın: ");
                    System.out.println("Çıkmak için 0'a basın: ");

                    int number = sc.nextInt();

                    if (number == 1) {
                        im.harfNotuBelirle();
                    } else if (number == 2) {
                        im.dersProgramiHazirla();
                    } else {
                        System.out.println("Geçersiz seçim girdiniz!!!");
                    }
                }
            } else {
                System.out.println("Geçersiz seçim girdiniz!!!!!");
            }
        }

    }

}
