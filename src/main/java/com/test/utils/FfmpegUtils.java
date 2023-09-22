package com.test.utils;

import com.jmc.io.Files;
import com.jmc.lang.Objs;
import com.jmc.lang.Run;
import com.jmc.lang.Strs;
import com.jmc.util.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ffmpeg工具类
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
         * 输出视频的帧数
         */
        private Long outputVideoFps;

        /**
         * 输出视频的音频比特率
         */
        private AudioBitrate audioBitrate;

        /**
         * 输入视频文件路径的命令键
         */
        private static final String INPUT_VIDEO_PATH_CMD_KEY = "-i ";

        /**
         * 输出视频帧数的命令键
         */
        private static final String OUTPUT_VIDEO_FPS_CMD_KEY = "-r ";

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
            private final static String CMD_KEY = "-preset ";

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
            private final static String CMD_KEY = "-c:v ";

            /**
             * 命令值
             */
            private final String CMD_VALUE;
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
            private final static String CMD_KEY = "-b:a ";

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

            String blank = " ", emptyStr = "";

            // ffmpeg -i "input/path" -preset fast -c:v libx265 -r 30 -b:a 128k "output/path"
            return ffmpegBinPath + blank
                    + INPUT_VIDEO_PATH_CMD_KEY + "\"" + inputVideoPath + "\"" + blank
                    + Preset.CMD_KEY + preset.CMD_VALUE + blank
                    + (outputVideoEncoder == null ? emptyStr : OutputVideoEncoder.CMD_KEY + outputVideoEncoder.CMD_VALUE + blank)
                    + (outputVideoFps == null ? emptyStr : OUTPUT_VIDEO_FPS_CMD_KEY + outputVideoFps + blank)
                    + (audioBitrate == null ? emptyStr : AudioBitrate.CMD_KEY + audioBitrate.CMD_VALUE + blank)
                    + "\"" + outputVideoPath + "\"";
        }
    }

    /**
     * 生成将视频转换为h265格式的命令
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param inputVideoPath 待转换视频路径
     * @param outputVideoPath 输出视频路径
     * @return 将视频转换为h265格式的命令
     */
    public static FfmpegCmd getH265TransferCmd(String ffmpegBinPath, String inputVideoPath, String outputVideoPath) {
        var fixedFps = 30L;

        return FfmpegCmd.builder()
                .ffmpegBinPath(ffmpegBinPath)
                .inputVideoPath(inputVideoPath)
                .outputVideoPath(outputVideoPath)
                .preset(FfmpegCmd.Preset.FAST)
                .outputVideoEncoder(FfmpegCmd.OutputVideoEncoder.H265)
                .outputVideoFps(fixedFps)
                .audioBitrate(FfmpegCmd.AudioBitrate.NORMAL)
                .build();
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
     * 获取压缩视频的ffmpeg脚本
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param ffprobeBinPath ffprobe二进制文件路径
     * @param inputVideoDir 待转换视频根目录
     * @param outputVideoDir 输出视频根路径（会在输出视频文件夹中保留原视频路径结构）
     * @return 压缩视频的ffmpeg脚本
     */
    public static String getCompressVideoScript(String ffmpegBinPath, String ffprobeBinPath, String inputVideoDir, String outputVideoDir) {
        // 创建输出视频根路径
        Files.mkdirs(outputVideoDir);

        // 规范化路径
        var normalizedInputVideoDir = Files.getAbsolutePath(inputVideoDir);
        var normalizedOutputVideoDir = Files.getAbsolutePath(outputVideoDir);

        // 字符串常量
        var emptyStr = "";
        var spliter = "&&";

        return Files.findFiles(inputVideoDir, emptyStr)
                .stream()
                // 获取完整的源路径
                .map(File::getAbsolutePath)
                // 只筛选H264的源视频（忽略已经是H265的视频）
                .filter(srcPath -> getVideoEncoder(ffprobeBinPath, srcPath) == VideoEncoder.H264)
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
                    return Tuple.fromNamed(Map.of("srcPath", srcPath, "desPath", desPath));
                })
                // 不覆盖结果视频路径
                .filter(tuple -> !Files.exists(tuple.<String>get("desPath")))
                // 创建父路径
                .peek(tuple -> Files.mkdirs(Files.getParentPath(tuple.get("desPath"))))
                // 获取ffmpeg命令
                .map(tuple -> getH265TransferCmd(ffmpegBinPath, tuple.get("srcPath"), tuple.get("desPath")).toString())
                // 将命令串起来为最终的结果命令
                .collect(Collectors.joining(spliter));
    }

    public static void main(String[] args) {
        var script = getCompressVideoScript(
                "D:/Tools/ffmpeg.exe",
                "D:/Tools/ffprobe.exe",
                "D:\\Temp\\待转",
                "D:\\Temp\\输出"
        );

        Files.out(
                script,
                "C:/Users/Jmc/Desktop/out.bat",
                Charset.forName("GBK"),
                false
        );
    }
}
