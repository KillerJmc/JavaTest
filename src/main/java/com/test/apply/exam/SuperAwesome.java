package com.test.apply.exam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

/**
 * 题作者：114514 <br>
 * 来自：2022年10月-21级后端Java考核第1题 <br>
 * 题目：<br>
 * <br>
 * 泛型、反射、注解专场 <br>
 * 某猿人：指望我直接让你写什么是不可能的，现实写代码全是结合现实需求的，所以自己理解题目吧。 <br>
 * <br>
 * <b>Round 1：注解 + 反射</b> <br>
 * 众所周知，每艘战舰都是由蓝图起步的，只要蓝图不动，战舰就会一模一样。 <br>
 * 而战舰有了蓝图之后，就会被造船厂造出来。 <br>
 * 现有这些类 <br>
 * <pre>{@code
 * // imports..
 * public class Main {
 *     public static void main(String args[]) {
 *         Shipyard.constructDestroyer().info();
 *     }
 * }
 *
 * public class Armament {
 *     String name;
 *     Integer caliber;
 * }
 *
 * // imports..
 * public class Destroyer implements WarShip {
 *     @BluePrint
 *     Armament mainArtillery;
 *     public void info() {
 *         System.out.println(mainArtillery);
 *     }
 * }
 * }</pre>
 * 此处的value在武备Armament中指口径caliber，在装甲shell中指厚度thick <br>
 * <pre>{@code
 * @Target(ElementType.FIELD)
 * @Retention(RetentionPolicy.RUNTIME)
 * public @interface BluePrint {
 *     String name() default "装备";
 *     int value() default 127;
 * }
 * }</pre>
 * --这些类和战舰没有任何关系，现在请你求解圆周率小数点后七位~~--（划掉） <br>
 * 请你补全Shipyard这个类，使Main里的main方法可以正常工作，即造船厂可以生产驱逐舰 <br>
 * <pre>{@code
 * public class Shipyard {
 *     public static WarShip constructDestroyer() {
 *         // Need Implement
 *     }
 * }
 * }</pre>
 * <b>Round 2：Round 1 + 泛型</b> <br>
 * 很明显，造船厂不可能只生产驱逐舰Destroyer。 <br>
 * 现代水面主力战舰种类有驱逐舰（含护卫舰）、巡洋舰（虽然逐渐退役）、战列舰（虽然已经消失）、航空母舰 <br>
 * 我们可以简单认为战舰由武备和装甲构成【真的是简单认为，实际有侦察、动力、后勤、通信、信息化等等等等】 <br>
 * 这时聪明的你就会发现，好像第一题的造船厂造出来的船没有装甲啊！ <br>
 * 所以我们再给一个类来表示装甲shell <br>
 * <pre>{@code
 * public class Shell {
 *     String name;
 *     Integer thick;
 * }
 * }</pre>
 *
 * 为了不干扰第一题，我们假设驱逐舰本来就没有装甲（实际上驱逐舰的装甲也可以说是忽略不计，比如说美国菲茨杰拉德号驱逐舰那次撞船事故）。 <br>
 * 所以咱们就从巡洋舰Cruiser下手，于是就有了 <br>
 * <pre>{@code
 * // imports..
 * public class Cruiser implements WarShip {
 *     @BluePrint
 *     Armament mainArtillery;
 *     @BluePrint
 *     Shell shell;
 *     public void info() {
 *         System.out.println(mainArtillery + shell);
 *     }
 * }
 * }</pre>
 * 很明显，有了巡洋舰之后，造船厂也不能只造驱逐舰了（就和江南造船厂不止造055还造003） <br>
 * 所以说，题目一的那个Shipyard很明显不适合了，要改造升级扩容！ <br>
 * 请你将优化后的造船厂实现出来吧（如有更好的设计可以自行重载） <br>
 * <pre>{@code
 * public class Shipyard {
 *     public static <T> WarShip dryDock(T shipDesign) {
 *         // Need Implement
 *     }
 * }
 * }</pre>
 * <b>注意：要求是干船坞Dry Dock可以同时处理驱逐舰和巡洋舰，不许硬编码为驱逐舰工厂和巡洋舰工厂；渲染架构都统一成Unified Shader Architecture了你害分两个的话是不是不太好啊</b> <br>
 * <br>
 * <b>Round 3：Round 2 Pro Max Plus</b> <br>
 * 我不演了，既然巡洋舰都出来了，那么咱们直接假设战列舰Battle Ship有一堆炮和一堆装甲结构；航空母舰Aircraft Career没有炮是有舰载机【当然真实情况是战列舰还有飞机，航母也留着防空炮】 <br>
 * 所以咱们首先得把武备分割成两个不同的类了，一个是火炮；另一个是舰载机。 <br>
 * 要求火炮起码要有口径、身管长度、射程三个数据；舰载机要有起飞重量、弹种、载弹量、航程四个数据。 <br>
 * 装甲结构咱们就不动了（实际中当然分了很多比如水密隔舱、穹甲等等） <br>
 * 这么整的话，原有的蓝图注解也不够用了，所以蓝图也要更新！ <br>
 * 具体怎么更新随便你。 <br>
 * 这一次，我把所有的想法都留给你来实现，从头到尾（其实是我懒了）。 <br>
 * 我只给一个类，我希望你能让这个类里充满内容 <br>
 * <pre>{@code
 * public class Fleet {
 * @BluePrint(/ * Content * /)
 * public List<WarShip> destroyers;
 * @BluePrint(/ * Content * /)
 * public List<WarShip> cruisers;
 * @BluePrint(/ * Content * /)
 * public List<WarShip> battleShips;
 * @BluePrint(/ * Content * /)
 * public List<WarShip> aircraftCareers;
 * }
 * }</pre>
 * 不过我希望你第二题的Dry Dock可以稍作修改就能套在这一题，不然泛型也没啥意义不是吗（笑） <br>
 * <pre>{@code
 * public class Shipyard {
 *     public static <T> WarShip dryDock(T shipDesign) {
 *         // Need Implement
 *     }
 * }
 * }</pre>
 * 这三道题其实有点不做人，因为并没有详细说要实现什么要用什么，但是我认为这恰恰是我们一路过来所缺失的内容，我们太过于强调技术的细枝末节，强调各种偏门怪异的用法，仿佛越难用越能体现出实力；但我认为这种强调细枝末节的其实是南辕北辙，技术是为人服务的，而不是人为技术服务，我们永远都是工具的主人，工具不应该成为我们的掣肘；我认为考核更应该考的是思想，是思维，是从0到1的飞跃；毕竟码农，CV工程师到处都是，但是架构师、工程师一直都很稀缺；我希望看到这些的你们能忍住骂我的冲动，静下心去思考这些实际的问题，就我个人经历的一些项目来看，如果程序员想真正发挥出他自身的价值，具备相当的不可替代性，他面对的问题会比这三道题多得多；他面对的场景会比这三道题复杂得多。所以，如果你能冷静下来完成这三道题尤其是第三题，我会相当感激。
 */
