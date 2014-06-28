package org.kevoree.idea.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * Created by duke on 6/28/14.
 */
public class KevScriptLexerAdapter extends FlexAdapter {
    public KevScriptLexerAdapter() {
        super(new KevScriptLexer());
    }
}
