import java.util.ArrayList;

/**
 * Created by jakutenshi on 12/18/17.
 */
public class GaluaField {

    private final static int PRIMITIVE_POLYNOM = 0x11d;
    private final static Polynom primitivePolunom = new Polynom(PRIMITIVE_POLYNOM);
    private final static int primitivePolynomValuableIndex = primitivePolunom.findValuebaleIndex();
    private final static Polynom GF_X = new Polynom(2);

    public static Polynom GFSum(Polynom inFirstPolynom, Polynom inSecondPolynom) {
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

    public static Polynom GFPowX(int power) {
        /*Polynom result = GF_X;
        if (power == 0)
            return new Polynom(1);
        else if (power == 1)
            return result;

        for (int i = 2; i <= power; i++) {
            result = GFMulX(result);
        }*/
        return GFPow(GF_X, power);
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
        if (inFirstPolynom.getIntRepr() == 0 || inSecondPolynom.getIntRepr() == 0) {
            return new Polynom(0);
        }
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

    public static Polynom GFPow(Polynom inPolynom, int power) {

        ArrayList<Polynom> closure = computeClosure(inPolynom);

        int resultIndex = power % (closure.size());
        if (resultIndex < 0) {
            resultIndex = closure.size() + resultIndex;
        }

        return closure.get(resultIndex);
    }

    public static int GFLog(Polynom inPolynom, Polynom base) {
        ArrayList<Polynom> closure = computeClosure(base);
        for (int i = 0; i < closure.size(); i++) {
            if (closure.get(i).getIntRepr() == inPolynom.getIntRepr())
                return i;
        }

        return -1;
    }

    public static Polynom GFInv(Polynom inPolynom) {
        return GFPow(inPolynom, -1);
    }

    public static Polynom GFDiv(Polynom a, Polynom b) {
        return GFMul(a, GFInv(b));
    }



}
