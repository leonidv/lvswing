package com.vygovskiy.controls.fileedit;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Javabeans component for input file name.
 * 
 * @author Leonid Vygovskiy
 * 
 */
public class FileEdit extends JPanel {
    private static final long serialVersionUID = 9166564872030028096L;

    /**
     * Dialog file chooser that showed whin {@link #button} is clicked.
     */
    protected JFileChooser fileChooser = new JFileChooser();

    /**
     * Textfield with file name.
     */
    protected JTextField textField;

    /**
     * Button for show file chooser.
     */
    protected JButton button;

    /**
     * Position of button relative {@link #textField}.
     */
    private ButtonPosition buttonPosition = ButtonPosition.East;

    /**
     * Create object without selected file.
     * <P>
     * Start directory is user home.
     */
    public FileEdit() {
        this(new File(System.getProperty("user.home")));
        init();
    }

    /**
     * Create object without selected file.
     * 
     * @param startDirectory
     *            default directory in JFileChooser.
     */
    public FileEdit(File startDirectory) {
        fileChooser = new JFileChooser(startDirectory);
        init();
    }

    /**
     * Create component and set given file name.
     * 
     * @param fileName
     */
    public FileEdit(String fileName) {
        this();
        setFileName(fileName);
    }

    /**
     * Create and init components on panel.
     */
    private void init() {
        super.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setColumns(20);
        button = new JButton("Select file...");

        add(textField, BorderLayout.CENTER);
        add(button, buttonPosition.getBorderLayoutConstant());

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fireFileSelectedEvent();
                }
            }
        });

        // setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    /**
     * Show fileChooser dialoger.
     */
    private void showDialog() {
        if (!textField.getText().trim().isEmpty()) {
            fileChooser.setSelectedFile(getFile());
        }

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File file;
                file = fileChooser.getSelectedFile();
                setFileName(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return button that showing file chooser.
     * 
     * @return
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Return file chooser.
     * 
     * @return
     */
    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Return text field with selected file name.
     * 
     * @return
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Return content of text field.
     * 
     * @return
     * @see #getFile()
     */
    public String getFileName() {
        return textField.getText();
    }

    /**
     * Set value of text field and send {@link FileSelectedEvent}.
     * 
     * @param fileName
     */
    public void setFileName(String fileName) {
        textField.setText(fileName);

        fireFileSelectedEvent();
    }

    /**
     * Return selected file.
     * 
     */
    public File getFile() {
        File file = new File(getFileName());
        return file;
    }

    /**
     * Return button position relative text field with file name.
     * 
     * @return
     */
    public ButtonPosition getButtonPosition() {
        return buttonPosition;
    }

    /**
     * Set new button position relative to text field with file name.
     * 
     * @param buttonPosition
     */
    public void setButtonPosition(ButtonPosition buttonPosition) {
        // Если пользователь указал старую позицию, ничего делать не будет.
        if (this.buttonPosition == buttonPosition) {
            return;
        }

        this.buttonPosition = buttonPosition;

        remove(button);
        add(button, this.buttonPosition.getBorderLayoutConstant());
    }

    /**
     * Set button text.
     * 
     * @param text
     */
    public void setButtonText(String text) {
        button.setText(text);
    }

    /**
     * Return button title.
     * 
     * @return
     */
    public String getButtonText() {
        return button.getText();
    }

    /**
     * This component required only {@link BorderLayout} manager. You shouldn't
     * use this method.
     * 
     * @throws IllegalArgumentException
     *             if mgr is not instance of {@link BorderLayout}.
     */
    @Override
    @Deprecated
    final public void setLayout(LayoutManager mgr) {
        super.setLayout(new BorderLayout());
    }

    public void addFileSelectedListener(FileSelectedListener l) {
        listenerList.add(FileSelectedListener.class, l);
    }

    public void removeFileSelectedListener(FileSelectedListener l) {
        listenerList.remove(FileSelectedListener.class, l);
    }

    public FileSelectedListener[] getFileSelectedListeners() {
        return (FileSelectedListener[]) listenerList
                .getListeners(FileSelectedListener.class);
    }

    /**
     * It's fire whene file name is changed.
     */
    private void fireFileSelectedEvent() {
        FileSelectedEvent event = new FileSelectedEvent(this);
        for (FileSelectedListener listener : getFileSelectedListeners()) {
            listener.fileSelected(event);
        }
    }

}
