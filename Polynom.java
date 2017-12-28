/**
 * Created by jakutenshi on 12/18/17.
 */
public class Polynom implements Comparable<Polynom> {
    public static final int BASE = 20;
    private int[] factorsRepr = new int[BASE];
    private int intRepr = 0;

    private int[] intToFactors(int in) {
        int[] out = new int[BASE];
        for (int i = 0; i < BASE; i++) {
            out[BASE - i - 1] = in % 2;
            in = in / 2;
        }
        return out;
    }

    private int factorsToInt(int[] in) {
        int out = 0;
        for (int i = 0; i < BASE; i++) {
            out += in[BASE - i - 1] << i;
        }
        return out;
    }

    public Polynom() {

    }

    public Polynom(int in) {
         intRepr = in;
         factorsRepr = intToFactors(in);
    }

    public Polynom(int[] in) {
        intRepr = factorsToInt(in);
        factorsRepr = in;
    }

    public int[] getFactorsRepr() {
        return factorsRepr;
    }

    public void setFactorsRepr(int[] factorsRepr) {
        this.intRepr = factorsToInt(factorsRepr);
        this.factorsRepr = factorsRepr;
    }

    public int getIntRepr() {
        return intRepr;
    }

    public void setIntRepr(int intRepr) {
        this.intRepr = intRepr;
        this.factorsRepr = intToFactors(intRepr);
    }

    public int findValuebaleIndex() {
        int i = 0;
        for (i = 0; i < BASE; i++) {
            if (factorsRepr[i] == 1) {
                break;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(intRepr);
    }

    @Override
    public int compareTo(Polynom o) {
        return this.intRepr - o.getIntRepr();
    }
}
