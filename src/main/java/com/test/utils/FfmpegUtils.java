package com.test.utils;

import com.jmc.io.Files;
import com.jmc.lang.Objs;
import com.jmc.lang.Run;
import com.jmc.lang.Strs;
import com.jmc.util.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ffmpeg工具类（CPU转换，跨平台）
 * @author Jmc
 */
public class FfmpegUtils {
    /**
     * ffmpeg命令
     */
    @Builder
    public static class FfmpegCmd {
        /**
         * ffmpeg二进制文件路径（必须）
         */
        private String ffmpegBinPath;

        /**
         * 待转换视频路径（必须）
         */
        private String inputVideoPath;

        /**
         * 输出视频路径（必须）
         */
        private String outputVideoPath;

        /**
         * 预设：表示转换速度，和转换质量成反比（必须）
         */
        private Preset preset;

        /**
         * 输出视频编码器
         */
        private OutputVideoEncoder outputVideoEncoder;

        /**
         * H265兼容性：和苹果产品（Iphone、Mac等）的默认视频播放器兼容
         */
        private OutputVideoEncoder.H265CompatibleWithAppleDevice h265CompatibleWithAppleDevice;

        /**
         * 输出视频的帧数
         */
        private Long outputVideoFps;

        /**
         * 输出视频的音频比特率
         */
        private AudioBitrate audioBitrate;

        /**
         * 是否强制覆盖已存在的输出视频
         */
        private boolean overrideOutputVideo;

        /**
         * 输入视频文件路径的命令键
         */
        private static final String INPUT_VIDEO_PATH_CMD_KEY = "-i";

        /**
         * 输出视频帧数的命令键
         */
        private static final String OUTPUT_VIDEO_FPS_CMD_KEY = "-r";

        /**
         * 强制覆盖已存在的输出视频的命令 + 标记无控制台输入（没有覆盖前询问也就不会有控制台输入了，主要用于后台执行）
         */
        private static final String OVERRIDE_OUTPUT_VIDEO_CMD = "-y -nostdin";

        /**
         * 预设
         * @author Jmc
         */
        @AllArgsConstructor
        public enum Preset {
            /**
             * 快速
             */
            FAST("fast");

            /**
             * 命令键
             */
            private final static String CMD_KEY = "-preset";

            /**
             * 命令值
             */
            private final String CMD_VALUE;
        }

        /**
         * 输出视频的编码器
         * @author Jmc
         */
        @AllArgsConstructor
        public enum OutputVideoEncoder {
            /**
             * H264编码器
             */
            H264("libx264"),

            /**
             * H265编码器
             */
            H265("libx265");

            /**
             * 命令键
             */
            private final static String CMD_KEY = "-c:v";

            /**
             * 命令值
             */
            private final String CMD_VALUE;

            /**
             * H265模式和苹果设备（Iphone、Mac等）的默认视频播放器兼容
             */
            @AllArgsConstructor
            public enum H265CompatibleWithAppleDevice {
                /**
                 * 使用codec标签：hvc1，提供苹果设备兼容性
                 */
                TRUE("-tag:v hvc1"),

                /**
                 * 默认使用了codec标签：hev1，苹果设备不兼容
                 */
                FALSE("");

                /**
                 * 命令
                 */
                private final String CMD;
            }
        }

        /**
         * 输出视频的音频比特率
         */
        @AllArgsConstructor
        public enum AudioBitrate {
            /**
             * 普通品质
             */
            NORMAL("128k"),

            /**
             * 良好品质
             */
            GOOD("196k");

            /**
             * 命令键
             */
            private final static String CMD_KEY = "-b:a";

            /**
             * 命令值
             */
            private final String CMD_VALUE;
        }

        /**
         * 返回ffmpeg命令
         * @return ffmpeg命令
         */
        @Override
        public String toString() {
            Objs.throwsIfNullOrEmpty("必须的参数为空！", ffmpegBinPath, inputVideoPath, outputVideoPath, preset);

            String blank = " ", emptyStr = "", newLine = "\n";

            // ffmpeg -i "input/path" -preset fast -c:v libx265 -r 30 -b:a 128k "output/path"
            return STR."""
            \{ffmpegBinPath} \{INPUT_VIDEO_PATH_CMD_KEY} "\{inputVideoPath}"
            \{Preset.CMD_KEY} \{preset.CMD_VALUE}
            \{outputVideoEncoder == null ? emptyStr : STR."\{OutputVideoEncoder.CMD_KEY} \{outputVideoEncoder.CMD_VALUE}"}
            \{h265CompatibleWithAppleDevice == null ? emptyStr : h265CompatibleWithAppleDevice.CMD}
            \{outputVideoFps == null ? emptyStr : STR."\{OUTPUT_VIDEO_FPS_CMD_KEY} \{outputVideoFps}"}
            \{audioBitrate == null ? emptyStr : STR."\{AudioBitrate.CMD_KEY} \{audioBitrate.CMD_VALUE}"}
            \{overrideOutputVideo ? OVERRIDE_OUTPUT_VIDEO_CMD : emptyStr}
            "\{outputVideoPath}"
            """.stripTrailing().replace(newLine, blank);
        }
    }

