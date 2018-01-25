package pom.uic.WebAppParser.parser.visitors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.ArrayList;
import java.util.List;

public class VSDVisitor extends ASTVisitor {
    List<VariableDeclarationStatement> visitor = new ArrayList<VariableDeclarationStatement>();

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        visitor.add(node);
        return super.visit(node);

    }

    public List<VariableDeclarationStatement> getVSDVisitor() {
        return visitor;
    }
}





