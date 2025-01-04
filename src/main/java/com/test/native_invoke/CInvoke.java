import com.jmc.lang.ref.Pointer;
import com.test.native_invoke.Switch;
import org.junit.Assert;
import java.lang.foreign.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


void main() {
    // strlen
    var s = "hello";
    long len = strlen(s);

    System.out.println(STR."strlen -> 字符串「\{s}」的长度是：\{len}");
    Assert.assertEquals(5L, len);

    // scanf
    var namePtr = Pointer.of("null");
    var agePtr = Pointer.of(-1);
    // 这里不使用c printf，因为有缓冲区刷新问题，无法调用fflush方法
    System.out.print("scanf -> 请输入你的姓名和年龄，用空格隔开：");
    int scanLen = scanf("%s %d", namePtr, agePtr);
    System.out.println(STR."你输入的姓名是：\{namePtr.get()}；年龄是：\{agePtr.get()}\n");

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
@SuppressWarnings("preview")
static int printf(String format, Object... vargs) {
    // 函数名
    var funcName = "printf";

    // 获取Linker对象
    var linker = Linker.nativeLinker();

    // 参数类型列表：包含格式化字符串指针和可变参数
    var argTypeList = new ArrayList<MemoryLayout>(List.of(ValueLayout.ADDRESS));
    // 依次填充可变参数对应的类型
    Arrays.stream(vargs).forEach(arg -> {
        var argType = Switch.<MemoryLayout> judge(arg.getClass())
                .cases(Byte.class, () -> ValueLayout.JAVA_BYTE)
                .cases(Boolean.class, () -> ValueLayout.JAVA_BOOLEAN)
                .cases(Character.class, () -> ValueLayout.JAVA_CHAR)
                .cases(Short.class, () -> ValueLayout.JAVA_SHORT)
                .cases(Integer.class, () -> ValueLayout.JAVA_INT)
                .cases(Long.class, () -> ValueLayout.JAVA_LONG)
                .cases(Float.class, () -> ValueLayout.JAVA_FLOAT)
                .cases(Double.class, () -> ValueLayout.JAVA_DOUBLE)
                .cases(String.class, () -> ValueLayout.ADDRESS)
                .orElseThrow(() -> new IllegalStateException(STR."不支持的参数类型：\{arg.getClass()}"));
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
        var formatArg = arena.allocateUtf8String(format);

        // 参数列表
        var argList = Stream.concat(Stream.of(formatArg), Arrays.stream(vargs))
                .map(arg -> {
                    // 把所有String转化为c_string
                    if (arg instanceof String s) {
                        return arena.allocateUtf8String(s);
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
@SuppressWarnings("preview")
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
        var formatArg = arena.allocateUtf8String(format);

        // 参数指针列表（每个参数初始化值都设置为-1）
        var argAddrList = Arrays.stream(vargs)
                .map(_ -> arena.allocate(ValueLayout.ADDRESS, MemorySegment.ofAddress(-1)))
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
            var value = Switch.judge(argPtr.type())
                    .cases(Byte.class, () -> argAddr.get(ValueLayout.JAVA_BYTE, 0))
                    .cases(Boolean.class, () -> argAddr.get(ValueLayout.JAVA_BOOLEAN, 0))
                    .cases(Character.class, () -> argAddr.get(ValueLayout.JAVA_CHAR, 0))
                    .cases(Short.class, () -> argAddr.get(ValueLayout.JAVA_SHORT, 0))
                    .cases(Integer.class, () -> argAddr.get(ValueLayout.JAVA_INT, 0))
                    .cases(Long.class, () -> argAddr.get(ValueLayout.JAVA_LONG, 0))
                    .cases(Float.class, () -> argAddr.get(ValueLayout.JAVA_FLOAT, 0))
                    .cases(Double.class, () -> argAddr.get(ValueLayout.JAVA_DOUBLE, 0))
                    .cases(String.class, () -> argAddr.getUtf8String(0))
                    .orElseThrow(() -> new IllegalStateException(STR."不支持的指针类型：\{argPtr.type()}"));
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
@SuppressWarnings("preview")
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
        var cStr = arena.allocateUtf8String(s);
        // 执行函数，返回结果
        return (long) strlen.invokeExact(cStr);
    } catch (Throwable e) {
        throw new RuntimeException(e);
    }
}
