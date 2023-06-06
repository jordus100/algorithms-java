package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    private double a, b;

    public HashQuadraticProbing() {
        super();
        a = 3.0;
        b = 5.0;
    }

    public HashQuadraticProbing(int size, double a, double b) {
        super(size);
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Hashing constants have to be positive");
        }
        this.a = a;
        this.b = b;
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        int hash = (int) (((key % m) + a * i + b * i * i) % m);

        hash = hash < 0 ? -hash : hash;

        return hash;
    }
}
