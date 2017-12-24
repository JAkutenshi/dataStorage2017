import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Polynom a = new Polynom(13);
        Polynom b = new Polynom(65);
        Polynom c = new Polynom(29);

        //System.out.println(GaluaField.GFMul(a, b));
        //System.out.println(GaluaField.GFMul(b, c));

        System.out.println(a + " ^ " + 22);
        //System.out.println(GaluaField.GFPow(a, 21));
        //System.out.println(GaluaField.GFMul(a, a));
        System.out.println(GaluaField.GFPow(a, -1));
        //System.out.println(GaluaField.GFPowM(b, 22));
        //System.out.println(GaluaField.GFPowM(c, 22));
    }
}
