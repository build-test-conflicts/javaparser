package com.github.javaparser.symbolsolver.javaparsermodel.contexts;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.resolution.declarations.*;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.resolution.types.ResolvedTypeVariable;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFactory;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserTypeParameter;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.model.resolution.Value;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
/** 
 * @author Federico Tomassetti
 */
public class ClassOrInterfaceDeclarationContext extends AbstractJavaParserContext<ClassOrInterfaceDeclaration> {
  private JavaParserTypeDeclarationAdapter javaParserTypeDeclarationAdapter;
  public ClassOrInterfaceDeclarationContext(  ClassOrInterfaceDeclaration wrappedNode,  TypeSolver typeSolver){
    super(wrappedNode,typeSolver);
    this.javaParserTypeDeclarationAdapter=new JavaParserTypeDeclarationAdapter(wrappedNode,typeSolver,getDeclaration(),this);
  }
  @Override public SymbolReference<? extends ResolvedValueDeclaration> solveSymbol(  String name){
    if (typeSolver == null)     throw new IllegalArgumentException();
    if (this.getDeclaration().hasVisibleField(name)) {
      return SymbolReference.solved(this.getDeclaration().getVisibleField(name));
    }
    return getParent().solveSymbol(name);
  }
  @Override public Optional<Value> solveSymbolAsValue(  String name){
    if (typeSolver == null)     throw new IllegalArgumentException();
    if (this.getDeclaration().hasVisibleField(name)) {
      return Optional.of(Value.from(this.getDeclaration().getVisibleField(name)));
    }
    return getParent().solveSymbolAsValue(name);
  }
  @Override public Optional<ResolvedType> solveGenericType(  String name){
    for (    com.github.javaparser.ast.type.TypeParameter tp : wrappedNode.getTypeParameters()) {
      if (tp.getName().getId().equals(name)) {
        return Optional.of(new ResolvedTypeVariable(new JavaParserTypeParameter(tp,typeSolver)));
      }
    }
    return getParent().solveGenericType(name);
  }
  @Override public SymbolReference<ResolvedTypeDeclaration> solveType(  String name){
    for (    ClassOrInterfaceType implementedType : wrappedNode.getImplementedTypes()) {
      if (implementedType.getName().getId().equals(name)) {
        return JavaParserFactory.getContext(wrappedNode.getParentNode().orElse(null),typeSolver).solveType(name);
      }
    }
    for (    ClassOrInterfaceType extendedType : wrappedNode.getExtendedTypes()) {
      if (extendedType.getName().getId().equals(name)) {
        return JavaParserFactory.getContext(wrappedNode.getParentNode().orElse(null),typeSolver).solveType(name);
      }
    }
    return javaParserTypeDeclarationAdapter.solveType(name);
  }
  @Override public SymbolReference<ResolvedMethodDeclaration> solveMethod(  String name,  List<ResolvedType> argumentsTypes,  boolean staticOnly){
    return javaParserTypeDeclarationAdapter.solveMethod(name,argumentsTypes,staticOnly);
  }
  public SymbolReference<ResolvedConstructorDeclaration> solveConstructor(  List<ResolvedType> argumentsTypes){
    return javaParserTypeDeclarationAdapter.solveConstructor(argumentsTypes);
  }
  @Override public List<ResolvedFieldDeclaration> fieldsExposedToChild(  Node child){
    List<ResolvedFieldDeclaration> fields=new LinkedList<>();
    fields.addAll(this.wrappedNode.resolve().getDeclaredFields());
    this.wrappedNode.getExtendedTypes().forEach(i -> fields.addAll(i.resolve().getAllFieldsVisibleToInheritors()));
    this.wrappedNode.getImplementedTypes().forEach(i -> fields.addAll(i.resolve().getAllFieldsVisibleToInheritors()));
    return fields;
  }
  public ResolvedReferenceTypeDeclaration getDeclaration(){
    return JavaParserFacade.get(typeSolver).getTypeDeclaration(this.wrappedNode);
  }
}
