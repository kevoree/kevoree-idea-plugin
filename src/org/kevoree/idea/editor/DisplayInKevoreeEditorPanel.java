package org.kevoree.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by duke on 23/01/2014.
 */
public class DisplayInKevoreeEditorPanel extends AnAction implements DumbAware {
    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        final Presentation presentation = e.getPresentation();
        if(currentFile != null && currentFile.getName().endsWith(".kevs")){
            presentation.setEnabledAndVisible(true);
        } else {
            presentation.setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(anActionEvent.getDataContext());
        KevoreeEditorComponent.getInstance(null);
        JPanel panel = KevoreeEditorComponent.kevoreeEditorPanel;
        try {
            Method m = panel.getClass().getMethod("load",String.class);
            m.invoke(panel,currentFile.getCanonicalPath());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
