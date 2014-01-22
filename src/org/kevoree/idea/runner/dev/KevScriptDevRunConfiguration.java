package org.kevoree.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kevoree.idea.runner.prod.KevScriptRunConfiguration;

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
}
