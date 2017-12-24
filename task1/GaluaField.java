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

    private static ArrayList<Polynom> computeClosure(Polynom polynom) {
        ArrayList<Polynom> closure = new ArrayList<>();
        closure.add(new Polynom(1)); // 0
        closure.add(polynom);           // 1

        Polynom currentPolynom = polynom;
        do {
            currentPolynom = GFMul(currentPolynom, polynom);
            if (currentPolynom.getIntRepr() == 1) {
                break;
            }
            closure.add(currentPolynom);
        } while (true);

        // printClosure(closure);

        return closure;
    }

    private static void printClosure(ArrayList<Polynom> closure) {
        System.out.println("For " + closure.get(0) + " power = " + closure.size() + " result:");
        for (int i = 0; i < closure.size(); i++) {
            System.out.println(i + ": " + closure.get(i).toString());
        }
    }

    public static Polynom GFPow(Polynom inPolynom, int power) {

        ArrayList<Polynom> closure = computeClosure(inPolynom);

        int resultIndex = power % (closure.size());
        if (resultIndex < 0) {
            resultIndex = closure.size() + resultIndex;
        }

        return closure.get(resultIndex);
    }

    public static int GFLog(Polynom inPolynom, Polynom base) {
        ArrayList<Polynom> closure = computeClosure(inPolynom);
        for (int i = 0; i < closure.size(); i++) {
            if (closure.get(i).getIntRepr() == base.getIntRepr())
                return i;
        }

        return -1;
    }

    public static Polynom GFInv(Polynom inPolynom) {
        return GFPow(inPolynom, -1);
    }

    public static ArrayList<Polynom> getAllPrimitivePolynomes() {

        return null;
    }
}
