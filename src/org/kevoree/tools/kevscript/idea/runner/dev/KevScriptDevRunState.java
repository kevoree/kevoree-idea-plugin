package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import org.kevoree.resolver.MavenResolver;
import org.kevoree.tools.kevscript.idea.runner.prod.KevScriptRunState;

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

        Module module = ((KevScriptDevRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).getConfigurationModule().getModule();
        JavaParameters parameters = new JavaParameters();
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk SDK = moduleRootManager.getSdk();

        Set<String> urls = new HashSet<String>();
        urls.add("http://repo1.maven.org/maven2/");
        urls.add("http://oss.sonatype.org/content/groups/public/");

        File kevoreeBase = resolver.resolve("org.kevoree.platform", "org.kevoree.platform.standalone", "latest", "jar", urls);
        if (kevoreeBase == null) {
            throw new ExecutionException("Unresolved Kevoree Runtime for version latest");
        }
        parameters.setJdk(SDK);
        parameters.setMainClass("org.kevoree.platform.standalone.App");
        parameters.getClassPath().add(kevoreeBase);
        parameters.getVMParametersList().add("-Dnode.name=node0");
        parameters.getVMParametersList().add("-Dnode.bootstrap=" + ((KevScriptDevRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).kevsFile.getPath());
        parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);

        return parameters;
    }

}
