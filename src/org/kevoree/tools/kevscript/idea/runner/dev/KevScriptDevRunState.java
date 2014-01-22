package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import org.kevoree.resolver.MavenResolver;
import org.kevoree.tools.kevscript.idea.runner.prod.KevScriptRunConfiguration;
import org.kevoree.tools.kevscript.idea.runner.prod.KevScriptRunState;
import org.kevoree.tools.kevscript.idea.utils.KevoreeMavenResolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gregory.nain on 20/01/2014.
 */
public class KevScriptDevRunState extends KevScriptRunState {

    protected KevScriptDevRunState(ExecutionEnvironment environment) {
        super(environment);
    }

    private static final MavenResolver resolver = new MavenResolver();

    @Override
    protected JavaParameters createJavaParameters() throws ExecutionException {

        KevScriptRunConfiguration runConfig = ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration());

        Module module = runConfig.getConfigurationModule().getModule();
        JavaParameters parameters = new JavaParameters();
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk SDK = moduleRootManager.getSdk();

        File kevoreeBase = KevoreeMavenResolver.resolve("org.kevoree.platform", "org.kevoree.platform.standalone", runConfig.kevoreeRuntimeVersion, "jar");
        if (kevoreeBase == null) {
            throw new ExecutionException("Unresolved Kevoree Runtime for version:" + runConfig.kevoreeRuntimeVersion);
        }
        parameters.setJdk(SDK);
        parameters.setMainClass("org.kevoree.platform.standalone.App");
        parameters.getClassPath().add(kevoreeBase);
        parameters.getVMParametersList().add("-Dnode.name=node0");
        parameters.getVMParametersList().add("-Dnode.bootstrap=" + runConfig.kevsFile.getPath());
        parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);

        return parameters;
    }

}
