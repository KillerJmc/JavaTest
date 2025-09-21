import com.jmc.lang.ref.Pointer;
import org.junit.Assert;


void main() {
    // strlen
    var s = "hello";
    long len = strlen(s);

    System.out.println("strlen -> 字符串「" + s + "」的长度是：" + len);
    Assert.assertEquals(5L, len);

    // scanf
    var namePtr = Pointer.of("null");
    var agePtr = Pointer.of(-1);
    // 这里不使用c printf，因为有缓冲区刷新问题，无法调用fflush方法
    System.out.print("scanf -> 请输入你的姓名和年龄，用空格隔开：");
    int scanLen = scanf("%s %d", namePtr, agePtr);
    System.out.println("你输入的姓名是：" + namePtr.get() + "；年龄是：" + agePtr.get() + "\n");

    Assert.assertEquals(2, scanLen);

    // printf
    double a = 4.5;
    long b = 3L;
    int printLen = printf("%s -> %.1f - %ld = %.1f\n", "printf", a, b, a - b);

    Assert.assertEquals("printf -> 4.5 - 3 = 1.5\n".length(), printLen);
}

/**
 * C本地函数：int printf(const char *format, ...)
 * @param format 格式化字符串
 * @param vargs 参数列表
 * @return 打印的字符串长度
 */
static int printf(String format, Object... vargs) {
    // 函数名
    var funcName = "printf";

    // 获取Linker对象
    var linker = Linker.nativeLinker();

    // 参数类型列表：包含格式化字符串指针和可变参数
    var argTypeList = new ArrayList<MemoryLayout>(List.of(ValueLayout.ADDRESS));
    // 依次填充可变参数对应的类型
    Arrays.stream(vargs).forEach(arg -> {
        MemoryLayout argType = switch (arg) {
            case Byte _ -> ValueLayout.JAVA_BYTE;
            case Boolean _ -> ValueLayout.JAVA_BOOLEAN;
            case Character _ -> ValueLayout.JAVA_CHAR;
            case Short _ -> ValueLayout.JAVA_SHORT;
            case Integer _ -> ValueLayout.JAVA_INT;
            case Long _ -> ValueLayout.JAVA_LONG;
            case Float _ -> ValueLayout.JAVA_FLOAT;
            case Double _ -> ValueLayout.JAVA_DOUBLE;
            case String _ -> ValueLayout.ADDRESS;
            default -> throw new IllegalStateException("不支持的参数类型：" + arg.getClass());
        };
        argTypeList.add(argType);
    });

    // 第一个可变参数在上述参数列表中的下标
    var firstVariadicArgIdx = 1;

    // 获取C函数，传入函数名、函数返回值和参数：int printf(const char *format, ...)
    var printf = linker.downcallHandle(
            linker.defaultLookup().find(funcName).orElseThrow(),
            // 函数签名：返回值类型 (int)，参数类型 (格式化字符串指针, 可变参数列表)
            FunctionDescriptor.of(ValueLayout.JAVA_INT, argTypeList.toArray(MemoryLayout[]::new)),
            // 标记第一个可变参数的下标
            Linker.Option.firstVariadicArg(firstVariadicArgIdx)
    );

    // 管理内存分配，在try结束后自动释放内存
    try (var arena = Arena.ofConfined()) {
        // 分配内存，并生成一个C字符串为format参数
        var formatArg = arena.allocateFrom(format);

        // 参数列表
        var argList = Stream.concat(Stream.of(formatArg), Arrays.stream(vargs))
                .map(arg -> {
                    // 把所有String转化为c_string
                    if (arg instanceof String s) {
                        return arena.allocateFrom(s);
                    }
                    return arg;
                })
                .toArray();

        // 执行函数：必须使用invokeWithArguments才能传入Object[]，调用invoke/invokeExact会报参数错误异常
        return (int) printf.invokeWithArguments(argList);
    } catch (Throwable e) {
        throw new RuntimeException(e);
    }
}

/**
 * C本地函数：int scanf(const char *format, ...)
 * @param format 格式化字符串
 * @param vargs 输入参数的指针列表
 * @return 打印的字符串长度
 */
