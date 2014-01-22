package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;
import org.kevoree.tools.kevscript.idea.KevIcons;

import javax.swing.*;

/**
 * Created by duke on 16/01/2014.
 */
public class KevScriptRunConfigurationType implements ConfigurationType {

    private ConfigurationFactory[] configurationFactories = new ConfigurationFactory[1];

    public KevScriptRunConfigurationType() {
        configurationFactories[0] = new KevScriptRunConfigurationFactory(this);
    }

    @Override
    public String getDisplayName() {
        return "KevScript Run Dev";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "KevScript Dev Runner";
    }

    @Override
    public Icon getIcon() {
        return KevIcons.KEVS_ICON_16x16;
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
