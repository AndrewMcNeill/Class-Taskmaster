package ui;

public class DescriptionPane {
    private static DescriptionPane instance;

    public static DescriptionPane getInstance() {
        if (DescriptionPane.instance == null)
            DescriptionPane.instance = new DescriptionPane();
        return DescriptionPane.instance;
    }

    private DescriptionPane() {

    }
}
