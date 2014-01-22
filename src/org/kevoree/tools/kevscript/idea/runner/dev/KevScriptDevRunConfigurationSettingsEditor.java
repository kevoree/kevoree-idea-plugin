package org.kevoree.tools.kevscript.idea.runner.dev;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.kevoree.tools.kevscript.idea.runner.prod.KevScriptRunConfigurationSettingsEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptDevRunConfigurationSettingsEditor extends KevScriptRunConfigurationSettingsEditor {

    public KevScriptDevRunConfigurationSettingsEditor(Project p) {
        super(p);
    }
}
