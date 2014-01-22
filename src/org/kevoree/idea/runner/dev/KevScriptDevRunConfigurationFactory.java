package org.kevoree.idea.runner.dev;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.kevoree.idea.runner.prod.KevScriptRunConfigurationFactory;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptDevRunConfigurationFactory extends KevScriptRunConfigurationFactory {

    public KevScriptDevRunConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public String getName() {
        return "Run (Dev Mode)";
    }

    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new KevScriptDevRunConfiguration(project, this, "KevScriptDevRunnerTemplate");
    }
}
