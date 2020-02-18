package com.github.javaparser.remove;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class NodeRemovalTest {
  private CompilationUnit cu=new CompilationUnit();
  @Test void testRemoveClassFromCompilationUnit(){
    ClassOrInterfaceDeclaration testClass=cu.addClass("test");
    assertEquals(1,cu.getTypes().size());
    boolean remove=testClass.remove();
    assertEquals(true,remove);
    assertEquals(0,cu.getTypes().size());
  }
  @Test void testRemoveFieldFromClass(){
    ClassOrInterfaceDeclaration testClass=cu.addClass("test");
    FieldDeclaration addField=testClass.addField(String.class,"test");
    assertEquals(1,testClass.getMembers().size());
    boolean remove=addField.remove();
    assertEquals(true,remove);
    assertEquals(0,testClass.getMembers().size());
  }
  @Test void testRemoveStatementFromMethodBody(){
    ClassOrInterfaceDeclaration testClass=cu.addClass("testC");
    MethodDeclaration addMethod=testClass.addMethod("testM");
    BlockStmt methodBody=addMethod.createBody();
    Statement addStatement=methodBody.addAndGetStatement("test");
    assertEquals(1,methodBody.getStatements().size());
    boolean remove=addStatement.remove();
    assertEquals(true,remove);
    assertEquals(0,methodBody.getStatements().size());
  }
  public NodeRemovalTest(){
  }
}
