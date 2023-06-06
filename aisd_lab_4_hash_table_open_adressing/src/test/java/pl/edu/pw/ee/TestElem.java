package pl.edu.pw.ee;

public class TestElem implements Comparable<TestElem> {

    public int value;

    public TestElem(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (anotherObject instanceof TestElem) {
            if (((TestElem) (anotherObject)).value == this.value) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(TestElem anotherObject) {
        if (anotherObject.value == this.value) {
            return 0;
        } else if (anotherObject.value >= this.value) {
            return -1;
        } else {
            return 1;
        }
    }
}
