package pom.uic.WebAppParser;
import pom.uic.WebAppParser.parser.mainParser;
import pom.uic.WebAppParser.parser.mainParser2;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main
{

public static void main(String[] args) {
    System.out.println( "Hello main program!" ); // Display the string.

/*
    try {
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\sachi\\Downloads\\budgetapp-master\\src"))) {
            paths.filter(Files::isRegularFile);
            paths.forEach(System.out::println);

        }
    } catch (IOException e) {
        e.printStackTrace();
    }*/


    List<File> filesInFolder = null;
    List<File> javafilesInFolder = null;
    try {

        filesInFolder = Files.walk( Paths.get( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\main\\java\\io\\budgetapp" ) )
                //       filesInFolder = Files.walk( Paths.get( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\AST" ) )

                .filter( Files::isRegularFile )
                // .filter(.getFileName().endsWith( ".java" ) )
                .map( Path::toFile )
                .collect( Collectors.toList() );
        List<File> filesInFolder1 = null;
        filesInFolder1 = Files.walk( Paths.get( "C:\\Users\\sachi\\Downloads\\budgetapp-master\\src\\test\\java" ) )
                .filter( Files::isRegularFile )
                // .filter(.getFileName().endsWith( ".java" ) )
                .map( Path::toFile )
                .collect( Collectors.toList() );
        for (File file : filesInFolder1) {
            filesInFolder.add( file );
        }

            for (File file : filesInFolder) {
                System.out.println( file.getName() );
                if (file.getName().endsWith( ".java" )) {
                    System.out.println( "Is a java file" );
                    javafilesInFolder.add( file );
                }

            }

    }catch (Exception e) {
        e.printStackTrace();
    }

/*

    mainParser p = new mainParser();
    p.setEnvironemnt(filesInFolder);
*/
    mainParser2 p1 = new mainParser2();
    p1.setEnvironemnt( filesInFolder );
}
}
