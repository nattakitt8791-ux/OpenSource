import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EarthquakeDamage {

    static Scanner sc;

    // อ่านตัวเลข คืนค่า null ถ้าพิมพ์ q, วนถามซ้ำถ้าไม่ถูกต้อง
    static Double readNumber(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                return null;
            }

            try {
                double num = Double.parseDouble(input.replace(",", ""));
                if (num < min || num > max) {
                    System.out.println("ค่าต้องอยู่ระหว่าง " + min + " - " + max + "\n");
                    continue;
                }
                return num;
            } catch (NumberFormatException e) {
                System.out.println("กรุณากรอกเป็นตัวเลข\n");
            }
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        sc = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("=== โปรแกรมคำนวณความเสียหายจากแผ่นดินไหว ===");
        System.out.println("(พิมพ์ q เพื่อออกจากโปรแกรม)\n");

        while (true) {
            // ===== ขั้นที่ 1: กรอกริกเตอร์ =====
            Double richterObj = readNumber("กรอกค่าแผ่นดินไหว (ริกเตอร์): ", 0, 10);
            if (richterObj == null) break;

            // ===== ขั้นที่ 2: กรอกมูลค่าทรัพย์สิน =====
            Double valueObj = readNumber("กรอกมูลค่าทรัพย์สิน (บาท): ", 0, 1_000_000_000_000.0);
            if (valueObj == null) break;

            double richter = richterObj;
            double value = valueObj;

            // ===== ขั้นที่ 3: คำนวณ (if-else) =====
            int level;
            String effect;
            double damage;

            if (richter < 2.0) {
                level = 1;  effect = "ไม่รู้สึก วัดได้เฉพาะเครื่องมือ";            damage = 0.0;
            } else if (richter < 3.0) {
                level = 2;  effect = "คนที่อยู่นิ่งๆ บนอาคารสูงรู้สึกเบาๆ";        damage = 0.0;
            } else if (richter < 3.5) {
                level = 3;  effect = "รู้สึกในอาคาร ของแขวนไหวเล็กน้อย";           damage = 1.0;
            } else if (richter < 4.0) {
                level = 4;  effect = "ของในบ้านสั่น ประตูหน้าต่างดัง";             damage = 2.0;
            } else if (richter < 4.5) {
                level = 5;  effect = "คนส่วนใหญ่รู้สึก ของตกแตก";                 damage = 5.0;
            } else if (richter < 5.0) {
                level = 6;  effect = "เฟอร์นิเจอร์ขยับ ผนังร้าวเล็กน้อย";          damage = 10.0;
            } else if (richter < 5.5) {
                level = 7;  effect = "ยืนไม่มั่นคง อาคารไม่แข็งแรงเสียหาย";        damage = 20.0;
            } else if (richter < 6.0) {
                level = 8;  effect = "อาคารทั่วไปเสียหายปานกลาง ปล่องไฟพัง";       damage = 35.0;
            } else if (richter < 6.5) {
                level = 9;  effect = "อาคารเสียหายหนัก รากฐานร้าว ท่อใต้ดินแตก";   damage = 50.0;
            } else if (richter < 7.0) {
                level = 10; effect = "อาคารส่วนใหญ่พัง ดินถล่ม รางรถไฟคด";         damage = 70.0;
            } else if (richter < 8.0) {
                level = 11; effect = "อาคารแทบทั้งหมดพัง สะพานถล่ม";              damage = 90.0;
            } else {
                level = 12; effect = "ทำลายล้างสิ้นเชิง พื้นดินเป็นคลื่น";           damage = 100.0;
            }

            double remaining   = 100.0 - damage;
            double damageCost  = value * damage / 100.0;
            double remainValue = value - damageCost;

            // ===== ขั้นที่ 4: แสดงผล =====
            System.out.println("\n===== ผลการคำนวณ =====");
            System.out.printf("ค่าแผ่นดินไหว       : %.1f ริกเตอร์%n", richter);
            System.out.println("ระดับผลกระทบ        : " + level + " / 12");
            System.out.println("ผลที่ตามมา           : " + effect);
            System.out.println("-----------------------");
            System.out.printf("มูลค่าทรัพย์สิน      : %,.2f บาท%n", value);
            System.out.printf("ความเสียหาย          : %.1f %%%n", damage);
            System.out.printf("สิ่งปลูกสร้างเหลือ    : %.1f %%%n", remaining);
            System.out.println("-----------------------");
            System.out.printf("เสียหายเป็นเงิน      : %,.2f บาท%n", damageCost);
            System.out.printf("มูลค่าที่เหลือ        : %,.2f บาท%n", remainValue);
            System.out.println("=======================\n");
        }

        System.out.println("จบการทำงาน ขอบคุณที่ใช้โปรแกรม");
        sc.close();
    }
}