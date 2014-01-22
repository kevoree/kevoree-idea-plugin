package org.kevoree.tools.kevscript.idea.runner.prod;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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

        JavaParameters parameters = new JavaParameters();

        //Tries to collect the module, to get the output folder
        Module module = ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).getConfigurationModule().getModule();
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
        parameters.getVMParametersList().add("-Dnode.bootstrap=" + ((KevScriptRunConfiguration) getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).kevsFile.getPath());


        //parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);

        //Checks if the output folder exists; launches a Make otherwise
        /*
        if(CompilerPaths.getModuleOutputDirectory(module, false) != null && CompilerPaths.getModuleOutputDirectory(module, false).exists()) {
            VirtualFile[] moduleOutputDir = new VirtualFile[1];
            moduleOutputDir[0] = CompilerPaths.getModuleOutputDirectory(module, false);
            handler = runBootstrap(moduleOutputDir);
        } else {
            CompilerManager.getInstance(getEnvironment().getProject()).make(module, new CompileStatusNotification() {
                @Override
                public void finished(boolean b, int i, int i2, final CompileContext compileContext) {
                    System.out.println("Make finished");
                    handler = runBootstrap(compileContext.getAllOutputDirectories());
                }
            });
        }
        */


        return parameters;
    }



    /*
    *
    *  for (VirtualFile f : outputFolders) {
                    handler.notifyTextAvailable("Adding components from location:" + f.getPath() + '\n', ProcessOutputTypes.STDOUT);
                    System.out.println(f.getPath());
                }

                for (int j = 0; j < 5; j++) {
                    try {
                        handler.notifyTextAvailable("Working hard " + j + '\n', ProcessOutputTypes.STDOUT);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.destroyProcess();
    *
    *
    *
    *
    *   //Tries to collect the module, to get the output folder
        Module module = ((KevScriptRunConfiguration)getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).getConfigurationModule().getModule();
        if(module == null) {
            module = (Module) getEnvironment().getDataContext().getData("module");
        }
        parameters.configureByModule(module, JavaParameters.CLASSES_ONLY);
        parameters.



        //Checks if the output folder exists; launches a Make otherwise
        if(CompilerPaths.getModuleOutputDirectory(module, false) != null && CompilerPaths.getModuleOutputDirectory(module, false).exists()) {
            VirtualFile[] moduleOutputDir = new VirtualFile[1];
            moduleOutputDir[0] = CompilerPaths.getModuleOutputDirectory(module, false);
            handler = runBootstrap(moduleOutputDir);
        } else {

            CompilerManager.getInstance(getEnvironment().getProject()).make(module, new CompileStatusNotification() {
                @Override
                public void finished(boolean b, int i, int i2, final CompileContext compileContext) {
                    System.out.println("Make finished");
                    handler = runBootstrap(compileContext.getAllOutputDirectories());
                }
            });
        }
    *
    *
    *
    * */


}
