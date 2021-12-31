package com.test.apply.eval;

import com.jmc.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.jmc.lang.Run.execToStr;

@SuppressWarnings("all")
public class Eval {
    public static String eval(String cmd) throws IOException {
        String template = """
        import java.util.*;
        import java.util.stream.*;
        
        class Main {
            public static void main(String[] args) {
                #code
            }
        }
        """;
        String src = template.replace("#code", cmd);
        File javaFile = File.createTempFile("Main", ".java");
        Files.out(src, javaFile, false);

        String result = execToStr("java " + javaFile.getAbsolutePath());
        javaFile.delete();

        return result;
    }


    @Test
    public void test() throws IOException {
        String result = eval("""
            IntStream.rangeClosed(1, 5)
                     .forEach(System.out::println);
            """);
        System.out.println(result);

    }
}
