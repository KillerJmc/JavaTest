package com.test.apply.ffmpeg;

import com.jmc.io.Files;
import com.jmc.lang.Objs;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.File;
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
                    + INPUT_VIDEO_PATH_CMD_KEY + inputVideoPath + blank
                    + Preset.CMD_KEY + preset.CMD_VALUE + blank
                    + (outputVideoEncoder == null ? emptyStr : OutputVideoEncoder.CMD_KEY + outputVideoEncoder.CMD_VALUE + blank)
                    + (outputVideoFps == null ? emptyStr : OUTPUT_VIDEO_FPS_CMD_KEY + outputVideoFps + blank)
                    + (audioBitrate == null ? emptyStr : AudioBitrate.CMD_KEY + audioBitrate.CMD_VALUE + blank)
                    + outputVideoPath;
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
     * 获取压缩视频的ffmpeg脚本
     * @param ffmpegBinPath ffmpeg二进制文件路径
     * @param inputVideoDir 待转换视频根目录
     * @param outputVideoDir 输出视频根路径（会在输出视频文件夹中保留原视频路径结构）
     * @return 压缩视频的ffmpeg脚本
     */
    public static String getCompressVideoScript(String ffmpegBinPath, String inputVideoDir, String outputVideoDir) {
        // 创建输出视频根路径
        Files.mkdirs(outputVideoDir);

        // 规范化路径
        var normalizedInputVideoDir = Files.getAbsolutePath(inputVideoDir);
        var normalizedOutputVideoDir = Files.getAbsolutePath(outputVideoDir);

        // 换行字符串
        var newLine = "\n";

        return Files.findFiles(inputVideoDir, "")
                .stream()
                .map(File::getAbsolutePath)
                .map(srcPath -> {
                    // 提取相对路径
                    var videoRelativePath = srcPath.replace(normalizedInputVideoDir, "");
                    // 视频结果路径
                    var desPath = normalizedOutputVideoDir + videoRelativePath;

                    // 视频结果父路径
                    var desRootPath = Files.getParentPath(desPath);
                    // 创建父路径
                    Files.mkdirs(desRootPath);

                    // 返回具体命令
                    return getH265TransferCmd(ffmpegBinPath, srcPath, desPath).toString();
                })
                .collect(Collectors.joining(newLine));
    }

    public static void main(String[] args) {
        var script = getCompressVideoScript(
                "D:/Temp/ffmpeg/bin/ffmpeg.exe",
                "D:/Temp/结果/视频",
                "D:/Temp/结果/输出"
        );
        System.out.println(script);
    }
}
