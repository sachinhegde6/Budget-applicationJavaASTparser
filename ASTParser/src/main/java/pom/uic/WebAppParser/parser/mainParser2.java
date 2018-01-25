package pom.uic.WebAppParser.parser;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import pom.uic.WebAppParser.parser.visitors.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.readFileToString;


public class mainParser2 extends ASTVisitor{


    public void setEnvironemnt(List<File> files) {
        try{
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setResolveBindings( true );
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions( options );
        String unitName = "test.java";
        parser.setUnitName( unitName );
        String[] sources = {"C:\\Users\\sachi\\Downloads\\budgetapp-master\\src"};
        String[] classpath = {"C:\\Program Files\\Java\\jdk1.8.0_144\\src.zip"};
        parser.setEnvironment( classpath, sources, null, true );
        //parser.setSource("C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java");



/*
        try {
            parser.setSource( readFileToString( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java" ) ).toCharArray() );
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        for (File file : files) {
            if(file.getName().endsWith("ASTClass.java")) continue;
            System.out.println( "======================================File Name====================================" );
            System.out.println( file );
            System.out.println( "======================================File Name====================================" );

            try {
                parser.setSource( readFileToString( new File( file.getAbsolutePath() ) ).toCharArray() );
            } catch (IOException e) {
                e.printStackTrace();
            }

            final CompilationUnit cu = (CompilationUnit) parser.createAST( null );


            mainVisitor mv= new mainVisitor();
            List<Statement> st=mv.setEnvironment(cu);

            SimpleNameVisitor SN= new SimpleNameVisitor();
            List<SimpleName> sn = SN.SimpleNameVisitorFunction( cu );


/*
            VSDVisitor v = new VSDVisitor();
            cu.accept( v );
            List<Statement> st = new ArrayList<Statement>();

            for (VariableDeclarationStatement vd : v.getVSDVisitor()) {
                st.add( vd );
            }
*/

            ASTRewrite rewriter = ASTRewrite.create( cu.getAST() );
            Block block = cu.getAST().newBlock();
            String s = null;
            for (Statement s_p : st) {
                int line = cu.getLineNumber( s_p.getStartPosition() );
                String statementclass = s_p.getClass().getName().toString();
                String statementclassupdated = statementclass;
                if(statementclass=="org.eclipse.jdt.core.dom.VariableDeclarationStatement"){statementclassupdated="Variable Declaration Statement";}
                if(statementclass=="org.eclipse.jdt.core.dom.ExpressionStatement"){statementclassupdated="Expression Statement";}
                if(statementclass=="org.eclipse.jdt.core.dom.IfStatement"){statementclassupdated="If Statement";}
                if(statementclass=="org.eclipse.jdt.core.dom.WhileStatement"){statementclassupdated="While Statement";}
                if(statementclass=="org.eclipse.jdt.core.dom.ForStatement"){statementclassupdated="For Statement";}

                for(SimpleName sn_i : sn ) {
                    if(cu.getLineNumber( s_p.getStartPosition()) == cu.getLineNumber( sn_i.getStartPosition()))
                    {

                        String name=sn_i.getFullyQualifiedName().toString();
                        String parent=sn_i.getParent().toString();

                        s = "\n ASTClass.instrum(\"" + statementclassupdated +"\",\""+ name +"\",\"" +parent+ "\",\"" + line +"\");";
                    //    System.out.println("TRUE TRUE statement: "+ sn_i + " at line " +cu.getLineNumber( sn_i.getStartPosition()) );
                    }
                    else{

                        //System.out.println("statement: "+ s_p + " at line " + line);

                        s = "\n ASTClass.instrum(\"" + statementclassupdated + "\",\"" + line +"\");";
//                String s = "\n //templateClass.instrum(" + s_p.getStructuralProperty() + " is at line " + line;

//                        s = s + "\n";
//                        System.out.println("ELSE ELSE statement: "+ sn_i + " at line " +cu.getLineNumber( sn_i.getStartPosition()) );
                    }
                }

/*                int line = cu.getLineNumber( s_p.getStartPosition() );
                //System.out.println("statement: "+ s_p + " at line " + line);

                String s = "\n //templateClass.instrum(\"" + s_p.getClass().getName().toString() + "\"," + line +");";
//                String s = "\n //templateClass.instrum(" + s_p.getStructuralProperty() + " is at line " + line;

                s = s + "\n";*/
                TextElement siso = cu.getAST().newTextElement();
                siso.setText( s );
                ListRewrite lrw = rewriter.getListRewrite( s_p.getParent(), Block.STATEMENTS_PROPERTY );
                lrw.insertAfter( siso, s_p, null );

            }


            IDocument document2 = null;
            try {
                //parser.setSource( readFileToString( new File( file.getAbsolutePath() ) ).toCharArray() );


//                document2 = new Document( readFileToString( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResourceIT.java" ) ) );
                document2 = new Document( readFileToString( new File(  file.getAbsolutePath()) ) );

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
//                FileUtils.write( new File( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST\\BudgetResource.java" ), document2.get() );
                FileUtils.write( new File( String.valueOf( file ) ), document2.get() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     } catch (Exception e){
         e.printStackTrace();
     }
     }

    }




