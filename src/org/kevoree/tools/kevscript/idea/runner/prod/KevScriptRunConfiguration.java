package org.kevoree.tools.kevscript.idea.runner.prod;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptRunConfiguration extends ModuleBasedConfiguration<KevRunConfigurationModule> {

    public VirtualFile kevsFile;
    private ApplicationConfiguration javaConf;


    protected KevScriptRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(name, new KevRunConfigurationModule(project), factory);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new KevScriptRunConfigurationSettingsEditor();
    }


    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }


    @Nullable
    @Override
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment executionEnvironment) throws ExecutionException {

        return new KevScriptRunState(executionEnvironment);
    }

    @Override
    public Collection<Module> getValidModules() {
        return this.getAllModules();
    }



}
