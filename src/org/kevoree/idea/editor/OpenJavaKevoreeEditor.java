package org.kevoree.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by duke on 23/01/2014.
 */
public class OpenJavaKevoreeEditor extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        KevoreeEditorComponent.getInstance(null);
        if (KevoreeEditorComponent.keveditorCL != null) {
            try {
                final Class clazz = KevoreeEditorComponent.keveditorCL.loadClass("org.kevoree.tools.ui.editor.runner.App");
                final Method meth = clazz.getMethod("main", String[].class);
                //TODO inject the selected file
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String[] params = new String[0];
                        try {
                            meth.invoke(null, (Object) params);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            com.intellij.openapi.diagnostic.Logger.getInstance(this.getClass()).error("Editor Jar not resolved");
        }
    }
}
