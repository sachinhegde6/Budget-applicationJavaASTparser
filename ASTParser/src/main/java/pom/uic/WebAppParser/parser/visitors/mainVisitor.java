package pom.uic.WebAppParser.parser.visitors;

import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class mainVisitor {

    public List setEnvironment(CompilationUnit cu) {
        VSDVisitor2 vd = new VSDVisitor2();
        ifVisitor2 ifV = new ifVisitor2();
        forVisitor forV = new forVisitor();
        whileVisitor whileV = new whileVisitor();
        ConditionalExpressionVisitor CondExpV = new ConditionalExpressionVisitor();
    //    SimpleNameVisitor SNV = new SimpleNameVisitor();
        ExpressionStatementVisitor ESV = new ExpressionStatementVisitor();

        cu.accept( vd );
        cu.accept( ifV );
        cu.accept( forV );
        cu.accept( whileV );
        cu.accept( CondExpV );
      //  cu.accept( SNV );
        cu.accept( ESV );

        List<Statement> st = new ArrayList<Statement>();

        for (VariableDeclarationStatement vd_list : vd.getVDSStatement_list()) st.add( vd_list );
        for (IfStatement if_list : ifV.getIfStatement_list()) st.add( if_list );
        for (ForStatement for_list : forV.getForStatement_list()) st.add( for_list );
        for (WhileStatement while_list: whileV.getwhileStatement_list()) st.add( while_list );
        for (ExpressionStatement exp_list: ESV.getExpressionStatement_list()) st.add(exp_list);
        //for (ConditionalExpression CE_list : CondExpV.getConditionalExpression_list()) st.add( CE_list);
        //for (SimpleName SN_list : SNV.getSimpleName_list()) st.add( SN_list );
        return st;


    }
}

class ifVisitor2 extends ASTVisitor {
    //  public class ForStmt  {
    public List<IfStatement> ifStatement_list = new ArrayList<IfStatement>();
    @Override
    public boolean visit(IfStatement node){
        ifStatement_list.add(node);
        return super.visit(node);
    }
    public List<IfStatement> getIfStatement_list() {
        return ifStatement_list;
    }

}

class forVisitor extends ASTVisitor {
    //  public class ForStmt  {
    private List<ForStatement> forStatement_list = new ArrayList<ForStatement>();
    @Override
    public boolean visit(ForStatement node){
        forStatement_list.add(node);
        return super.visit(node);
    }
    public List<ForStatement> getForStatement_list() {
        return forStatement_list;
    }

}

class whileVisitor extends ASTVisitor {
    //  public class ForStmt  {
    private List<WhileStatement> whileStatement_list = new ArrayList<WhileStatement>();
    @Override
    public boolean visit(WhileStatement node){
        whileStatement_list.add(node);
        return super.visit(node);
    }
    public List<WhileStatement> getwhileStatement_list() {
        return whileStatement_list;
    }

}


class VSDVisitor2 extends ASTVisitor {
    private List<VariableDeclarationStatement> VDSStatement_list = new ArrayList<VariableDeclarationStatement>();
    @Override
    public boolean visit(VariableDeclarationStatement node) {
        VDSStatement_list.add(node);
        return super.visit(node);
    }
    public List<VariableDeclarationStatement> getVDSStatement_list() {
        return VDSStatement_list;
    }
}

//ConditionalExpression
class ConditionalExpressionVisitor extends ASTVisitor {
    private List<ConditionalExpression> ConditionalExpression_list = new ArrayList<ConditionalExpression>();
    @Override
    public boolean visit(ConditionalExpression node) {
        ConditionalExpression_list.add(node);
        return super.visit(node);
    }
    public List<ConditionalExpression> getConditionalExpression_list() {
        return ConditionalExpression_list;
    }
}
/*

//SimpleName
class SimpleNameVisitor extends ASTVisitor {
    private List<SimpleName> SimpleName_list = new ArrayList<SimpleName>();
    @Override
    public boolean visit(SimpleName node) {
        SimpleName_list.add(node);
        return super.visit(node);
    }
    public List<SimpleName> getSimpleName_list() {
        return SimpleName_list;
    }
}
*/

//ExpressionStatement
class ExpressionStatementVisitor extends ASTVisitor {
    private List<ExpressionStatement> ExpressionStatement_list = new ArrayList<ExpressionStatement>();

    @Override
    public boolean visit(ExpressionStatement node) {
        ExpressionStatement_list.add(node);
        return super.visit(node);
    }
    public List<ExpressionStatement> getExpressionStatement_list() {
        return ExpressionStatement_list;
    }
}