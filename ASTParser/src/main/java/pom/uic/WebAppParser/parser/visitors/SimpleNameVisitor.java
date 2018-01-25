package pom.uic.WebAppParser.parser.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.ArrayList;
import java.util.List;

public class SimpleNameVisitor extends ASTVisitor {
    public List SimpleNameVisitorFunction(CompilationUnit cu) {
        SimpleNameV SNV = new SimpleNameV();
        cu.accept( SNV );
//        List<Statement> st = new ArrayList<Statement>();
        List<SimpleName> st = new ArrayList<SimpleName>();

        for (SimpleName SN_list : SNV.getSimpleName_list()) st.add( SN_list );


        return st;

    }

}
    class SimpleNameV extends ASTVisitor {
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


