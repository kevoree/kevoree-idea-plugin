package org.kevoree.idea.editor;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.kevoree.idea.utils.KevoreeMavenResolver;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by duke on 24/01/2014.
 */
public class KevoreeEditorComponent extends AbstractProjectComponent {

    protected KevoreeEditorComponent(Project project) {
        super(project);
    }

    public static KevoreeEditorComponent getInstance(final Project project) {
        if (keveditorCL == null) {
            File kevoreeEditor = KevoreeMavenResolver.resolve("org.kevoree.tools", "org.kevoree.tools.ui.editor", "latest", "jar");
            if (kevoreeEditor != null) {
                try {
                    keveditorCL = new URLClassLoader(new URL[]{kevoreeEditor.toURI().toURL()});
                    Class clazz = keveditorCL.loadClass("org.kevoree.tools.ui.editor.panel.KevoreeEditorPanel");
                    kevoreeEditorPanel = (JPanel) clazz.newInstance();
                    com.intellij.openapi.diagnostic.Logger.getInstance(KevoreeEditorComponent.class).info("Load Kevoree Editor");
                } catch (Exception e) {
                    com.intellij.openapi.diagnostic.Logger.getInstance(KevoreeEditorComponent.class).error(e);
                }
            }
        }
        if (project == null) {
            return null;
        } else {
            return project.getComponent(KevoreeEditorComponent.class);
        }
    }

    public static URLClassLoader keveditorCL = null;
    public static JPanel kevoreeEditorPanel;

}
