package model.diagram.fxgraph.cells;

import javafx.scene.paint.Color;

/**
 * Factory for creating UML classifier cells.
 * Provides a cleaner alternative to multiple similar cell classes.
 */
public class UMLCellFactory {

    public enum ClassifierType {
        CLASS("<<class>>", Color.LIGHTBLUE),
        INTERFACE("<<interface>>", Color.web("#E6F3FF")),
        ENUM("<<enum>>", Color.web("#FFFFCC"));

        private final String stereotype;
        private final Color backgroundColor;

        ClassifierType(String stereotype, Color backgroundColor) {
            this.stereotype = stereotype;
            this.backgroundColor = backgroundColor;
        }

        public String getStereotype() {
            return stereotype;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }
    }

    /**
     * Creates a class cell.
     */
    public static UMLClassifierCell createClass(String className, String[] attributes, String[] methods) {
        return new UMLClassifierCell(className, attributes, methods, ClassifierType.CLASS.getStereotype(), ClassifierType.CLASS.getBackgroundColor());
    }

    /**
     * Creates an interface cell.
     */
    public static UMLClassifierCell createInterface(String interfaceName, String[] attributes, String[] methods) {
        return new UMLClassifierCell(interfaceName, attributes, methods, ClassifierType.INTERFACE.getStereotype(), ClassifierType.INTERFACE.getBackgroundColor());
    }

    /**
     * Creates an enum cell.
     */
    public static UMLClassifierCell createEnum(String enumName, String[] constants, String[] methods) {
        return new UMLClassifierCell(enumName, constants, methods, ClassifierType.ENUM.getStereotype(), ClassifierType.ENUM.getBackgroundColor());
    }

    /**
     * Creates a classifier cell of the specified type.
     */
    public static UMLClassifierCell createClassifier(ClassifierType type, String name, String[] section1, String[] section2) {
        return new UMLClassifierCell(name, section1, section2, type.getStereotype(), type.getBackgroundColor());
    }
}
