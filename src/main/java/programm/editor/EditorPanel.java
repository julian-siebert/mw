package programm.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programm.editor.overview.OverviewFrame;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public abstract class EditorPanel extends JPanel {
    protected static final Logger logger = LoggerFactory.getLogger("Panels");
    protected static final GridBagConstraints gbc = new GridBagConstraints();

    protected final Editor editor;
    protected final EditorFrame parent;

    public EditorPanel(EditorFrame parent, LayoutManager layout) {
        super(layout);
        this.editor = parent.editor;
        this.parent = parent;
    }

    protected abstract EditorPanel create();

    protected final JButton createBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener(event -> {
            parent.setVisible(false);
            parent.remove(this);
            new OverviewFrame(this.editor);
        });
        return button;
    }

    protected final JButton createBackButton(Runnable onClick) {
        JButton button = new JButton("Back");
        button.addActionListener(event -> onClick.run());
        return button;
    }
}
