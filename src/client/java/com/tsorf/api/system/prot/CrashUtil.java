package com.tsorf.api.system.prot;

import cc.polymorphism.annot.IncludeReference;
import com.tsorf.common.QuickImports;

@IncludeReference
public class CrashUtil implements QuickImports {
    public static void exit() {
        System.exit(-1);
    }
}
