package com.test.incubator.clinker;

/**
 * 测试CLinker <br>
 * <pre>
 * 模块化应用：
 *     module-info.java: requires jdk.incubator.foreign;
 *     VM Args: --enable-native-access=module-name
 *
 * 非模块化应用：
 *     Compile Args: --add-modules jdk.incubator.foreign
 *     VM Args: --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign
 * </pre>
 */
public class Test {
    /*
    public static void main(String[] args) throws Throwable {
        // scanf & printf
        var aPtr = Pointer.of(0);
        var bPtr = Pointer.of(0L);

        int res = scanf("%d %lld", aPtr, bPtr);
        printf("scanf: a = %d, b = %lld, return value = %d.\n", aPtr.get(), bPtr.get(), res);

        printf("---------------------------------------------\n");

        printf("%s %s!\n", "Hello", "World");
        printf("%d / %.1f = %.6lf\n", 10, 3.0f, 10 / 3.0);
        printf("The time is: %s\n", Time.now());
    }

    public static int printf(String format, Object... args) {
        var cLinker = CLinker.getInstance();
        var lookup = CLinker.systemLookup();

        var printf = cLinker.downcallHandle(
                // vprintf才能传入va_list
                lookup.lookup("vprintf").orElse(null),
                MethodType.methodType(int.class, MemoryAddress.class, CLinker.VaList.class),
                FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_VA_LIST)
        );

        // 只属于本线程，可释放的“内存区域”（try结束后自动释放）
        try (var scope = ResourceScope.newConfinedScope()) {
            if (args.length == 0) {
                // printf(char *format);
                return (int) printf.invokeExact(CLinker.toCString(format, scope).address(),
                        CLinker.VaList.empty());
            }

            Consumer<CLinker.VaList.Builder> builderConsumer = builder -> {
                for (var arg : args) {
                    switch (arg) {
                        case Integer i -> builder.vargFromInt(CLinker.C_INT, i);
                        case Long l -> builder.vargFromLong(CLinker.C_LONG_LONG, l);
                        case Float f -> builder.vargFromDouble(CLinker.C_DOUBLE, f);
                        case Double d -> builder.vargFromDouble(CLinker.C_DOUBLE, d);
                        case String s -> builder.vargFromAddress(CLinker.C_POINTER, CLinker.toCString(s, scope));
                        default -> throw new IllegalStateException("Unsupported type: " + arg.getClass().getSimpleName());
                    }
                }
            };

            // printf(char *format, ...);
            return (int) printf.invokeExact(CLinker.toCString(format, scope).address(),
                    CLinker.VaList.make(builderConsumer, scope));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static int scanf(String format, Pointer<?>... ptrs) {
        var cLinker = CLinker.getInstance();
        var lookup = CLinker.systemLookup();

        var scanf = cLinker.downcallHandle(
                // vscanf才能传入va_list
                lookup.lookup("vscanf").orElse(null),
                MethodType.methodType(int.class, MemoryAddress.class, CLinker.VaList.class),
                FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_VA_LIST)
        );

        try (var scope = ResourceScope.newConfinedScope()) {
            if (ptrs.length == 0) {
                throw new RuntimeException("args can not be null!");
            }

            var bindMap = new HashMap<Pointer<?>, MemorySegment>();

            Consumer<CLinker.VaList.Builder> builderConsumer = builder -> {
                for (var ptr : ptrs) {
                    MemorySegment cPtr;

                    if (ptr.type() == Integer.class || ptr.type() == Float.class) {
                        cPtr = MemorySegment.allocateNative(4, scope);
                    } else if (ptr.type() == Long.class || ptr.type() == Double.class || ptr.type() == String.class) {
                        cPtr = MemorySegment.allocateNative(8, scope);
                    } else {
                        throw new RuntimeException("Unsupported type: " + ptr.type().getSimpleName());
                    }

                    builder.vargFromAddress(CLinker.C_POINTER, cPtr);
                    bindMap.put(ptr, cPtr);
                }
            };

            // scanf(char *format, ...);
            int res = (int) scanf.invokeExact(CLinker.toCString(format, scope).address(),
                    CLinker.VaList.make(builderConsumer, scope));

            bindMap.forEach((ptr, cPtr) -> {
                switch (ptr.get()) {
                    case Integer ignored -> ptr.resetUnchecked(MemoryAccess.getInt(cPtr));
                    case Long ignored -> ptr.resetUnchecked(MemoryAccess.getLong(cPtr));
                    case Float ignored -> ptr.resetUnchecked(MemoryAccess.getFloat(cPtr));
                    case Double ignored -> ptr.resetUnchecked(MemoryAccess.getDouble(cPtr));
                    case String ignored -> ptr.resetUnchecked(CLinker.toJavaString(cPtr));
                    default -> throw new IllegalStateException("Unsupported type: " + ptr.type().getSimpleName());
                }
            });

            return res;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    */
}