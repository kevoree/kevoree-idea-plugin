package org.kevoree.idea.runner.dev;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

/**
 * Created by gregory.nain on 23/01/2014.
 */
public class KevAnnotationProcessorBeforeRunTask extends BeforeRunTask {

    public RunConfiguration runConfiguration;
    public Module module;

    protected KevAnnotationProcessorBeforeRunTask(@NotNull Key providerId, RunConfiguration runConfiguration, Module m) {
        super(providerId);
        this.runConfiguration = runConfiguration;
        this.module = m;
        setEnabled(true);
    }



}
