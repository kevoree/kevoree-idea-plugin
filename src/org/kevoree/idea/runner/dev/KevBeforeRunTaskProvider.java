package org.kevoree.idea.runner.dev;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.RunManagerEx;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.Nullable;
import org.kevoree.idea.runner.prod.KevScriptRunConfiguration;

import java.util.List;

/**
 * Created by gregory.nain on 23/01/2014.
 */
public class KevBeforeRunTaskProvider extends BeforeRunTaskProvider<KevAnnotationProcessorBeforeRunTask> {

    public static final Key<KevAnnotationProcessorBeforeRunTask> ID = Key.create("KevAnnotationProcessorBeforeRunTask");
    private final Project myProject;

    public KevBeforeRunTaskProvider(Project project) {
        myProject = project;
    }


    @Override
    public Key<KevAnnotationProcessorBeforeRunTask> getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "KevAnnotationProcessorBeforeRunTask";
    }

    @Override
    public String getDescription(KevAnnotationProcessorBeforeRunTask kevAnnotationProcessorBeforeRunTask) {
        return "Run KevoreeAnnotationProcessor";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Nullable
    @Override
    public KevAnnotationProcessorBeforeRunTask createTask(RunConfiguration runConfiguration) {
        if(runConfiguration instanceof KevScriptRunConfiguration) {
            System.out.println("KevScriptBeforeTask createTask for RunConfig:" + runConfiguration.getClass());
            return new KevAnnotationProcessorBeforeRunTask(ID, runConfiguration, ((KevScriptRunConfiguration)runConfiguration).getConfigurationModule().getModule());
        }
        System.out.println("KevScriptBeforeTask null");
        return null;
    }

    @Override
    public boolean configureTask(RunConfiguration runConfiguration, KevAnnotationProcessorBeforeRunTask kevAnnotationProcessorBeforeRunTask) {
        System.out.println("KevScriptBeforeTask configureTask");
        if(!this.getName().equals("KevScriptDevRunnerTemplate")) {
            //RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(getProject());
            //RunManager.getInstance(getProject()).
            RunManagerEx runManager = RunManagerImpl.getInstanceEx(myProject);

            List<BeforeRunTask> list = runManager.getBeforeRunTasks(runConfiguration);
            KevBeforeRunTaskProvider prov = new KevBeforeRunTaskProvider(myProject);
            list.add(prov.createTask(runConfiguration));
            for(BeforeRunTask t : list) {
                System.out.println("Before Run Task:" + t.toString());
            }
            runManager.setBeforeRunTasks(runConfiguration, list, true);
        }
        return true;
    }

    @Override
    public boolean canExecuteTask(RunConfiguration runConfiguration, KevAnnotationProcessorBeforeRunTask kevAnnotationProcessorBeforeRunTask) {
        System.out.println("KevScriptBeforeTask canExecute");
        return true;
    }

    @Override
    public boolean executeTask(DataContext dataContext, RunConfiguration runConfiguration, ExecutionEnvironment executionEnvironment, KevAnnotationProcessorBeforeRunTask kevAnnotationProcessorBeforeRunTask) {
        System.out.println("KevScriptBeforeTask Execution");
        return true;
    }
}
