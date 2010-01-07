package com.vygovskiy.controls.fileedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Javabeans, который предоставляет доступ к выбору файла. При выборе файла
 * вызывается событие Action.
 * 
 * @author leonidv
 * 
 */
public class FileEdit extends JPanel {
    private static final long serialVersionUID = 9166564872030028096L;

    // Препочтительные размеры компоненты по ширине
    private static final int PREFERRED_WIDTH = 200;

    // Преподчтительные размеры компоненты по высоте
    private static final int PREFERRED_HEIGHT = 20;

    // Диалог для выбора файла
    protected JFileChooser fileChooser;

    // Поле ввода/отображения выбранного файла
    protected JTextField textField;

    // Кнопка, по которой вызывается каталог выбора файла
    protected JButton button;

    // Расположение кнопки выбора файла относительно поля ввода
    private ButtonPosition buttonPosition = ButtonPosition.East;

    /**
     * Создает и размещает компоненты на панели.
     * 
     */
    private void init() {
        // Инициализирем раскладку
        super.setLayout(new BorderLayout());

        // Создаем компоненты
        textField = new JTextField();
        button = new JButton("Select file...");

        // Создаем диалог выбора файла
        fileChooser = new JFileChooser();

        // Добавляем созданные компоненты на панель
        add(textField, BorderLayout.CENTER);
        add(button, buttonPosition.getBorderLayoutConstant());

        // Настраиваем действие кнопки
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });
        
        // Создаем обработчик нажатия клавиши ENTER в строке ввода имени файла.
        // Нажатием ENTER пользователь указывает на выбор файла.
        textField.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {};
            public void keyPressed(KeyEvent e) {};

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fireFileSelectedEvent();
                }
            }            
        });
    }

    /**
     * Показываем диалого выбора файла. Вызывается по нажатию кнопки. При этом
     * если установлено корретное (существующее) имя файла,
     * 
     * 
     */
    private void showDialog() {
        // Получаем файл, на который указывает компонента
        File file = getFile();

        // Если файл является директорией, устанавливаем его
        if (file.isDirectory()) {
            fileChooser.setSelectedFile(file);
            // Иначе пытаемся установить директорию, в которой лежит файл и
            // установить ее.
        } else if ((file.getParentFile() != null)
                && (file.getParentFile().exists())) {
            fileChooser.setSelectedFile(file.getParentFile());
        }

        // Показываем диалог, и проверяем - выбрал пользователь файл или нет.
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                file = fileChooser.getSelectedFile();
                setFileName(file.getCanonicalPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Создает и инициализирует компоненту.
     * 
     */
    public FileEdit() {
        super(new BorderLayout());
        init();
    }

    /**
     * Создает компоненту у устанавливает путь к файлу.
     * 
     * @param fileName -
     *            заданное имя файла.
     */
    public FileEdit(String fileName) {
        this();
        setFileName(fileName);
    }

    /**
     * Возвращает указатель на кнопку выбора файла
     * 
     * @return кнопка открытия диалога выбора файла.
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Возвращает указатель на диалог выбора файла
     * 
     * @return диалог выбора файла
     */
    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Возвращает текстовое поле, в котором указывается имя файла
     * 
     * @return текстовое поле с именем файла
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Возвращает указанное имя файл, которое берется как строка из текстового
     * поля. Для проверки, корректно указано имя файла или нет используйте метод
     * checkFileName();
     * 
     * @return - возвращает имя файла, на которое указывает строка ввода.
     */
    public String getFileName() {
        return textField.getText();
    }

    /**
     * Устанавливает хранимое имя файла.
     * 
     * @param fileName -
     *            новое имя файла, на который ссылается компонент.
     */
    public void setFileName(String fileName) {
        String oldValue = textField.getText();
        textField.setText(fileName);

        fireFileSelectedEvent();
    }

    /**
     * Возвращает файл, путь к которому указан в поле ввода.
     * 
     * @return - файл, указанный пользователем.
     */
    public File getFile() {
        File file = new File(getFileName());
        return file;
    }

    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }

    /**
     * Возвращает текущее положение кнопки относительно метки
     * 
     * @return положение кнопки относительно поля ввода
     */
    public ButtonPosition getButtonPosition() {
        return buttonPosition;
    }

    /**
     * Устанавливает положение кнопки относительно метки
     * 
     * @param buttonPosition -
     *            новое положение кнопки
     */
    public void setButtonPosition(ButtonPosition buttonPosition) {
        // Если пользователь указал старую позицию, ничего делать не будет.
        if (this.buttonPosition == buttonPosition) {
            return;
        }

        // Запонимаем новую позицию
        this.buttonPosition = buttonPosition;

        // Теперь нужно разместить кнопку на новом месте. Для этого удаляем ее
        // и размещаем заново, с новой позицией.
        remove(button);
        add(button, this.buttonPosition.getBorderLayoutConstant());
    }

    /**
     * Устанавливает заголовок кнопки. В случае, если текст кнопки был изменен
     * напрямую через доступ к переменной кнопки, данное значение теряется.
     * 
     * @param text -
     *            новый текст, отображаемый на кнопке.
     */
    public void setButtonText(String text) {
        button.setText(text);
    }

    /**
     * Возвращает установленный заголовок кнопки.
     * 
     * @return - заголовок кнопки.
     */
    public String getButtonText() {
        return button.getText();
    }

    /**
     * Для корректного позиционировая компонент, требуется чтобы менеджером
     * раскладки обязательно был BorderLayout. В ином случае кидаем исключение
     * {@link IllegalArgumentException}
     * 
     * @param -
     *            экземпляр класса {@link BorderLayout}
     * @throws IllegalArgumentException -
     *             в случае, если менеждер раскладки не является экземпляром
     *             класса BorderLayout.
     */
    @Override
    final public void setLayout(LayoutManager mgr) {
        if (!(mgr instanceof BorderLayout)) {
            throw new IllegalArgumentException(
                    "Only BorderLayout available in file edit");
        } else {
            super.setLayout(mgr);
        }
    }

    /**
     * Добавляет в список слушателей событий слушателя выбора имени файла.
     * 
     * @param l -
     *            слушатель имени файла.
     */
    public void addFileSelectedListener(FileSelectedListener l) {
        listenerList.add(FileSelectedListener.class, l);
    }

    /**
     * Удаляет из списка слушателей событий слушателя выбора имени файла
     * 
     * @param l
     */
    public void removeFileSelectedListener(FileSelectedListener l) {
        listenerList.remove(FileSelectedListener.class, l);
    }

    /**
     * Возвращает массив зарегестрированных слушателей события выбора файла
     * 
     * @return - массив слушателей события выбора файла.
     */
    public FileSelectedListener[] getFileSelectedListeners() {
        return (FileSelectedListener[]) listenerList
                .getListeners(FileSelectedListener.class);
    }

    /**
     * Вызывает событие изменения выбора файла.
     * 
     */
    private void fireFileSelectedEvent() {
        FileSelectedEvent event = new FileSelectedEvent(this);
        for (FileSelectedListener listener : getFileSelectedListeners()) {
            listener.fileSelected(event);
        }
    }

}
