package com.darkyver.javafxview.controllersView;

import com.darkyver.config.Config;

public abstract class AbstractController {
    private static final Config config = new Config();

    protected Config getConfig() {
        return config;
    }
}
