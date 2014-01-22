package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.CompilerPaths;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.util.HashSet;

/**
 * Created by gregory.nain on 20/01/2014.
 */
public class KevScriptRunState extends JavaCommandLineState {


    protected KevScriptRunState(ExecutionEnvironment environment) {
        super(environment);
    }

    private static final MavenResolver resolver = new MavenResolver();

    @Override
    protected JavaParameters createJavaParameters() throws ExecutionException {

        Module module = ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).getConfigurationModule().getModule();
        JavaParameters parameters = new JavaParameters();
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk SDK = moduleRootManager.getSdk();
        File kevoreeBase = resolver.resolve("org.kevoree.platform", "org.kevoree.platform.standalone", "latest", "jar", new HashSet<String>());
        if (kevoreeBase == null) {
            throw new ExecutionException("Unresolved Kevoree Runtime for version latest");
        }
        parameters.setJdk(SDK);
        parameters.setMainClass("org.kevoree.platform.standalone.App");
        parameters.getClassPath().add(kevoreeBase);
        parameters.getVMParametersList().add("-Dnode.name=node0");
        parameters.getVMParametersList().add("-Dnode.bootstrap=" + ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).kevsFile.getPath());
        parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);
        return parameters;
    }

}
