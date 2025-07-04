package com.rpgmanager.utils;

import com.rpgmanager.controllers.MainController;

public interface MainAware {
    void setMainController(MainController mainController);
    void initData();
}