public class SuperAwesome {
    public static void main(String[] args) {
        Shipyard.constructDestroyer().info();
        Shipyard2.dryDock(new Cruiser()).info();
        Shipyard3.dryDock(new Cruiser()).info();
    }
}

class Armament {
    String name;
    Integer caliber;
}

class Destroyer implements WarShip {
    @BluePrint
    Armament mainArtillery;
    public void info() {
        System.out.println(mainArtillery);
    }
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface BluePrint {
    String name() default "装备";
    int value() default 127;
}

@SuppressWarnings("unused")
class Shell {
    String name;
    Integer thick;
}

class Cruiser implements WarShip {
    @BluePrint
    Armament mainArtillery;
    @BluePrint
    Shell shell;
    public void info() {
        System.out.println("" + mainArtillery + shell);
    }
}

/**
 * Round 1
 */
interface WarShip {
    void info();
}

class Shipyard {
    public static WarShip constructDestroyer() {
        var res = new Destroyer();
        try {
            // 获取成员变量mainArtillery
            var mainArtilleryField = Destroyer.class.getDeclaredField("mainArtillery");
            // 从成员变量mainArtillery取注解
            var bluePrintAnno = mainArtilleryField.getAnnotation(BluePrint.class);

            // 构建成员变量
            var mainArtillery = new Armament();

            // 设置值
            Armament.class.getDeclaredField("name").set(mainArtillery, bluePrintAnno.name());
            Armament.class.getDeclaredField("caliber").set(mainArtillery, bluePrintAnno.value());

            // 注入成员变量
            mainArtilleryField.set(res, mainArtillery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}

/**
 * Round 2
 */
class Shipyard2 {
    public static <T> WarShip dryDock(T shipDesign) {
        try {
            Arrays.stream(shipDesign.getClass().getDeclaredFields())
                    .forEach(field -> {
                        try {
                            // 从成员变量取注解
                            var anno = field.getAnnotation(BluePrint.class);
                            // 获得成员变量的类型
                            var fieldType = field.getType();
                            // 创建成员变量的值
                            var fieldValue = fieldType.getDeclaredConstructor().newInstance();
                            // 获取成员变量的变量们
                            var fieldFields = fieldType.getDeclaredFields();

                            // 从注解塞入值注入成员变量的值
                            for (var fieldField : fieldFields) {
                                if (fieldField.getType() == String.class) {
                                    fieldField.set(fieldValue, anno.name());
                                } else if (fieldField.getType() == Integer.class) {
                                    fieldField.set(fieldValue, anno.value());
                                } else {
                                    throw new RuntimeException("Type Error!");
                                }
                            }

                            // 把成员变量注入回去
                            field.set(shipDesign, fieldValue);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (WarShip) shipDesign;
    }
}

/**
 * Round 3
 */
@SuppressWarnings("all")
@interface NewBluePrint {
    String 内容() default "内容";
}

@SuppressWarnings("unused")
class Fleet {
    @NewBluePrint(内容 = "填满内容：destroyers")
    public List<WarShip> destroyers;
    @NewBluePrint(内容 = "填满内容：cruisers")
    public List<WarShip> cruisers;
    @NewBluePrint(内容 = "填满内容：battleShips")
    public List<WarShip> battleShips;
    @NewBluePrint(内容 = "填满内容：aircraftCareers")
    public List<WarShip> aircraftCareers;
}

class Shipyard3 {
    @SuppressWarnings("unused")
    public static <T> WarShip dryDock(T shipDesign) {
        return () -> System.out.println("充满内容！");
    }
}