    /**
     * 视频编码类型
     * @author Jmc
     */
    @AllArgsConstructor
    public enum VideoEncoder {
        /**
         * h264编码
         */
        H264("h264"),

        /**
         * h265编码
         */
        H265("hevc"),

        /**
         * 未知类型
         */
        UNKNOWN("unknown");

        /**
         * 编码类型对应的ffprobe输出值
         */
        private final String FFPROBE_VALUE;
    }

    /**
     * 获取视频的编码类型
     * @param ffprobeBinPath ffprobe二进制文件路径
     * @param videoPath 待分析视频路径
     * @return 视频的编码类型
     */
    public static VideoEncoder getVideoEncoder(String ffprobeBinPath, String videoPath) {
        // 获取视频的编码类型的命令
        var cmd = """
        %s -v error -select_streams v:0 -show_entries stream=codec_name -of default=noprint_wrappers=1:nokey=1 %s
        """.formatted(ffprobeBinPath, videoPath);

        // 执行命令获取结果
        var res = Run.execToStr(cmd).trim();

        // 判断结果对应的文件编码类型并返回
        return Arrays.stream(VideoEncoder.values())
                .filter(videoEncoder -> res.contains(videoEncoder.FFPROBE_VALUE))
                .findAny()
                .orElse(VideoEncoder.UNKNOWN);
    }

    /**
     * 生成将视频转换为h265格式的默认命令
     *
     * @param ffmpegBinPath       ffmpeg二进制文件路径
     * @param inputVideoPath      待转换视频路径
     * @param outputVideoPath     输出视频路径
     * @param overrideOutputVideo 是否覆盖已存在的输出视频
     * @return 将视频转换为h265格式的命令
     */
    public static FfmpegCmd getTransferH265DefaultCmd(String ffmpegBinPath,
                                                      String inputVideoPath,
                                                      String outputVideoPath,
                                                      boolean overrideOutputVideo) {
        var fixedFps = 30L;

        return FfmpegCmd.builder()
                .ffmpegBinPath(ffmpegBinPath)
                .inputVideoPath(inputVideoPath)
                .outputVideoPath(outputVideoPath)
                .preset(FfmpegCmd.Preset.FAST)
                .outputVideoEncoder(FfmpegCmd.OutputVideoEncoder.H265)
                .h265CompatibleWithAppleDevice(FfmpegCmd.OutputVideoEncoder.H265CompatibleWithAppleDevice.TRUE)
                .outputVideoFps(fixedFps)
                .audioBitrate(FfmpegCmd.AudioBitrate.NORMAL)
                .overrideOutputVideo(overrideOutputVideo)
                .build();
    }

    /**
     * 获取压缩H265视频的ffmpeg命令列表
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param ffprobeBinPath ffprobe二进制文件路径
     * @param inputVideoDir 待转换视频根目录
     * @param outputVideoDir 输出视频根路径（会在输出视频文件夹中保留原视频路径结构）
     * @return 压缩视频的ffmpeg命令列表
     */
    public static List<FfmpegCmd> getCompressH265VideoCmds(String ffmpegBinPath,
                                                        String ffprobeBinPath,
                                                        String inputVideoDir,
                                                        String outputVideoDir,
                                                        boolean overrideOutputVideo) {
        // 创建输出视频根路径
        Files.mkdirs(outputVideoDir);

        // 规范化路径
        var normalizedInputVideoDir = Files.getAbsolutePath(inputVideoDir);
        var normalizedOutputVideoDir = Files.getAbsolutePath(outputVideoDir);

        // 字符串常量
        var emptyStr = "";

        return Files.findFiles(inputVideoDir, emptyStr)
                .stream()
                // 获取完整的源路径
                .map(File::getAbsolutePath)
                // 只筛选编码类型正确的视频
                .filter(srcPath -> getVideoEncoder(ffprobeBinPath, srcPath) != VideoEncoder.UNKNOWN)
                // 获取文件结果路径，结果为元组（源路径，结果路径）
                .map(srcPath -> {
                    // 提取相对路径
                    var videoRelativePath = srcPath.replace(normalizedInputVideoDir, emptyStr);

                    // 如果源文件不是mp4格式，输出文件路径就转为mp4格式路径
                    var mp4Suffix = ".mp4";
                    var dotStr = ".";
                    if (!inputVideoDir.endsWith(mp4Suffix)) {
                        // 去除文件后缀
                        var pathWithoutSuffix = Strs.subExclusive(videoRelativePath, emptyStr, dotStr);
                        videoRelativePath = pathWithoutSuffix + mp4Suffix;
                    }

                    // 返回视频结果路径desPath
                    var desPath = normalizedOutputVideoDir + videoRelativePath;
                    return Tuple.fromNamed(Map.entry("srcPath", srcPath), Map.entry("desPath", desPath));
                })
                // 判断是否需要过滤掉结果视频存在的路径
                .filter(tuple -> !(!overrideOutputVideo && Files.exists(tuple.<String>get("desPath"))))
                // 创建父路径
                .peek(tuple -> Files.mkdirs(Files.getParentPath(tuple.get("desPath"))))
                // 获取ffmpeg命令
                .map(tuple -> getTransferH265DefaultCmd(
                        ffmpegBinPath, tuple.get("srcPath"),
                        tuple.get("desPath"),
                        overrideOutputVideo
                ))
                .toList();
    }

