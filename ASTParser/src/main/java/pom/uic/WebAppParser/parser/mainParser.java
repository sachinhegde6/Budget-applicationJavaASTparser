package pom.uic.WebAppParser.parser;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import pom.uic.WebAppParser.parser.visitors.VSDVisitor;
import pom.uic.WebAppParser.parser.visitors.VSDVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map;

import static org.apache.commons.io.FileUtils.readFileToString;


public class mainParser extends ASTVisitor{

    public int runParser(ArrayList<File> files)
    {
        //setEnvironemnt();
        System.out.println("Hello parse problem!");
       // visitor v = new visitor();
      //  v.visit();
        return 1;
    }
    public void setEnvironemnt(List<File> files) {
        try{
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setResolveBindings( true );
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions( options );
        String unitName = "test.java";
        parser.setUnitName( unitName );
        String[] sources = {"C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST"};
        String[] classpath = {"C:\\Program Files\\Java\\jdk1.8.0_144\\src.zip"};
        parser.setEnvironment( classpath, sources, null, true );
        //parser.setSource("C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java");



        try {
            parser.setSource( readFileToString( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java" ) ).toCharArray() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File file : files) {
            System.out.println( "HEEEEEEEEEEERREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" );
            System.out.println( file );
            System.out.println( "HEEEEEEEEEEERREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" );

            try {
                parser.setSource( readFileToString( new File( file.getAbsolutePath() ) ).toCharArray() );
            } catch (IOException e) {
                e.printStackTrace();
            }

            final CompilationUnit cu = (CompilationUnit) parser.createAST( null );

            VSDVisitor v = new VSDVisitor();
            cu.accept( v );
            List<Statement> st = new ArrayList<Statement>();

            for (VariableDeclarationStatement vd : v.getVSDVisitor()) {
                st.add( vd );
            }

            ASTRewrite rewriter = ASTRewrite.create( cu.getAST() );
            Block block = cu.getAST().newBlock();

            for (Statement s_p : st) {
                int line = cu.getLineNumber( s_p.getStartPosition() );
                //System.out.println("statement: "+ s_p + " at line " + line);

                String s = "\n //statement: " + s_p + " at line " + line;
                s = s + "\n";
                TextElement siso = cu.getAST().newTextElement();
                siso.setText( s );
                ListRewrite lrw = rewriter.getListRewrite( s_p.getParent(), Block.STATEMENTS_PROPERTY );
                lrw.insertAfter( siso, s_p, null );

            }
            IDocument document2 = null;
            try {
                document2 = new Document( readFileToString( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java" ) ) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextEdit edits = rewriter.rewriteAST( document2, null );
            try {
                edits.apply( document2 );
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            System.out.print( document2.get() );
            try {
                FileUtils.write( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResource.java" ), document2.get() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     } catch (Exception e){
         e.printStackTrace();
     }
     }

    }




