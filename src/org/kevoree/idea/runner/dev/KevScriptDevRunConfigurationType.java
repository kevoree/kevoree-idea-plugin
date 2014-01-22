package org.kevoree.idea.runner.dev;

import com.intellij.execution.configurations.ConfigurationFactory;
import org.jetbrains.annotations.NotNull;
import org.kevoree.idea.runner.prod.KevScriptRunConfigurationType;

/**
 * Created by duke on 16/01/2014.
 */
public class KevScriptDevRunConfigurationType extends KevScriptRunConfigurationType {

    private ConfigurationFactory[] configurationFactories = new ConfigurationFactory[1];

    public KevScriptDevRunConfigurationType() {
        configurationFactories[0] = new KevScriptDevRunConfigurationFactory(this);
    }

    @Override
    public String getDisplayName() {
        return "KevScript Run (Dev)";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "KevScript Dev Runner";
    }

    @NotNull
    @Override
    public String getId() {
        return "KevscriptDevRunner";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return configurationFactories;
    }
}