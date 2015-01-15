package org.kevoree.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineBuilder;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.JavaCommandLineStateUtil;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.compiler.CompilerPaths;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
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
    //private static final MavenResolver resolver = new MavenResolver();

    @Override
    protected JavaParameters createJavaParameters() throws ExecutionException {
        KevScriptRunConfiguration runConfig = ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration());
        Module module = runConfig.getConfigurationModule().getModule();
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk sdk = moduleRootManager.getSdk();

        File kevoreeAnnotator = KevoreeMavenResolver.resolve("org.kevoree.tools", "org.kevoree.tools.annotator", runConfig.kevoreeRuntimeVersion, "jar");
        if (kevoreeAnnotator == null) {
            throw new ExecutionException("Unresolved Kevoree Annotator for version:" + runConfig.kevoreeRuntimeVersion);
        }
        JavaParameters paramAnnotator = new JavaParameters();
        paramAnnotator.setUseDynamicClasspath(false);
        paramAnnotator.setMainClass("org.kevoree.annotator.runner.App");
        paramAnnotator.setWorkingDirectory(CompilerPaths.getModuleOutputDirectory(module, false).getPath());
        paramAnnotator.getClassPath().add(kevoreeAnnotator);
        paramAnnotator.getProgramParametersList().add(kevoreeAnnotator.getPath());
        paramAnnotator.getVMParametersList().add("-Ddev.target.dirs=" + CompilerPaths.getModuleOutputDirectory(module, false).getPath());
        paramAnnotator.configureByModule(module, JavaParameters.CLASSES_ONLY);
        paramAnnotator.getVMParametersList().add("-Ddev.classloader=" + paramAnnotator.getClassPath().getPathsString().replace(kevoreeAnnotator.getPath(),""));
        paramAnnotator.setJdk(sdk);
        GeneralCommandLine gcmd = CommandLineBuilder.createFromJavaParameters(paramAnnotator, getEnvironment().getProject(), false);
        OSProcessHandler handler = JavaCommandLineStateUtil.startProcess(gcmd, true);
        handler.setShouldDestroyProcessRecursively(true);
        try {
            int res = handler.getProcess().waitFor();
            File kevoreeBase = KevoreeMavenResolver.resolve("org.kevoree.platform", "org.kevoree.platform.standalone", runConfig.kevoreeRuntimeVersion, "jar");
            if (kevoreeBase == null) {
                throw new ExecutionException("Unresolved Kevoree Runtime for version:" + runConfig.kevoreeRuntimeVersion);
            }
            if (res == 0) {
                //common params
                JavaParameters parameters = new JavaParameters();
                parameters.getClassPath().add(kevoreeBase);
                parameters.getVMParametersList().add("-Dnode.name=" + runConfig.nodeName);
                parameters.getVMParametersList().add("-Dnode.bootstrap=" + runConfig.kevsFile.getPath());
                parameters.getVMParametersList().add("-Dversion=" + runConfig.kevoreeRuntimeVersion);
                parameters.getVMParametersList().add("-Ddev.target.dirs=" + CompilerPaths.getModuleOutputDirectory(module, false).getPath());
                parameters.setJdk(sdk);
                parameters.setMainClass("org.kevoree.platform.standalone.App");
                return parameters;
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

}
