package com.github.javaparser.ast.observer;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.github.javaparser.StaticJavaParser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
class PropagatingAstObserverTest {
  @Test void verifyPropagation(){
    String code="class A {  }";
    CompilationUnit cu=parse(code);
    List<String> changes=new ArrayList<>();
    AstObserver observer=new PropagatingAstObserver(){
      @Override public void concretePropertyChange(      Node observedNode,      ObservableProperty property,      Object oldValue,      Object newValue){
        changes.add(String.format("%s.%s changed from %s to %s",observedNode.getClass().getSimpleName(),property.name().toLowerCase(),oldValue,newValue));
      }
    }
;
    cu.registerForSubtree(observer);
    assertEquals(Arrays.asList(),changes);
    FieldDeclaration fieldDeclaration=cu.getClassByName("A").get().addField("String","foo");
    assertEquals(Arrays.asList(),changes);
    assertEquals(true,fieldDeclaration.isRegistered(observer));
    cu.getClassByName("A").get().getFieldByName("foo").get().getVariables().get(0).setName("Bar");
    assertEquals(Arrays.asList("VariableDeclarator.name changed from foo to Bar"),changes);
  }
  public PropagatingAstObserverTest(){
  }
}
