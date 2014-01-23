package org.kevoree.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.kevoree.idea.utils.KevoreeMavenResolver;
import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by duke on 23/01/2014.
 */
public class OpenJavaKevoreeEditor extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        File kevoreeEditor = KevoreeMavenResolver.resolve("org.kevoree.tools", "org.kevoree.tools.ui.editor", "release", "jar");
        if (kevoreeEditor != null) {
            System.out.println(kevoreeEditor.getAbsolutePath());
        } else {
           System.out.println("Editor Jar not resolved");
        }
    }
}
