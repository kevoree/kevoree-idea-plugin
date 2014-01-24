package org.kevoree.idea.runner.dev;

import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.eclipse.jdt.internal.compiler.ProcessTaskManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.model.RunnerBuilder;
import org.kevoree.idea.runner.prod.KevScriptProgramRunner;

/**
 * Created by gregory.nain on 20/01/2014.
 */
public class KevScriptDevProgramRunner extends KevScriptProgramRunner {


    @Nullable
    @Override
    protected RunContentDescriptor doExecute(Project project, RunProfileState runProfileState, RunContentDescriptor runContentDescriptor, ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return null;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment environment) throws ExecutionException {
        super.execute(environment);
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment env, @Nullable Callback callback) throws ExecutionException {
        super.execute(env, callback);
    }

    @NotNull
    @Override
    public String getRunnerId() {
        return "KevScript Dev Runner";
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return runProfile instanceof KevScriptDevRunConfiguration;
    }


    @Nullable
    @Override
    public RunnerSettings createConfigurationData(ConfigurationInfoProvider settingsProvider) {
        RunnerSettings runnerSettings = super.createConfigurationData(settingsProvider);

        return runnerSettings;
    }

    @Override
    public void checkConfiguration(RunnerSettings settings, ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws RuntimeConfigurationException {
        super.checkConfiguration(settings, configurationPerRunnerSettings);
    }

    @Override
    public void onProcessStarted(RunnerSettings settings, ExecutionResult executionResult) {
        super.onProcessStarted(settings, executionResult);
    }

    @Nullable
    @Override
    public SettingsEditor getSettingsEditor(Executor executor, RunConfiguration configuration) {
        return super.getSettingsEditor(executor, configuration);
    }




}
