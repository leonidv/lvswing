package com.vygovskiy.controls.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.vygovskiy.controls.fileedit.ButtonPosition;
import com.vygovskiy.controls.fileedit.FileEdit;
import com.vygovskiy.controls.fileedit.FileSelectedEvent;
import com.vygovskiy.controls.fileedit.FileSelectedListener;


public class FileEditDemo extends JFrame {
    private static final long serialVersionUID = -3743302935013401956L;

    // Reference to testing component
    FileEdit fileEdit = new FileEdit();

    JLabel labelSelectedFile = new JLabel(" ");

    public FileEditDemo() {
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setMinimumSize(new Dimension(400, 40));
        this.setTitle("FileEdit demo");

        this.add(fileEdit, BorderLayout.NORTH);
        this.add(labelSelectedFile, BorderLayout.SOUTH);
        
        fileEdit.addFileSelectedListener(new FileSelectedListener(){
            @Override
            public void fileSelected(FileSelectedEvent e) {
                labelSelectedFile.setText(e.getSelectedFileName());
                FileEditDemo.this.pack();
            }            
        });
    }

    public static void main(String[] args) {
        FileEditDemo demo = new FileEditDemo();
        demo.setSize(400, 50);
        demo.setTitle("lvControls: FileEdit");
        // Центрируем форму по экрану
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        demo.setLocation((screenSize.width - demo.getWidth()) / 2,
                (screenSize.height - demo.getHeight()) / 3);

        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
    }

} // @jve:decl-index=0:visual-constraint="10,10"
