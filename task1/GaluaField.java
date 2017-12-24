import java.util.ArrayList;

/**
 * Created by jakutenshi on 12/18/17.
 */
public class GaluaField {

    private final static int PRIMITIVE_POLYNOM = 0x11d;
    private final static Polynom primitivePolunom = new Polynom(PRIMITIVE_POLYNOM);
    private final static int primitivePolynomValuableIndex = primitivePolunom.findValuebaleIndex();

    private static Polynom GFSum(Polynom inFirstPolynom, Polynom inSecondPolynom) {
        int result = inFirstPolynom.getIntRepr() ^ inSecondPolynom.getIntRepr();
        /*System.out.println(
                Integer.toBinaryString(inFirstPolynom.getIntRepr()) + " + \n" +
                Integer.toBinaryString(inSecondPolynom.getIntRepr()) + " = \n" +
                Integer.toBinaryString(result) + "\n"
        );*/
        return new Polynom(result);
    }

    public static Polynom GFMulX(Polynom inPolynom) {
        Polynom result = new Polynom(inPolynom.getIntRepr() << 1);
        if (inPolynom.getFactorsRepr()[0] == 1) {
            result = GFSum(result, primitivePolunom);
        }
        return result;
    }

    private static Polynom GFModF(Polynom inPolynom) {
        /*System.out.println(
                Integer.toBinaryString(inPolynom.getIntRepr()) + " div " +
                Integer.toBinaryString(PRIMITIVE_POLYNOM) + "\n"
        );*/
        int shift = 0;
        Polynom divider;
        while ((shift = primitivePolynomValuableIndex - inPolynom.findValuebaleIndex()) >= 0) {
            divider = new Polynom(PRIMITIVE_POLYNOM << shift );
            inPolynom = GFSum(inPolynom, divider);
        }
        return inPolynom;
    }

    public static Polynom GFMul(Polynom inFirstPolynom, Polynom inSecondPolynom) {
        Polynom result = new Polynom();
        int base = Polynom.BASE;
        for (int i= 0; i < base; i++) {
            if (inSecondPolynom.getFactorsRepr()[base - i - 1] == 0) {
                continue;
            }
            result = GFSum(result, new Polynom(inFirstPolynom.getIntRepr() << i));
        }
        return GFModF(result);
    }

    public static Polynom GWPow(Polynom inPolynom, int power) {
        for (int i = 0; i < power; i++) {
            inPolynom = GFMulX(inPolynom);
        }
        return inPolynom;
    }

    public static Polynom GFLog(Polynom inPolynom, int base) {

        return null;
    }

    public static Polynom GFInv(Polynom inPolynom) {

        return null;
    }

    public static ArrayList<Polynom> getAllPrimitivePolynomes() {

        return null;
    }
}
