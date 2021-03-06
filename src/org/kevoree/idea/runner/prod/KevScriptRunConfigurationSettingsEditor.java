package org.kevoree.idea.runner.prod;

import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;
import org.kevoree.idea.KevsFileChooserDescriptor;
import org.kevoree.idea.ui.KevoreeVersionSelector;
import org.kevoree.idea.utils.SpringUtilities;

import javax.swing.*;

/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptRunConfigurationSettingsEditor extends SettingsEditor<KevScriptRunConfiguration> {

    private JLabel kevScriptFileLocation_lbl = new JLabel("KevScript file:");
    private JLabel module_lbl = new JLabel("Module:");
    private JLabel kevoreeVersions_lbl = new JLabel("Runtime Version:");
    private JLabel nodeName_lbl = new JLabel("Node Name:");
    //private JTextField kevScriptFileLocation_txt = new JTextField();
    private TextFieldWithBrowseButton kevScriptFileLocation_txt =  new TextFieldWithBrowseButton();
    private FileChooserDescriptor kevsFileChooserDescriptor = new KevsFileChooserDescriptor();
    private ComboBox module_lst = new ComboBox();
    private ConfigurationModuleSelector moduleSelector;
    private JTextField nodeName_txt;

    private ComboBox kevoreeVersions_lst = new ComboBox();
    private KevoreeVersionSelector kevoreeVersionsSelector;

    private Project projet;

    public KevScriptRunConfigurationSettingsEditor(Project p) {
        projet = p;
        moduleSelector = new ConfigurationModuleSelector(projet, module_lst);
        TextBrowseFolderListener browseListener = new TextBrowseFolderListener(kevsFileChooserDescriptor, projet);
        kevScriptFileLocation_txt.addBrowseFolderListener(browseListener);
        module_lst.setEditable(true);

        kevoreeVersionsSelector = new KevoreeVersionSelector(projet, kevoreeVersions_lst);
        kevoreeVersions_lst.setEditable(true);

        nodeName_txt = new JTextField();

    }


    @Override
    protected void resetEditorFrom(KevScriptRunConfiguration kevScriptRunConfiguration) {

        moduleSelector.reset(kevScriptRunConfiguration);
        kevoreeVersionsSelector.reset(kevScriptRunConfiguration);

        nodeName_txt.setText(kevScriptRunConfiguration.nodeName);

        if(kevScriptRunConfiguration.getConfigurationModule().getModule() != null) {
            kevsFileChooserDescriptor.setRoots(kevScriptRunConfiguration.getConfigurationModule().getModule().getModuleFile());
        }

        if(kevScriptRunConfiguration.kevsFile != null) {
            kevScriptFileLocation_txt.setText(kevScriptRunConfiguration.kevsFile.getPath());
        } else {
            if(moduleSelector.getModule()  != null) {
                if(moduleSelector.getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs").exists()) {
                    kevScriptRunConfiguration.kevsFile = moduleSelector.getModule().getModuleFile().findFileByRelativePath("src/main/kevs/main.kevs");
                    kevScriptFileLocation_txt.setText(kevScriptRunConfiguration.kevsFile.getPath());
                }
            }
        }
    }

    @Override
    protected void applyEditorTo(KevScriptRunConfiguration kevScriptRunConfiguration) throws ConfigurationException {

        moduleSelector.applyTo(kevScriptRunConfiguration);
        kevoreeVersionsSelector.applyTo(kevScriptRunConfiguration);

        VirtualFile kevsVirtualFile = VirtualFileManager.getInstance().findFileByUrl((kevScriptFileLocation_txt.getText().startsWith("file://")?"":"file://") + kevScriptFileLocation_txt.getText());

        kevScriptRunConfiguration.kevsFile = kevsVirtualFile;
        kevScriptRunConfiguration.nodeName = nodeName_txt.getText();

    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        JPanel mainPanel = new JPanel(new SpringLayout());

        //Module selector
        mainPanel.add(module_lbl);
        module_lbl.setLabelFor(module_lst);
        mainPanel.add(module_lst);

        //KevScriptFile selector
        mainPanel.add(kevScriptFileLocation_lbl);
        kevScriptFileLocation_lbl.setLabelFor(kevScriptFileLocation_txt);
        mainPanel.add(kevScriptFileLocation_txt);

        //Node Name text field
        mainPanel.add(nodeName_lbl);
        nodeName_lbl.setLabelFor(nodeName_txt);
        mainPanel.add(nodeName_txt);

        //Kevoree Runtime Version selector
        mainPanel.add(kevoreeVersions_lbl);
        kevoreeVersions_lbl.setLabelFor(kevoreeVersions_lst);
        mainPanel.add(kevoreeVersions_lst);

        SpringUtilities.makeCompactGrid(mainPanel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        return mainPanel;

    }
}
