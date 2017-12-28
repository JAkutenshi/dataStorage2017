import java.util.ArrayList;

/**
 * Created by jakutenshi on 12/25/17.
 */
public class Raid6 {
    private ArrayList<Polynom> disks = new ArrayList<>();
    private Polynom S1;
    private Polynom S2;

    public Raid6() {
        for (int i = 1; i <= 10; i++) {
            disks.add(new Polynom(i));
        }
        computeSyndromes();
    }

    public Raid6(int[] data) {
        for (int i = 0; i < data.length; i++) {
            disks.add(new Polynom(data[i]));
        }

        computeSyndromes();
    }

    private Polynom computeS1(ArrayList<Polynom> disks) {
        Polynom S1 = new Polynom(0);
        for (Polynom disk : disks)
            S1 = GaluaField.GFSum(S1, disk);

        return S1;
    }

    private Polynom computeS2(ArrayList<Polynom> disks) {
        Polynom S2 = disks.get(0);
        Polynom x, ind;
        for (int i = 0; i < disks.size(); i++) {
            x = GaluaField.GFMulX(S2);
            ind = disks.get(i);
            S2 = GaluaField.GFSum(x, ind);
        }
        return S2;
        /*Polynom S2 = new Polynom(0);
        Polynom x, ind;
        for (int i = 0; i < disks.size(); i++) {
            x = GaluaField.GFPowX(disks.size() - i - 1);
            ind = disks.get(i);
            S2 = GaluaField.GFSum(S2, GaluaField.GFMul(ind, x));
        }
        return S2;*/
    }

    private void computeSyndromes() {
        this.S1 = computeS1(this.disks);
        this.S2 = computeS2(this.disks);
    }

    public Polynom recoveryS1(int index) {
        Polynom result = new Polynom(0);
        for (int i = 0; i < disks.size(); i++) {
            if (i != index) {
                result = GaluaField.GFSum(disks.get(i), result);
            }
        }
        result = GaluaField.GFSum(S1, result);

        return result;
    }

    public Polynom recoveryS2(int index) {
        ArrayList<Polynom> brokenDisks = ((ArrayList<Polynom>) disks.clone());
        brokenDisks.set(index, new Polynom(0));

        Polynom tmpS2 = computeS2(brokenDisks);
        Polynom x = GaluaField.GFPowX(disks.size() - index - 1);
        return GaluaField.GFDiv(GaluaField.GFSum(tmpS2, this.S2), x);
    }

    public Polynom[] recovery(int[] indexes) {
        Polynom[] results = new Polynom[2];

        ArrayList<Polynom> brokenDisks = ((ArrayList<Polynom>) disks.clone());
        brokenDisks.set(indexes[0], new Polynom(0));
        brokenDisks.set(indexes[1], new Polynom(0));

        Polynom tmpS1 = computeS1(brokenDisks);
        Polynom tmpS2 = computeS2(brokenDisks);
        Polynom tmpX;

        results[0] = GaluaField.GFSum(S2, tmpS2); // S2 + S2`
        tmpX = GaluaField.GFPowX(-(brokenDisks.size() - indexes[1] - 1)); // x^(-(n - k - 1))
        results[0] = GaluaField.GFMul(results[0], tmpX); // (S2 + S2`) * x^(-(n - k - 1))
        results[0] = GaluaField.GFSum(results[0], S1);
        results[0] = GaluaField.GFSum(results[0], tmpS1);
        tmpX = GaluaField.GFPowX(indexes[1] - indexes[0]);
        tmpX = GaluaField.GFSum(tmpX, new Polynom(1));
        results[0] = GaluaField.GFDiv(results[0], tmpX);

        results[1] = GaluaField.GFSum(S1, tmpS1);
        results[1] = GaluaField.GFSum(results[0], results[1]);

        return results;
    }

    public int silentCorruptionDetection() {
        Polynom tmpS1 = computeS1(disks);
        Polynom tmpS2 = computeS2(disks);

        if (tmpS1.getIntRepr() == S1.getIntRepr() && tmpS2.getIntRepr() == S2.getIntRepr()) {
            return -1;
        }

        int corruption = disks.size() - 1;
        Polynom S1Sum = GaluaField.GFSum(tmpS1, S1);
        Polynom S2Sum = GaluaField.GFSum(tmpS2, S2);
        Polynom division = GaluaField.GFDiv(S2Sum, S1Sum);
        corruption = corruption - GaluaField.GFLog(division, new Polynom(2));

        disks.set(corruption, recoveryS1(corruption));

        return corruption;
    }

    public void setDataDisk(int index, Polynom data) {
        this.disks.set(index, data);
    }
}
