package org.choongang;

import org.choongang.main.controllers.MainRouter;

public class Application {
    public static void main(String[] args) {
        MainRouter.getInstance().start();
    }
}
