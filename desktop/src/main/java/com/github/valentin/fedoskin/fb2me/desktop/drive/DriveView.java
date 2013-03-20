package com.github.valentin.fedoskin.fb2me.desktop.drive;

/**
 * Utility methods to print to the command line.
 * 
 * @author rmistry@google.com (Ravi Mistry)
 */
public class DriveView {

    static void header1(String name) {
        System.out.println();
        System.out.println("================== " + name + " ==================");
        System.out.println();
    }

    static void header2(String name) {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~ " + name + " ~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
}
