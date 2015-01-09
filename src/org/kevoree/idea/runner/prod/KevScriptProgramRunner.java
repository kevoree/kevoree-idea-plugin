package org.kevoree.idea.runner.prod;

import com.intellij.debugger.engine.DebuggerUtils;
import com.intellij.debugger.impl.GenericDebuggerRunner;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.*;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.GenericProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by gregory.nain on 20/01/2014.
 */
public class KevScriptProgramRunner extends GenericDebuggerRunner {
    @Nullable
    @Override
    protected RunContentDescriptor doExecute(Project project, RunProfileState runProfileState, RunContentDescriptor runContentDescriptor, ExecutionEnvironment executionEnvironment) throws ExecutionException {
        final JavaCommandLine javaCommandLine = (JavaCommandLine) runProfileState;
        final JavaParameters params = javaCommandLine.getJavaParameters();
        String address = null;
        try {
            for (String s : params.getProgramParametersList().getList()) {
                if (s.startsWith("run-")) {
                    // Application will be run in forked VM
                    address = DebuggerUtils.getInstance().findAvailableDebugAddress(true);
                    params.getProgramParametersList().replaceOrAppend(s, s + " --debug --debugPort=" + address);
                    break;
                }
            }
        } catch (ExecutionException ignored) {
        }

        if (address == null) {
            return super.createContentDescriptor(runProfileState, executionEnvironment);
        }
        RemoteConnection connection = new RemoteConnection(true, "127.0.0.1", address, false);
        return attachVirtualMachine(runProfileState, executionEnvironment, connection, true);
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
        return "KevScript Runner Production Only";
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return runProfile instanceof KevScriptRunConfiguration;
    }
}
