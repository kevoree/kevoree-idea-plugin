package org.kevoree.tools.kevscript.idea.runner.prod;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kevoree.resolver.MavenResolver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptRunConfiguration extends ModuleBasedConfiguration<KevRunConfigurationModule> {

    public VirtualFile kevsFile;
    public String moduleName;
    public String kevoreeRuntimeVersion;

    protected static final MavenResolver resolver = new MavenResolver();
    public SortedSet<String> availableRuntimeVersions;

    public KevScriptRunConfiguration(String name, KevRunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super(name, configurationModule, factory);
        loadKevoreeRuntimeVersions();
    }

    public KevScriptRunConfiguration(KevRunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super(configurationModule, factory);
        loadKevoreeRuntimeVersions();
    }

    protected KevScriptRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(name, new KevRunConfigurationModule(project), factory);
        loadKevoreeRuntimeVersions();
    }

    private void loadKevoreeRuntimeVersions() {
        Set<String> urls = new HashSet<String>();
        urls.add("http://repo1.maven.org/maven2/");
        urls.add("http://oss.sonatype.org/content/groups/public/");

        availableRuntimeVersions = resolver.listVersion("org.kevoree.platform", "org.kevoree.platform.standalone", "jar", urls);
        kevoreeRuntimeVersion = availableRuntimeVersions.first();
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new KevScriptRunConfigurationSettingsEditor(getProject());
    }


    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if(getConfigurationModule().getModule() == null) {
            throw new RuntimeConfigurationException("You must select a module for this configuration.");
        }
    }


    @Nullable
    @Override
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment executionEnvironment) throws ExecutionException {

        return new KevScriptRunState(executionEnvironment);
    }

    @Override
    public Collection<Module> getValidModules() {
        return this.getAllModules();
    }


    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);

        if(getName().equals("KevScriptRunnerTemplate") || getName().equals("KevScriptDevRunnerTemplate")) {
            return;
        }

        if(element.getChild("KevsFile") != null) {
            VirtualFile f = VirtualFileManager.getInstance().findFileByUrl(element.getChild("KevsFile").getAttributeValue("location"));
            if(f != null) {
                kevsFile = f;
            } else {
                Notifications.Bus.notify(new Notification("KevPlugin", "KevScriptRunConfiguration", "Could not find KevScript file at location:" + element.getChild("KevsFile").getAttributeValue("location"), NotificationType.WARNING));
            }
        }

        if(element.getChild("Module") != null) {
            moduleName = element.getChild("Module").getAttributeValue("name");
            Module m = getConfigurationModule().findModule(element.getChild("Module").getAttributeValue("name"));
            if(m != null) {
                getConfigurationModule().setModule(m);
            } else {
                Notifications.Bus.notify(new Notification("KevPlugin", "KevScriptRunConfiguration", "Could not find module for name:" + element.getChild("Module").getAttributeValue("name"), NotificationType.WARNING));
            }
        }

        if(element.getChild("KevoreeRuntime") != null) {
            kevoreeRuntimeVersion = element.getChild("KevoreeRuntime").getAttributeValue("version");
        }

        getConfigurationModule().readExternal(element);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);

        if(getName().equals("KevScriptRunnerTemplate") || getName().equals("KevScriptDevRunnerTemplate")) {
            return;
        }


        if(kevsFile != null) {
            Element kevsFileElem = new Element("KevsFile");
            kevsFileElem.setAttribute("location", kevsFile.getUrl());
            element.addContent(kevsFileElem);
        }

        if(getConfigurationModule().getModule() != null) {
            Element moduleElem = new Element("Module");
            moduleElem.setAttribute("name", getConfigurationModule().getModule().getName());
            element.addContent(moduleElem);
        }

        if(kevoreeRuntimeVersion != null) {
            Element versionElem = new Element("KevoreeRuntime");
            versionElem.setAttribute("version", kevoreeRuntimeVersion);
            element.addContent(versionElem);
        }

        getConfigurationModule().writeExternal(element);
    }


}
