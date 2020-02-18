package com.github.javaparser.ast.body;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.SimpleName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
class AnnotationMemberDeclarationTest {
  @Test void whenSettingNameTheParentOfNameIsAssigned(){
    AnnotationMemberDeclaration decl=new AnnotationMemberDeclaration();
    SimpleName name=new SimpleName("foo");
    decl.setName(name);
    assertTrue(name.getParentNode().isPresent());
    assertTrue(decl == name.getParentNode().get());
  }
  @Test void removeDefaultValueWhenNoDefaultValueIsPresent(){
    AnnotationMemberDeclaration decl=new AnnotationMemberDeclaration();
    SimpleName name=new SimpleName("foo");
    decl.setName(name);
    decl.removeDefaultValue();
    assertFalse(decl.getDefaultValue().isPresent());
  }
  @Test void removeDefaultValueWhenDefaultValueIsPresent(){
    AnnotationMemberDeclaration decl=new AnnotationMemberDeclaration();
    SimpleName name=new SimpleName("foo");
    decl.setName(name);
    Expression defaultValue=new IntegerLiteralExpr("2");
    decl.setDefaultValue(defaultValue);
    decl.removeDefaultValue();
    assertFalse(defaultValue.getParentNode().isPresent());
  }
  public AnnotationMemberDeclarationTest(){
  }
}
