package me.jrayn.bootstrap.sys;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

public enum OS {
    WIN, MAC, UNIX;

    /**
     * Get the system, will check if windows or mac, otherwise
     * it will just return unix
     *
     * @return the system type
     */
    public static OS getOS() {
        if (SystemUtils.IS_OS_WINDOWS)
            return WIN;
        if (SystemUtils.IS_OS_MAC)
            return MAC;
        return UNIX;
    }

    /**
     * Get the correct folder based on the operating system
     * for the working folder
     *
     * @return working directory
     */
    public static String getOSDir() {
        switch (getOS()) {
            case WIN:
                return System.getenv("AppData");
            case UNIX:
                return System.getProperty("user.home");
            case MAC:
                return System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support";
        }
        return System.getProperty("user.dir");
    }

}
