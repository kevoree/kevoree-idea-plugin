package org.kevoree.idea.runner.dev;

import com.intellij.conversion.impl.RunManagerSettingsImpl;
import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.tools.ToolBeforeRunTask;
import com.intellij.tools.ToolBeforeRunTaskProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kevoree.idea.runner.prod.KevScriptRunConfiguration;

import java.util.List;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptDevRunConfiguration extends KevScriptRunConfiguration {

    protected KevScriptDevRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);

    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new KevScriptDevRunConfigurationSettingsEditor(getProject());
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new KevScriptDevRunState(executionEnvironment);
    }


    @Override
    public void checkSettingsBeforeRun() throws RuntimeConfigurationException {
        super.checkSettingsBeforeRun();
    }

    @Override
    public void onNewConfigurationCreated() {
        
    }
}
