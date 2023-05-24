package xyz.cherish.model;

public class MultiFiledObj {
    public int intFiled;
    public Long longFiled;
    public double doubleFiled;
    public Boolean booleanFiled;

    public String stringFiled;

    public Byte byteFiled;

    public int getIntFiled() {
        return intFiled;
    }

    public void setIntFiled(int intFiled) {
        this.intFiled = intFiled;
    }

    public Long getLongFiled() {
        return longFiled;
    }

    public void setLongFiled(Long longFiled) {
        this.longFiled = longFiled;
    }

    public double getDoubleFiled() {
        return doubleFiled;
    }

    public void setDoubleFiled(double doubleFiled) {
        this.doubleFiled = doubleFiled;
    }

    public Boolean getBooleanFiled() {
        return booleanFiled;
    }

    public void setBooleanFiled(Boolean booleanFiled) {
        this.booleanFiled = booleanFiled;
    }

    public String getStringFiled() {
        return stringFiled;
    }

    public void setStringFiled(String stringFiled) {
        this.stringFiled = stringFiled;
    }

    public Byte getByteFiled() {
        return byteFiled;
    }

    public void setByteFiled(Byte byteFiled) {
        this.byteFiled = byteFiled;
    }

    @Override
    public String toString() {
        return "MultiFiledObj{" +
                "intFiled=" + intFiled +
                ", longFiled=" + longFiled +
                ", doubleFile=" + doubleFiled +
                ", booleanFiled=" + booleanFiled +
                ", stringFiled='" + stringFiled + '\'' +
                ", byteFiled=" + byteFiled +
                '}';
    }
}