    /**
     * 获取压缩H265视频的ffmpeg顺序执行脚本（目标视频路径存在不覆盖）
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param ffprobeBinPath ffprobe二进制文件路径
     * @param inputVideoDir 待转换视频根目录
     * @param outputVideoDir 输出视频根路径（会在输出视频文件夹中保留原视频路径结构）
     * @return 压缩视频的ffmpeg脚本
     */
    public static String getCompressH265VideoSyncScript(String ffmpegBinPath,
                                                        String ffprobeBinPath,
                                                        String inputVideoDir,
                                                        String outputVideoDir) {
        var spliter = "&&";
        return FfmpegUtils.getCompressH265VideoCmds(
                ffmpegBinPath, ffprobeBinPath, inputVideoDir, outputVideoDir, false
                )
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(spliter));
    }

    /**
     * 获取压缩H265视频的ffmpeg并行执行脚本（目标视频路径存在会覆盖）
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param ffprobeBinPath ffprobe二进制文件路径
     * @param inputVideoDir 待转换视频根目录
     * @param outputVideoDir 输出视频根路径（会在输出视频文件夹中保留原视频路径结构）
     * @return 压缩视频的ffmpeg脚本
     */
    public static String getCompressH265VideoAsyncScript(String ffmpegBinPath,
                                                         String ffprobeBinPath,
                                                         String inputVideoDir,
                                                         String outputVideoDir) {
        // shell异步命令模板
        var asyncCmdTemplate = "(%s) &";
        // 等待执行完成的命令
        var waitCmd = "wait";
        var newLine = "\n";
        // 计时相关的
        var getStartTimeCmd = """
        echo "正在把视频转换为H265格式，开始时间是：$(date "+%Y-%m-%d %H:%M:%S")"
        start_epoch=$(date +%s)
        """;
        var endTimeAndCalcCostTimeCmd = """
        
        echo "结束时间是：$(date "+%Y-%m-%d %H:%M:%S")"
        end_epoch=$(date +%s)
        time_diff=$((end_epoch - start_epoch))
        echo "视频已经全部转换为H265格式，一共花费了 $time_diff 秒！"
        """;

        var asyncFfmpegCmds = FfmpegUtils.getCompressH265VideoCmds(
                ffmpegBinPath, ffprobeBinPath, inputVideoDir, outputVideoDir, true
                )
                .stream()
                .map(Object::toString)
                .map(asyncCmdTemplate::formatted)
                .collect(Collectors.toList());
        // 加上开始时间计时命令
        asyncFfmpegCmds.addFirst(getStartTimeCmd);
        // 加上等待执行完成的命令
        asyncFfmpegCmds.addLast(waitCmd);
        // 加上计时命令
        asyncFfmpegCmds.addLast(endTimeAndCalcCostTimeCmd);
        // 每行命令加上换行符
        return String.join(newLine, asyncFfmpegCmds);
    }

    public static void main(String[] args) {
        // 获取异步执行的H265转化脚本
        var script = getCompressH265VideoAsyncScript(
                "ffmpeg",
                "ffprobe",
                "/Volumes/Data/Temp/待转",
                "/Volumes/Data/Temp/输出"
        );

        Files.out(
                script,
                "/Users/jmc/Desktop/out.sh",
                StandardCharsets.UTF_8,
                false
        );
    }
}
