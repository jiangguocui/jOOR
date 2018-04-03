/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joor.test;

/* [java-8] */

import static junit.framework.Assert.*;

import org.joor.Reflect;
import org.joor.test.CompileTest.J;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author Lukas Eder
 */
public class CompileTest {

    @Test
    public void testCompile1() throws Exception {
        I i = Reflect.compile("org.joor.test.CompileTest1", "package org.joor.test; public class CompileTest1 implements org.joor.test.I {}").create().get();
        assertEquals("I.m()", i.m());

        J j = Reflect.compile("org.joor.test.CompileTest2", "package org.joor.test; public class CompileTest2 implements org.joor.test.CompileTest.J {}").create().get();
        assertEquals("J.m()", j.m());
    }

    @Test
    public void testCompile2() {
        Supplier<String> supplier = Reflect.compile(
                "org.joor.test.CompileTest3",
                "package org.joor.test;\n" +
                        "class CompileTest3 implements java.util.function.Supplier<String> {\n" +
                        "  public String get() {\n" +
                        "    return \"Hello World!\";\n" +
                        "  }\n" +
                        "}\n").create().get();

        assertEquals("Hello World!", supplier.get());
    }

    interface J {
        default String m() {
            return "J.m()";
        }
    }
}

interface I extends J {
    @Override
    default String m() {
        return "I.m()";
    }
}

/* [/java-8] */