package org.kevoree.idea.actions;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateTemplateInPackageAction;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
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
public class NewKevScriptFileAction extends CreateTemplateInPackageAction<PsiElement> implements DumbAware {

    public NewKevScriptFileAction() {
        super(KevBundle.message("new.kevs.file"),
                KevBundle.message("new.kevs.file.description"),
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
        KevTemplatesFactory.Template template = KevTemplatesFactory.Template.KevScriptFile;
        String fileName = fileNameFromTypeName(typeName, parameterName);
        String packageName = packageNameFromTypeName(typeName, parameterName);
        return KevTemplatesFactory.createFromTemplate(psiDirectory, packageName, fileName, template);
    }

    String packageNameFromTypeName(String typeName, String parameterName) {
        if (typeName.startsWith("lib.")) {
            return typeName.replaceFirst("^lib\\.", "");
        }
        return StringUtil.getPackageName(parameterName, '.');
    }

    String fileNameFromTypeName(String typeName, String parameterName) {
        return parameterName + ".kevs";
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        builder.addKind("New KevScript", KevIcons.KEVS_ICON_16x16, "main.kevs");
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s, String s2) {
        return KevBundle.message("new.kevs.lib.action.text");
    }

    protected boolean isAvailable(final DataContext dataContext) {
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (project == null || view == null || view.getDirectories().length == 0) {
            return false;
        }
        return true;
    }

}
