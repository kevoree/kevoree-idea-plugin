package org.kevoree.tools.kevscript.idea;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by gregory.nain on 22/01/2014.
 */
public class KevsFileChooserDescriptor extends FileChooserDescriptor {

    public KevsFileChooserDescriptor() {
        super(true, false, false, false, false, false);
    }

    @Override
    public boolean isFileSelectable(VirtualFile file) {
        return file != null && file.getExtension() != null && file.getExtension().equals("kevs");
    }

}
