package estructuras.lineales;

public class Par {

    private Object a;
    private Object b;

    public Par(Object a, Object b) {
        this.a = a;
        this.b = b;
    }

    public Par() {
        this.a = null;
        this.b = null;
    }

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }

    public Object getB() {
        return b;
    }

    public void setB(Object b) {
        this.b = b;
    }

    public String toString() {
        return "[ " + a.toString() + " | " + b.toString() + " ]";
    }

}
