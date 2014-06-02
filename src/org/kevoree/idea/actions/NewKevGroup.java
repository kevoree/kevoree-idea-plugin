package org.kevoree.idea.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;

/**
 * Created by duke on 16/01/2014.
 */
public class NewKevGroup extends DefaultActionGroup {

    @Override
    public void update(AnActionEvent e)
    {
        super.update(e);
        e.getPresentation().setVisible(true);
    }

}
