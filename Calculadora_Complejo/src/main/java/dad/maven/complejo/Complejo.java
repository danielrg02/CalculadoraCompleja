public class Complejo {
    private DoubleProperty real = new SimpleDoubleProperty();
    private DoubleProperty imaginario = new SimpleDoubleProperty();

    public DoubleProperty realProperty() {
        return real;
    }

    public DoubleProperty imaginarioProperty() {
        return imaginario;
    }
}