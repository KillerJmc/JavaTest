open module JavaTest {
    requires java.sql;
    requires java.scripting;
    requires java.compiler;
    requires java.net.http;

    requires static lombok;
    requires junit;
    requires jmh.core;
    requires jmh.generator.annprocess;
    requires jmc.utils;
}