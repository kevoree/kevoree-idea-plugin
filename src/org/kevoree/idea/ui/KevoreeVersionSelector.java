/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kevoree.idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.ComboboxSpeedSearch;
import org.kevoree.idea.runner.prod.KevScriptRunConfiguration;

import javax.swing.*;
import java.util.*;

public class KevoreeVersionSelector {

    private final Project myProject;
    private final JComboBox myVersionsCombo;
    private final DefaultComboBoxModel myVersions = new DefaultComboBoxModel();


    public KevoreeVersionSelector(final Project project, final JComboBox versionListCombo) {
        myProject = project;
        myVersionsCombo = versionListCombo;
        new ComboboxSpeedSearch(versionListCombo){
            protected String getElementText(Object element) {
                if (element instanceof String){
                    return (String)element;
                }
                return super.getElementText(element);
            }
        };
        versionListCombo.setModel(myVersions);
    }

    public void applyTo(final KevScriptRunConfiguration kevRunConfiguration) {
        kevRunConfiguration.kevoreeRuntimeVersion = (String)myVersionsCombo.getSelectedItem();
    }

    public void reset(final KevScriptRunConfiguration kevRunConfiguration) {
        setPossibleVersions(kevRunConfiguration.availableRuntimeVersions);
        if(kevRunConfiguration.kevoreeRuntimeVersion != null) {
            myVersions.setSelectedItem(kevRunConfiguration.kevoreeRuntimeVersion);
        }
    }


    public Project getProject() {
        return myProject;
    }


    private void setPossibleVersions(final Collection<String> versions) {
        myVersions.removeAllElements();
        myVersions.addElement("Latest");
        myVersions.addElement("Release");
        String versionToSelect = null;
        for (String version : versions) {
            if(!version.contains("SNAPSHOT")) {
                versionToSelect = version;
            }
            myVersions.addElement(version);
        }
        myVersions.setSelectedItem(versionToSelect);

    }

    public String getVersion() {
        return (String)myVersions.getSelectedItem();
    }

}
