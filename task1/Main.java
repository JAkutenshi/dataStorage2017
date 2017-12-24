import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Polynom a = new Polynom(13);
        Polynom b = new Polynom(65);
        Polynom c = new Polynom(29);

        System.out.println(GaluaField.GFMul(a, b));
        System.out.println(GaluaField.GFMul(b, c));
    }
}
