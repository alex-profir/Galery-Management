package com.dosto;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.Node;
import javafx.scene.control.Tab;

class CustomPanel extends JFXTabPane {
    private Tab tab;

    public void setTabContent(Node container) {
        tab.setContent(container);
        getSelectionModel().select(tab);
        requestLayout(); // to force refresh the layout
    }
}