package org.kevoree.tools.kevscript.idea.runner.prod;

import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptRunConfigurationSettingsEditor extends SettingsEditor<KevScriptRunConfiguration> {

    private JLabel kevScriptFileLocation_lbl = new JLabel("KevScript file:");
    private JTextField kevScriptFileLocation_txt = new JTextField();
    private JComboBox moduleList = new JComboBox();
    private ConfigurationModuleSelector moduleSelector;

    private Project projet;

    public KevScriptRunConfigurationSettingsEditor(Project p) {
        projet = p;
        moduleSelector = new ConfigurationModuleSelector(projet, moduleList);
    }

    @Override
    protected void resetEditorFrom(KevScriptRunConfiguration kevScriptRunConfiguration) {

        moduleSelector.reset(kevScriptRunConfiguration);

        if(kevScriptRunConfiguration.getConfigurationModule() != null) {
            if(kevScriptRunConfiguration.getConfigurationModule().getModule() != null) {
                if(kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile()!= null) {
                    if(kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs") != null) {
                        kevScriptRunConfiguration.kevsFile = kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs");
                    }
                }
            }
        }

        if(kevScriptRunConfiguration.kevsFile != null) {
            kevScriptFileLocation_txt.setText(kevScriptRunConfiguration.kevsFile.getCanonicalPath());
        }
    }

    @Override
    protected void applyEditorTo(KevScriptRunConfiguration kevScriptRunConfiguration) throws ConfigurationException {

        moduleSelector.applyTo(kevScriptRunConfiguration);

        if(kevScriptRunConfiguration.getConfigurationModule() != null) {
            if(kevScriptRunConfiguration.getConfigurationModule().getModule() != null) {
                if(kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile()!= null) {
                    if(kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs") != null) {
                        kevScriptRunConfiguration.kevsFile = kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs");
                    }
                }
            }
        }
  }

    @NotNull
    @Override
    protected JComponent createEditor() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(kevScriptFileLocation_lbl);
        mainPanel.add(kevScriptFileLocation_txt);
        mainPanel.add(moduleList);
        return mainPanel;
    }
}
