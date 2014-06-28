// This is a generated file. Not intended for manual editing.
package org.kevoree.idea.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.kevoree.idea.psi.KevScriptTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.kevoree.idea.psi.*;

public class KevScriptActionsImpl extends ASTWrapperPsiElement implements KevScriptActions {

  public KevScriptActionsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof KevScriptVisitor) ((KevScriptVisitor)visitor).visitActions(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getEof() {
    return findChildByType(EOF);
  }

  @Override
  @Nullable
  public PsiElement getNewline() {
    return findChildByType(NEWLINE);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(STRING);
  }

}
