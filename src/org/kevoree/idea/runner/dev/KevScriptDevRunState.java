package org.kevoree.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.compiler.CompilerPaths;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import org.kevoree.resolver.MavenResolver;
import org.kevoree.idea.runner.prod.KevScriptRunConfiguration;
import org.kevoree.idea.runner.prod.KevScriptRunState;
import org.kevoree.idea.utils.KevoreeMavenResolver;

import java.io.File;

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
        //common params
        parameters.getClassPath().add(kevoreeBase);
        parameters.getVMParametersList().add("-Dkevoree.dev=true");
        parameters.getVMParametersList().add("-Dnode.name=" + runConfig.nodeName);
        parameters.getVMParametersList().add("-Dnode.bootstrap=" + runConfig.kevsFile.getPath());
        parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);
        parameters.setJdk(SDK);

        File kevoreeAnnotator = KevoreeMavenResolver.resolve("org.kevoree.tools", "org.kevoree.tools.annotator.standalone", runConfig.kevoreeRuntimeVersion, "jar");
        if (kevoreeAnnotator == null || (CompilerPaths.getModuleOutputDirectory(module, false) == null)) {
            parameters.setMainClass("org.kevoree.platform.standalone.App");
        } else {
            parameters.getClassPath().add(kevoreeAnnotator);
            parameters.setMainClass("org.kevoree.tools.annotator.App");
            parameters.getProgramParametersList().add(CompilerPaths.getModuleOutputDirectory(module, false).getPath());
            parameters.getProgramParametersList().add("org.kevoree.platform.standalone.App");
        }

        return parameters;
    }

}
