package org.kevoree.idea.runner.dev;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import org.kevoree.idea.KevScriptLanguageType;


/**
 * Created by gregory.nain on 17/01/2014.
 */
public class KevScriptDevRunConfigurationProducer extends RunConfigurationProducer<KevScriptDevRunConfiguration> {

    protected KevScriptDevRunConfigurationProducer() {
        super(new KevScriptDevRunConfigurationType());
    }


    /*protected KevScriptDevRunConfigurationProducer() {
        super(KevScriptRunConfigurationType.getInstance());
    }
    */

    //Checks if the target of the click is a kevScript file
    @Override
    protected boolean setupConfigurationFromContext(KevScriptDevRunConfiguration kevScriptDevRunConfiguration, ConfigurationContext configurationContext, Ref<PsiElement> psiElementRef) {

        if(configurationContext.getLocation() != null && configurationContext.getLocation().getVirtualFile() != null && configurationContext.getLocation().getVirtualFile().getExtension() != null) {
            if(configurationContext.getLocation().getVirtualFile().getExtension().equals(KevScriptLanguageType.DEFAULT_EXTENSION)){
                kevScriptDevRunConfiguration.kevsFile = configurationContext.getLocation().getVirtualFile();
                kevScriptDevRunConfiguration.setName("Run (Dev) " + configurationContext.getModule().getName());
                kevScriptDevRunConfiguration.setModule(configurationContext.getModule());
                return true;
            }
        }
        return false;
    }

    //Checks if a RunConfiguration already exists for this project
    @Override
    public boolean isConfigurationFromContext(KevScriptDevRunConfiguration kevScriptDevRunConfiguration, ConfigurationContext configurationContext) {
        return kevScriptDevRunConfiguration.getName().equals("Run (Dev) " + configurationContext.getModule().getName());
    }
}
