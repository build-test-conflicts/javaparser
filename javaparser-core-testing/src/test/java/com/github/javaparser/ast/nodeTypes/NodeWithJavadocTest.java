package com.github.javaparser.ast.nodeTypes;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class NodeWithJavadocTest {
  @Test void removeJavaDocNegativeCaseNoComment(){
    ClassOrInterfaceDeclaration decl=new ClassOrInterfaceDeclaration(new NodeList<>(),false,"Foo");
    assertEquals(false,decl.removeJavaDocComment());
  }
  @Test void removeJavaDocNegativeCaseCommentNotJavaDoc(){
    ClassOrInterfaceDeclaration decl=new ClassOrInterfaceDeclaration(new NodeList<>(),false,"Foo");
    decl.setComment(new LineComment("A comment"));
    assertEquals(false,decl.removeJavaDocComment());
    assertTrue(decl.getComment().isPresent());
  }
  @Test void removeJavaDocPositiveCase(){
    ClassOrInterfaceDeclaration decl=new ClassOrInterfaceDeclaration(new NodeList<>(),false,"Foo");
    decl.setComment(new JavadocComment("A comment"));
    assertEquals(true,decl.removeJavaDocComment());
    assertFalse(decl.getComment().isPresent());
  }
  @Test void getJavadocOnMethodWithLineCommentShouldReturnEmptyOptional(){
    MethodDeclaration method=new MethodDeclaration();
    method.setLineComment("Lorem Ipsum.");
    assertFalse(method.getJavadocComment().isPresent());
    assertFalse(method.getJavadoc().isPresent());
  }
  public NodeWithJavadocTest(){
  }
}
