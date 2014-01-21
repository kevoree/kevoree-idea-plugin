package org.kevoree.tools.kevscript.idea.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
        Module module = ((KevScriptRunConfiguration)getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).getConfigurationModule().getModule();
       /*
        if(module == null) {
            module = (Module) getEnvironment().getDataContext().getData("module");
        }*/


        //
        File kevoreeBase = resolver.resolve("org.kevoree.platform","org.kevoree.platform.standalone","latest","jar",new HashSet<String>());
        if(kevoreeBase == null){
            throw new ExecutionException("Unresolved Kevoree Runtime for version latest");
        }

        parameters.setMainClass("org.kevoree.platform.standalone.App");
        parameters.getClassPath().add(kevoreeBase);
        parameters.getProgramParametersList().add("node.name", "node0");
        parameters.getProgramParametersList().add("node.bootstrap", ((KevScriptRunConfiguration)getEnvironment().getRunnerAndConfigurationSettings().getConfiguration()).kevsFile.getPath());



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