static int scanf(String format, Pointer<?>... vargs) {
    // 函数名
    var funcName = "scanf";

    // 获取Linker对象
    var linker = Linker.nativeLinker();

    // 参数类型列表：包含格式化字符串指针和可变参数
    var argTypeList = new ArrayList<MemoryLayout>(List.of(ValueLayout.ADDRESS));
    // 依次填充可变参数（都是地址类型）
    Arrays.stream(vargs).forEach(_ -> argTypeList.add(ValueLayout.ADDRESS));

    // 第一个可变参数在上述参数列表中的下标
    var firstVariadicArgIdx = 1;

    // 获取C函数，传入函数名、函数返回值和参数：int printf(const char *format, ...)
    var scanf = linker.downcallHandle(
            linker.defaultLookup().find(funcName).orElseThrow(),
            // 函数签名：返回值类型 (int)，参数类型 (格式化字符串指针, 可变参数列表)
            FunctionDescriptor.of(ValueLayout.JAVA_INT, argTypeList.toArray(MemoryLayout[]::new)),
            // 标记第一个可变参数的下标
            Linker.Option.firstVariadicArg(firstVariadicArgIdx)
    );

    // 管理内存分配，在try结束后自动释放内存
    try (var arena = Arena.ofConfined()) {
        // 分配内存，并生成一个C字符串为format参数
        var formatArg = arena.allocateFrom(format);

        // 参数指针列表（每个参数初始化值都设置为-1）
        var argAddrList = Arrays.stream(vargs)
                .map(_ -> arena.allocate(ValueLayout.ADDRESS))
                .toList();

        // 参数列表
        var argList = Stream.concat(Stream.of(formatArg), argAddrList.stream()).toArray();

        // 执行函数：必须使用invokeWithArguments才能传入Object[]，调用invoke/invokeExact会报参数错误异常
        var scanLen = (int) scanf.invokeWithArguments(argList);

        // 把传入参数放回Java指针中
        for (int idx = 0; idx < argAddrList.size(); idx++) {
            // 参数的C指针
            var argAddr = argAddrList.get(idx);
            // 参数的Java指针
            var argPtr = vargs[idx];

            // 读取指针，从参数指针的最开始获取参数值
            Object value = switch (argPtr.type()) {
                case Class<?> c when c == Byte.class -> argAddr.get(ValueLayout.JAVA_BYTE, 0);
                case Class<?> c when c == Boolean.class -> argAddr.get(ValueLayout.JAVA_BOOLEAN, 0);
                case Class<?> c when c == Character.class -> argAddr.get(ValueLayout.JAVA_CHAR, 0);
                case Class<?> c when c == Short.class -> argAddr.get(ValueLayout.JAVA_SHORT, 0);
                case Class<?> c when c == Integer.class -> argAddr.get(ValueLayout.JAVA_INT, 0);
                case Class<?> c when c == Long.class -> argAddr.get(ValueLayout.JAVA_LONG, 0);
                case Class<?> c when c == Float.class -> argAddr.get(ValueLayout.JAVA_FLOAT, 0);
                case Class<?> c when c == Double.class -> argAddr.get(ValueLayout.JAVA_DOUBLE, 0);
                case Class<?> c when c == String.class -> argAddr.getString(0, java.nio.charset.StandardCharsets.UTF_8);
                default -> throw new IllegalStateException("不支持的指针类型：" + argPtr.type());
            };
            // 把参数值设置回Java指针中
            argPtr.resetUnchecked(value);
        }

        return scanLen;
    } catch (Throwable e) {
        throw new RuntimeException(e);
    }
}

/**
 * C本地函数：size_t strlen(const char *s)
 * @param s 字符串s
 * @return C字符串长度
 */
static long strlen(String s) {
    // 函数名
    var funcName = "strlen";

    // 获取Linker对象
    var linker = Linker.nativeLinker();

    // 获取C函数，传入函数名、函数返回值和参数：size_t strlen(const char *s)
    var strlen = linker.downcallHandle(
            linker.defaultLookup().find(funcName).orElseThrow(),
            // 函数签名：返回值类型 (long)，参数类型 (字符串指针)
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
    );

    // 管理内存分配，在try结束后自动释放内存
    try (var arena = Arena.ofConfined()) {
        // 分配内存，并生成一个C字符串为函数参数
        var cStr = arena.allocateFrom(s);
        // 执行函数，返回结果
        return (long) strlen.invokeExact(cStr);
    } catch (Throwable e) {
        throw new RuntimeException(e);
    }
}
