package com.vygovskiy.controls.fileedit;

import java.util.EventListener;

public abstract class FileSelectedListener implements EventListener {    
    public abstract void fileSelected(FileSelectedEvent e);
}
