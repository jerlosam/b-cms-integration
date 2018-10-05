package io.crazy88.beatrix.e2e.rewardmanager.loyalty.utils;

import internal.katana.core.config.PropertiesConfigManager;

public class FileUtils {

    private static final PropertiesConfigManager configManager = new PropertiesConfigManager.PropertiesConfigManagerBuilder().build();

    private FileUtils() {
    }

    public static String getFileName(final String name) {
        final String env = configManager.getStringValue("environment", "mdev");
        return env + "_" + name;
    }
}
