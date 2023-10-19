package dad.maven.complejo;

import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;

import javax.script.Bindings;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculadoraComplejos extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculadora de Números Complejos");

        // Crear las propiedades para los números complejos
        Complejo numero1 = new Complejo();
        Complejo numero2 = new Complejo();

        // Crear los controles de la interfaz de usuario
        ComboBox<String> operacionComboBox = new ComboBox<>();
        operacionComboBox.getItems().addAll("+", "-", "*", "/");
        operacionComboBox.setValue("+");

        TextField real1Field = new TextField();
        TextField imaginario1Field = new TextField();
        TextField real2Field = new TextField();
        TextField imaginario2Field = new TextField();
        TextArea resultadoTextArea = new TextArea();

        // Configurar los Bindings para realizar cálculos automáticamente
        DoubleProperty resultadoReal = new SimpleDoubleProperty();
        DoubleProperty resultadoImaginario = new SimpleDoubleProperty();

        Bindings.bindBidirectional(real1Field.textProperty(), numero1.realProperty(), new DoubleStringConverter());
        Bindings.bindBidirectional(imaginario1Field.textProperty(), numero1.imaginarioProperty(), new DoubleStringConverter());
        Bindings.bindBidirectional(real2Field.textProperty(), numero2.realProperty(), new DoubleStringConverter());
        Bindings.bindBidirectional(imaginario2Field.textProperty(), numero2.imaginarioProperty(), new DoubleStringConverter());

        resultadoReal.bind(Bindings.when(operacionComboBox.valueProperty().isEqualTo("+"))
                .then(numero1.realProperty().add(numero2.realProperty()))
                .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("-"))
                        .then(numero1.realProperty().subtract(numero2.realProperty()))
                        .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("*"))
                                .then(numero1.realProperty().multiply(numero2.realProperty()))
                                .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("/"))
                                        .then(numero1.realProperty().multiply(numero2.realProperty()).add(numero1.imaginarioProperty().multiply(numero2.imaginarioProperty()))
                                                .divide(numero2.realProperty().multiply(numero2.realProperty()).add(numero2.imaginarioProperty().multiply(numero2.imaginarioProperty())))
                                        .otherwise(0.0)
                                )
                        )
                )
        );

        resultadoImaginario.bind(Bindings.when(operacionComboBox.valueProperty().isEqualTo("+"))
                .then(numero1.imaginarioProperty().add(numero2.imaginarioProperty()))
                .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("-"))
                        .then(numero1.imaginarioProperty().subtract(numero2.imaginarioProperty()))
                        .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("*"))
                                .then(numero1.realProperty().multiply(numero2.imaginarioProperty()).add(numero1.imaginarioProperty().multiply(numero2.realProperty()))
                                        .otherwise(Bindings.when(operacionComboBox.valueProperty().isEqualTo("/"))
                                                .then(numero1.imaginarioProperty().multiply(numero2.realProperty()).subtract(numero1.realProperty().multiply(numero2.imaginarioProperty()))
                                                        .divide(numero2.realProperty().multiply(numero2.realProperty()).add(numero2.imaginarioProperty().multiply(numero2.imaginarioProperty())))
                                                .otherwise(0.0)
                                        )
                                )
                        )
                )
        );

        resultadoTextArea.textProperty().bind(Bindings.concat(
                "Resultado: (", resultadoReal.asString(), ", ", resultadoImaginario.asString(), ")"));

        // Configurar el diseño de la UI
        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(
                new Label("Operación:"),
                operacionComboBox,
                new Label("Número Complejo 1 (a, b):"),
                new HBox(real1Field, new Label(" + "), imaginario1Field, new Label("i")),
                new Label("Número Complejo 2 (c, d):"),
                new HBox(real2Field, new Label(" + "), imaginario2Field, new Label("i")),
                resultadoTextArea
        );

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class Complejo {
	private DoubleProperty real = new SimpleDoubleProperty();
	private DoubleProperty imaginario = new SimpleDoubleProperty();

	public DoubleProperty realProperty() {
		return real;
	}

	public DoubleProperty imaginarioProperty() {
		return imaginario;
	}
}
