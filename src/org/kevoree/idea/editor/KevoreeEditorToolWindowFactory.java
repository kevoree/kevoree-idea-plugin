package org.kevoree.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.kevoree.idea.utils.KevoreeMavenResolver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by duke on 24/01/2014.
 */
public class KevoreeEditorToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        Component component = toolWindow.getComponent();
        JPanel kevoreeEditorPanel = KevoreeEditorComponent.getInstance(project).kevoreeEditorPanel ;
        if(kevoreeEditorPanel != null){
            component.getParent().add(kevoreeEditorPanel);
        } else {
            component.getParent().add(new JLabel("loading..."));
        }
    }
}
