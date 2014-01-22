package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.GenericProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kevoree.tools.kevscript.idea.runner.prod.KevScriptProgramRunner;

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
}
