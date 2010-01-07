package com.vygovskiy.controls.fileedit;

import java.util.EventListener;

public interface FileSelectedListener extends EventListener {    
    public abstract void fileSelected(FileSelectedEvent e);
}
