package org.kevoree.idea.actions;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateTemplateInPackageAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.kevoree.idea.KevBundle;
import org.kevoree.idea.KevIcons;
import org.kevoree.idea.KevTemplatesFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by duke on 16/01/2014.
 */
public class NewNodeTypeAction extends CreateTemplateInPackageAction<PsiElement> implements DumbAware {

    public NewNodeTypeAction() {
        super(KevBundle.message("new.node.file"),
                KevBundle.message("new.node.file.description"),
                KevIcons.KEVS_ICON_16x16, JavaModuleSourceRootTypes.SOURCES);
    }

    @Nullable
    @Override
    protected PsiElement getNavigationElement(@NotNull PsiElement psiElement) {
        return psiElement;
    }

    @Override
    protected boolean checkPackageExists(PsiDirectory psiDirectory) {
        return true;
    }

    @Nullable
    @Override
    protected PsiElement doCreate(PsiDirectory psiDirectory, String parameterName, String typeName) throws IncorrectOperationException {
        KevTemplatesFactory.Template template = KevTemplatesFactory.Template.KevNodeFile;
        String fileName = fileNameFromTypeName(typeName, parameterName);
        String packageName = packageNameFromTypeName(typeName, parameterName);
        return KevTemplatesFactory.createFromTemplate(psiDirectory, packageName, fileName, template,null);
    }

    String packageNameFromTypeName(String typeName, String parameterName) {
        if (typeName.startsWith("lib.")) {
            return typeName.replaceFirst("^lib\\.", "");
        }
        return StringUtil.getPackageName(parameterName, '.');
    }

    String fileNameFromTypeName(String typeName, String parameterName) {
        return parameterName + ".java";
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        PsiFile childs[] = psiDirectory.getFiles();
        Set<String> packages = new HashSet<String>();
        for (PsiFile child : childs) {
            /*
            if (child instanceof GoFile) {
                GoFile goFile = (GoFile) child;
                if (!goFile.getPackage().isMainPackage()) {
                    packages.add(goFile.getPackage().getPackageName());
                }
            }*/
        }
        builder.addKind("New Kevoree Node", KevIcons.KEVS_ICON_16x16, "main.java");
        /*
        for (String packageName : packages) {
            builder.addKind("New file in library: " + packageName, KevIcons.KEVS_ICON_16x16, "lib." + packageName);
        }*/
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s, String s2) {
        return KevBundle.message("new.node.lib.action.text");
    }
}
