package com.github.javaparser.ast;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.observer.AstObserver;
import com.github.javaparser.ast.observer.AstObserverAdapter;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import static com.github.javaparser.StaticJavaParser.parse;
import static com.github.javaparser.StaticJavaParser.parseExpression;
import static com.github.javaparser.utils.Utils.EOL;
import static org.junit.jupiter.api.Assertions.*;
class NodeTest {
  @Test void registerSubTree(){
    String code="class A { int f; void foo(int p) { return 'z'; }}";
    CompilationUnit cu=parse(code);
    List<String> changes=new ArrayList<>();
    AstObserver observer=new AstObserverAdapter(){
      @Override public void propertyChange(      Node observedNode,      ObservableProperty property,      Object oldValue,      Object newValue){
        changes.add(String.format("%s.%s changed from %s to %s",observedNode.getClass().getSimpleName(),property.name().toLowerCase(),oldValue,newValue));
      }
    }
;
    cu.registerForSubtree(observer);
    assertEquals(Arrays.asList(),changes);
    cu.getClassByName("A").get().setName("MyCoolClass");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
    cu.getClassByName("MyCoolClass").get().getFieldByName("f").get().getVariable(0).setType(new PrimitiveType(PrimitiveType.Primitive.BOOLEAN));
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass","FieldDeclaration.maximum_common_type changed from int to boolean","VariableDeclarator.type changed from int to boolean"),changes);
    cu.getClassByName("MyCoolClass").get().getMethodsByName("foo").get(0).getParameterByName("p").get().setName("myParam");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass","FieldDeclaration.maximum_common_type changed from int to boolean","VariableDeclarator.type changed from int to boolean","Parameter.name changed from p to myParam"),changes);
  }
  @Test void registerWithJustNodeMode(){
    String code="class A { int f; void foo(int p) { return 'z'; }}";
    CompilationUnit cu=parse(code);
    List<String> changes=new ArrayList<>();
    AstObserver observer=new AstObserverAdapter(){
      @Override public void propertyChange(      Node observedNode,      ObservableProperty property,      Object oldValue,      Object newValue){
        changes.add(String.format("%s.%s changed from %s to %s",observedNode.getClass().getSimpleName(),property.name().toLowerCase(),oldValue,newValue));
      }
    }
;
    cu.getClassByName("A").get().register(observer,Node.ObserverRegistrationMode.JUST_THIS_NODE);
    assertEquals(Arrays.asList(),changes);
    cu.getClassByName("A").get().setName("MyCoolClass");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
    cu.getClassByName("MyCoolClass").get().getFieldByName("f").get().getVariable(0).setType(new PrimitiveType(PrimitiveType.Primitive.BOOLEAN));
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
    cu.getClassByName("MyCoolClass").get().getMethodsByName("foo").get(0).getParameterByName("p").get().setName("myParam");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
    cu.getClassByName("MyCoolClass").get().addField("int","bar").getVariables().get(0).setInitializer("0");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
  }
  @Test void registerWithNodeAndExistingDescendantsMode(){
    String code="class A { int f; void foo(int p) { return 'z'; }}";
    CompilationUnit cu=parse(code);
    List<String> changes=new ArrayList<>();
    AstObserver observer=new AstObserverAdapter(){
      @Override public void propertyChange(      Node observedNode,      ObservableProperty property,      Object oldValue,      Object newValue){
        changes.add(String.format("%s.%s changed from %s to %s",observedNode.getClass().getSimpleName(),property.name().toLowerCase(),oldValue,newValue));
      }
    }
;
    cu.getClassByName("A").get().register(observer,Node.ObserverRegistrationMode.THIS_NODE_AND_EXISTING_DESCENDANTS);
    assertEquals(Arrays.asList(),changes);
    cu.getClassByName("A").get().setName("MyCoolClass");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass"),changes);
    cu.getClassByName("MyCoolClass").get().getFieldByName("f").get().getVariable(0).setType(new PrimitiveType(PrimitiveType.Primitive.BOOLEAN));
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass","FieldDeclaration.maximum_common_type changed from int to boolean","VariableDeclarator.type changed from int to boolean"),changes);
    cu.getClassByName("MyCoolClass").get().getMethodsByName("foo").get(0).getParameterByName("p").get().setName("myParam");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass","FieldDeclaration.maximum_common_type changed from int to boolean","VariableDeclarator.type changed from int to boolean","Parameter.name changed from p to myParam"),changes);
    cu.getClassByName("MyCoolClass").get().addField("int","bar").getVariables().get(0).setInitializer("0");
    assertEquals(Arrays.asList("ClassOrInterfaceDeclaration.name changed from A to MyCoolClass","FieldDeclaration.maximum_common_type changed from int to boolean","VariableDeclarator.type changed from int to boolean","Parameter.name changed from p to myParam"),changes);
  }
  @Test void registerWithSelfPropagatingMode(){
  }
  public NodeTest(){
  }
}
