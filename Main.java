import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Polynom a = new Polynom(13);
        Polynom b = new Polynom(65);
        Polynom c = new Polynom(29);

        Raid6 raid = new Raid6(new int[] {1, 2, 6, 4});
        System.out.println(raid.recoveryS1(0));
        System.out.println(raid.recoveryS2(0).getIntRepr());
        int[] indexes = {1, 2};
        Polynom[] results = raid.recovery(indexes);
        System.out.println("D" + indexes[0] + " = " + results[0].getIntRepr());
        System.out.println("D" + indexes[1] + " = " + results[1].getIntRepr());

        raid.setDataDisk(2, new Polynom(0));
        raid.silentCorruptionDetection();
    }
}
