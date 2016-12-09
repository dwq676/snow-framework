/**
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zoe.snow.word;

import com.zoe.snow.word.recognition.StopWord;
import com.zoe.snow.word.segmentation.Segmentation;
import com.zoe.snow.word.segmentation.SegmentationAlgorithm;
import com.zoe.snow.word.segmentation.SegmentationFactory;
import com.zoe.snow.word.segmentation.Word;
import com.zoe.snow.word.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 词频统计
 *
 * @author 杨尚川
 */
public class WordFrequencyStatistics {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordSegmenter.class);
    private static WordFrequencyStatistics instance = null;

    static {
        if (instance == null)
            instance = new WordFrequencyStatistics();
    }

    private String resultPath = "WordFrequencyStatistics-Result.txt";
    private Segmentation segmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.MaxNgramScore);
    private Map<String, AtomicInteger> statisticsMap = new ConcurrentHashMap<>();
    private boolean removeStopWord = false;

    /**
     * 默认构造函数
     * 不指定算法则默认使用：最大Ngram分值算法
     * 不指定词频统计结果保存路径默认使用当前路径下的：WordFrequencyStatistics-Result.txt
     */
    public WordFrequencyStatistics() {
    }

    /**
     * 构造函数
     * 不指定算法则默认使用：最大Ngram分值算法
     *
     * @param resultPath 词频统计结果保存路径
     */
    public WordFrequencyStatistics(String resultPath) {
        this.resultPath = resultPath;
    }

    /**
     * 构造函数
     *
     * @param resultPath            词频统计结果保存路径
     * @param segmentationAlgorithm 分词算法
     */
    public WordFrequencyStatistics(String resultPath, SegmentationAlgorithm segmentationAlgorithm) {
        this.resultPath = resultPath;
        this.segmentation = SegmentationFactory.getSegmentation(segmentationAlgorithm);
    }

    /**
     * 构造函数
     *
     * @param resultPath            词频统计结果保存路径
     * @param segmentationAlgorithm 分词算法，要符合 com.zoe.snow.word.segmentation.SegmentationAlgorithm 中的定义
     */
    public WordFrequencyStatistics(String resultPath, String segmentationAlgorithm) {
        this.resultPath = resultPath;
        this.segmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.valueOf(segmentationAlgorithm));
    }

    public static List<Word> matching(String text) {
        instance.setRemoveStopWord(true);
        instance.setSegmentationAlgorithm(SegmentationAlgorithm.SmartMatching);
        //开始分词
        if (text == null)
            return new ArrayList<>();
        return instance.seg(text);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            //词频统计设置
            WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
            Set<String> textFiles = new HashSet<>();
            for (String arg : args) {
                if (arg.equals("-removeStopWord")) {
                    wordFrequencyStatistics.setRemoveStopWord(true);
                }
                if (arg.startsWith("-textFile=")) {
                    textFiles.add(arg.replace("-textFile=", ""));
                }
                if (arg.startsWith("-statisticsResultFile=")) {
                    wordFrequencyStatistics.setResultPath(arg.replace("-statisticsResultFile=", ""));
                }
                if (arg.startsWith("-segmentationAlgorithm=")) {
                    wordFrequencyStatistics.setSegmentationAlgorithm(SegmentationAlgorithm.valueOf(arg.replace("-segmentationAlgorithm=", "")));
                }
            }
            for (String textFile : textFiles) {
                wordFrequencyStatistics.seg(new File(textFile), (new File(textFile + ".seg.txt")));
            }
            wordFrequencyStatistics.dump();
            return;
        }
        //词频统计设置
        //WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
        instance.setRemoveStopWord(true);
        //wordFrequencyStatistics.setResultPath("word-frequency-statistics.txt");
        instance.setSegmentationAlgorithm(SegmentationAlgorithm.SmartMatching);
        //开始分词
        List<Word> words = instance.seg("2011年12月中旬起无明显诱因反复出现腹泻，约2-4次/日，黄色糊状，偶混有粘液脓血。无腹胀、腹痛；无排便困难、里急后重、肛门坠痛、大便变细；无头晕、乏力、面色苍白；无活动后心悸、气促；无恶心、呕吐、呕血、黑便；无发热、盗汗、咳嗽、咳痰、消瘦。2012年3月中旬求诊厦门长庚医院，行肠镜（2012-3-16）：“距肛门18cm见不规则肿物，约4*3.5cm，考虑乙状结肠癌。距肛门24-27cm，见8枚息肉。距肛门9CM，见3枚息肉。直肠及乙状结肠息肉予肠镜下切除”。病理（福建省肿瘤医院12-2462）：“1.乙状结肠腺癌；2.乙状结肠腺瘤；3.直肠腺瘤”。遂转诊福建省肿瘤医院，完善分期检查，于2012年3月22日全麻下行“腹腔镜辅助乙状结肠癌根治术”。术中见：“乙状结肠可及一肿物，大小约4*4.5cm，侵及浆膜，肠腔狭窄，肠系膜下动脉根部及乙状结肠动脉旁未触及肿大淋巴结，其他大肠未触及明显肿物”。术程顺利，术后恢复好。术后病理（2012-3-29福建省肿瘤医院12-03125）：“1.（乙状结肠）隆起型中分化腺癌（肿物大小3*2.5CM），侵犯肠周脂肪组织，脉管内见癌栓。标本两切端及另送“上、下切端”未见癌。“直肠上动脉旁淋巴结”7/0，“肠系膜下动脉根部淋巴结”4/0未见转移癌。2.管状腺瘤伴低级别上皮内肿瘤”。于2012年3月26日行DC-CIK免疫治疗1次。术后转诊我科，于2012年04月20日-2012年7月18日行FOLFOX方案（L-OHP130mgd1+CF0.6d1+5-FU0.5gd1+5-FU3.5gciv46hd1-214天/周期）化疗6周期，过程顺利，无不适。2012-7-1复查胸部CT：双肺散在点状影及右肺尖疑小结节，请随访观察。腹部CT：乙状结肠癌术后化疗后复查：无腹部CT旧片对照；膀胱左侧异常占位样影，建议MR复查；余全腹部CT平扫+增强扫描未见明显复发及转移证。评效；SD。目前进普食顺利，无发热、恶心、呕吐、呕血、黑便；无腹痛、腹胀、腹泻、便血；无头晕、乏力、面色苍白、活动后心悸、气促；无肛周坠痛、腰骶部酸痛；无咳嗽、咳痰、胸痛、气促、盗汗；无眼黄、尿黄、皮肤黄；无尿频、尿急、尿痛。为进一步治疗，今求诊我科，诊断为“乙状结肠中分化腺癌根治术后pT4aN0M0ⅡB期化疗后”收住入院。末次出院以来，精神体力尚好，生活自理。食欲食量正常。夜里睡眠好，大便同上述，小便正常，体重无明显增减");
        words.forEach(word -> System.out.println(word.toString()));

        //近7天发热最高体温，发热39.3，咳嗽，流涕， 无呕吐、腹泻，无粘液脓血，无腹胀，无尿频、尿急，无肉眼血尿，精神、饮食可，尿量可，
        //双乳晕皮肤破溃渗出1个月
        //1年前无明显诱因出现胸闷、气喘，伴咳嗽、咳痰，无发热，无胸痛，无心悸
        //乳腺增生症状反复3年
        //活动后气急，近年逐渐加重，心脏彩超检查 诊断为 风湿性心脏病 二尖瓣狭窄并关闭不全，来院手术治疗
        //双乳晕皮肤破溃渗出1个月
        //昨起腹泻，解黄色糊状便8-9次，无呕吐、脐周阵痛，无发热
        //上腹部发面大大的异物确认为肿块"
        //因无痛性身目黄染入院半月
        //白陶土样的大便
        //咖啡色样白带
        //流涕1周，无明显发热，偶咳嗽，痰多。无气喘，腹泻7-8次稀便
        //2011年12月中旬起无明显诱因反复出现腹泻，约2-4次/日，黄色糊状，偶混有粘液脓血。无腹胀、腹痛；无排便困难、里急后重、肛门坠痛、大便变细；无头晕、乏力、面色苍白；无活动后心悸、气促；无恶心、呕吐、呕血、黑便；无发热、盗汗、咳嗽、咳痰、消瘦。2012年3月中旬求诊厦门长庚医院，行肠镜（2012-3-16）：“距肛门18cm见不规则肿物，约4*3.5cm，考虑乙状结肠癌。距肛门24-27cm，见8枚息肉。距肛门9CM，见3枚息肉。直肠及乙状结肠息肉予肠镜下切除”。病理（福建省肿瘤医院12-2462）：“1.乙状结肠腺癌；2.乙状结肠腺瘤；3.直肠腺瘤”。遂转诊福建省肿瘤医院，完善分期检查，于2012年3月22日全麻下行“腹腔镜辅助乙状结肠癌根治术”。术中见：“乙状结肠可及一肿物，大小约4*4.5cm，侵及浆膜，肠腔狭窄，肠系膜下动脉根部及乙状结肠动脉旁未触及肿大淋巴结，其他大肠未触及明显肿物”。术程顺利，术后恢复好。术后病理（2012-3-29福建省肿瘤医院12-03125）：“1.（乙状结肠）隆起型中分化腺癌（肿物大小3*2.5CM），侵犯肠周脂肪组织，脉管内见癌栓。标本两切端及另送“上、下切端”未见癌。“直肠上动脉旁淋巴结”7/0，“肠系膜下动脉根部淋巴结”4/0未见转移癌。2.管状腺瘤伴低级别上皮内肿瘤”。于2012年3月26日行DC-CIK免疫治疗1次。术后转诊我科，于2012年04月20日-2012年7月18日行FOLFOX方案（L-OHP130mgd1+CF0.6d1+5-FU0.5gd1+5-FU3.5gciv46hd1-214天/周期）化疗6周期，过程顺利，无不适。2012-7-1复查胸部CT：双肺散在点状影及右肺尖疑小结节，请随访观察。腹部CT：乙状结肠癌术后化疗后复查：无腹部CT旧片对照；膀胱左侧异常占位样影，建议MR复查；余全腹部CT平扫+增强扫描未见明显复发及转移证。评效；SD。目前进普食顺利，无发热、恶心、呕吐、呕血、黑便；无腹痛、腹胀、腹泻、便血；无头晕、乏力、面色苍白、活动后心悸、气促；无肛周坠痛、腰骶部酸痛；无咳嗽、咳痰、胸痛、气促、盗汗；无眼黄、尿黄、皮肤黄；无尿频、尿急、尿痛。为进一步治疗，今求诊我科，诊断为“乙状结肠中分化腺癌根治术后pT4aN0M0ⅡB期化疗后”收住入院。末次出院以来，精神体力尚好，生活自理。食欲食量正常。夜里睡眠好，大便同上述，小便正常，体重无明显增减。
        //刘XX,女，76岁，，，经查B超、CTA、MRCP显示肝门部胆管癌(IV型)，与家属沟通，认为年龄大，手术难度大，风险高，并发症发生率高(特别是胆漏)，家属仍要求手术。手术切除胆囊，不是肝十二指肠韧带骨骼化，切除肝门板后向肝组织内切开，肛门部胆管共7条，行肝门部胆管吻合，术后一过性胆漏，痊愈出院，随诊4个月患者感觉良好，排尿不畅,胎盘。


        //输出词频统计结果
        //wordFrequencyStatistics.dump();

        /*
        //准备文件
        Files.write(Paths.get("text-to-seg.txt"), Arrays.asList("张XX，男，74岁，确诊为肝癌，术前AFP23000ug/l，完善检查后成功实施右半肝切除，术后2月AFP下降至正常。术后半年随诊事AFP升至，B超和CT显示门静脉矢状部癌栓，大小约，在B超引导下行门静脉癌栓无水酒精注射术2次，术后1月AFP降至正常，复查B超和CT显示门静脉癌栓消失。"));
        //清除之前的统计结果
        wordFrequencyStatistics.reset();
        //对文件进行分词
        wordFrequencyStatistics.seg(new File("text-to-seg.txt"), new File("text-seg-result.txt"));
        //输出词频统计结果
        wordFrequencyStatistics.dump("file-seg-statistics-result.txt");*/
    }

    /**
     * 是否移除停用词
     *
     * @return
     */
    public boolean isRemoveStopWord() {
        return removeStopWord;
    }

    /**
     * 设置是否移除停用词
     *
     * @param removeStopWord 是否移除停用词
     */
    public void setRemoveStopWord(boolean removeStopWord) {
        this.removeStopWord = removeStopWord;
    }

    /**
     * 获取词频统计结果保存路径
     *
     * @return 词频统计结果保存路径
     */
    public String getResultPath() {
        return resultPath;
    }

    /**
     * 设置词频统计结果保存路径
     *
     * @param resultPath 词频统计结果保存路径
     */
    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    /**
     * 获取分词算法
     *
     * @return 分词算法
     */
    public SegmentationAlgorithm getSegmentationAlgorithm() {
        return segmentation.getSegmentationAlgorithm();
    }

    /**
     * 设置分词算法
     *
     * @param segmentationAlgorithm 分词算法
     */
    public void setSegmentationAlgorithm(SegmentationAlgorithm segmentationAlgorithm) {
        this.segmentation = SegmentationFactory.getSegmentation(segmentationAlgorithm);
    }

    /**
     * 对文本进行分词
     *
     * @param text 文本
     */
    public List<Word> seg(String text) {
        List<Word> words = segmentation.seg(text);
        words.parallelStream().forEach(word -> {
            //停用词过滤
            if (isRemoveStopWord() && StopWord.is(word.getText())) {
                return;
            }
            statistics(word, 1, statisticsMap);
        });
        return words.parallelStream().distinct().collect(Collectors.toList());
    }

    /**
     * 对文件进行分词
     *
     * @param input  待分词的文本文件
     * @param output 分词结果保存的文本文件
     * @throws Exception
     */
    public void seg(File input, File output) throws Exception {
        Utils.seg(input, output, isRemoveStopWord(), segmentation.getSegmentationAlgorithm(), word -> statistics(word, 1, statisticsMap));
    }

    /**
     * 统计词频
     *
     * @param word      词
     * @param times     词频
     * @param container 内存中保存词频的数据结构
     */
    private void statistics(String word, int times, Map<String, AtomicInteger> container) {
        container.putIfAbsent(word, new AtomicInteger());
        container.get(word).addAndGet(times);
    }

    /**
     * 统计词频
     *
     * @param word      词
     * @param times     词频
     * @param container 内存中保存词频的数据结构
     */
    private void statistics(Word word, int times, Map<String, AtomicInteger> container) {
        statistics(word.toString(), times, container);
    }

    /**
     * 将词频统计结果保存到文件
     *
     * @param resultPath 词频统计结果保存路径
     */
    public void dump(String resultPath) {
        this.resultPath = resultPath;
        dump();
    }

    /**
     * 将词频统计结果保存到文件
     */
    public void dump() {
        dump(this.statisticsMap, this.resultPath);
    }

    /**
     * 将内存中的词频统计结果写到文件
     *
     * @param map  内存中的词频统计结果
     * @param path 词频统计结果文件保存路径
     */
    private void dump(Map<String, AtomicInteger> map, String path) {
        try {
            //按分值排序
            List<String> list = map.entrySet().parallelStream().sorted((a, b) -> new Integer(b.getValue().get()).compareTo(a.getValue().intValue())).map(entry -> entry.getKey() + " " + entry.getValue().get()).collect(Collectors.toList());
            Files.write(Paths.get(path), list);
            if (list.size() < 100) {
                LOGGER.info("词频统计结果：");
                AtomicInteger i = new AtomicInteger();
                list.forEach(item -> LOGGER.info("\t" + i.incrementAndGet() + "、" + item));
            }
            LOGGER.info("词频统计结果成功保存到文件：" + path);
        } catch (Exception e) {
            LOGGER.error("dump error!", e);
        }
    }

    /**
     * 将多个词频统计结果文件进行合并
     *
     * @param mergeResultPath 合并结果文件路径
     * @param resultPaths     多个词频统计结果文件路径
     */
    public void merge(String mergeResultPath, String... resultPaths) {
        try {
            Map<String, AtomicInteger> map = new ConcurrentHashMap<>();
            for (String resultPath : resultPaths) {
                Files.lines(Paths.get(resultPath)).forEach(line -> {
                    String[] attrs = line.split("\\s+");
                    if (attrs != null && attrs.length == 2) {
                        statistics(attrs[0], Integer.parseInt(attrs[1]), map);
                    }
                });
            }
            dump(map, mergeResultPath);
        } catch (Exception e) {
            LOGGER.error("merge error!", e);
        }
    }

    /**
     * 清除之前的统计结果
     */
    public void reset() {
        this.statisticsMap.clear();
    }
}
