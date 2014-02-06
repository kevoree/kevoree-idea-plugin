package org.kevoree.idea.runner.prod;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;
import org.kevoree.idea.KevIcons;

import javax.swing.*;

/**
 * Created by duke on 16/01/2014.
 */
public class KevScriptRunConfigurationType implements ConfigurationType {

    private ConfigurationFactory[] configurationFactories = new ConfigurationFactory[1];

    public KevScriptRunConfigurationType() {
        configurationFactories[0] = new KevScriptRunConfigurationFactory(this);
        //configurationFactories[1] = new KevScriptDevRunConfigurationFactory(this);
    }

    @Override
    public String getDisplayName() {
        return "KevScript Run (Production Only)";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Kevoree Script Runner Prod";
    }

    @Override
    public Icon getIcon() {
        return KevIcons.KEVS_ICON_16x16;
    }

    @NotNull
    @Override
    public String getId() {
        return "kevsrunner_prod";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return configurationFactories;
    }
}
