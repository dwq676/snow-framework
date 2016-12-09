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

package com.zoe.snow.word.segmentation.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zoe.snow.word.file.WordFile;
import com.zoe.snow.word.recognition.RecognitionTool;
import com.zoe.snow.word.segmentation.SegmentationAlgorithm;
import com.zoe.snow.word.segmentation.Word;

/**
 * 基于词典的正向最大匹配算法
 * Dictionary-based minimum matching algorithm
 *
 * @author daiwenqing
 */
public class SmartMatching extends AbstractSegmentation {
    @Override
    public SegmentationAlgorithm getSegmentationAlgorithm() {
        return SegmentationAlgorithm.SmartMatching;
    }

    //TODO 增加输出路径
    @Override
    public List<Word> segImpl(String text){
        //System.out.println(text);
        List<Word> result = new ArrayList<>();
        List<String> healthList = new ArrayList<>();
        boolean hasPart = false;
        boolean hasOrSecretion = false;
        boolean hasAction = false;
        //文本长度
        final int textLen = text.length();
        //从未分词的文本中截取的长度
        int len = getInterceptLength();
        //剩下未分词的文本的索引
        int start = 0;
        //命中位置列表
        List<Integer> negativePos = new ArrayList<>();
        List<String> positiveWords = new ArrayList<>();
        //第几次命中
        int ndx = 0;

        /**
         * 动作，部位，分泌物的变量
         * @author Zhang Chunrong
         * @date 2016/9/7
         */
        List<String> actionWords=new ArrayList<>();
        List<String> partWords=new ArrayList<>();
        List<String> secretionWords=new ArrayList<>();



        //只要有词未切分完就一直继续
        while (start < textLen) {
            if (len > textLen - start) {
                //如果未分词的文本的长度小于截取的长度
                //则缩短截取的长度
                len = textLen - start;
            }
            boolean hit = true;
            //最大正向匹配的移动的最大值
            //用长为len的字符串查词典，并做特殊情况识别
            //&& !RecognitionTool.recog(text, start, len)


            //分词，开始的位置
            int maxLen=1;

            while (!getDictionary().contains(text, start, len)) {
                //如果长度为一且在词典中未找到匹配
                //则按长度为一切分


                String t = text.substring(start, start + len);

               /* if("后气".equals(t)){
                    int i=0;
                }*/

                if (RecognitionTool.isPositive(t)) {
                    positiveWords.add(t);
                }

                if (RecognitionTool.isNegative(t)) {
                    if (positiveWords.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        negativePos.add(ndx);
                        ndx++;
                    }
                }
                if (RecognitionTool.isHealthPosition(t)) {
                    if (healthList.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        //方位应该在最前面，否则不被识别，或者伴随症状出现
                        if (healthList.size() == 0){
                            maxLen=t.length();
                            healthList.add(t);
                        }
                    }
                }
                //识别动作
                if (RecognitionTool.isHeathAction(t)) {
                    if (healthList.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        healthList.add(t);
                        //往动作列表中添加词
                        actionWords.add(t);
                        hasAction = true;
                        maxLen=t.length();
                    }
                }

                if (RecognitionTool.isHealthAdv(t)
                        || RecognitionTool.isHealthAdj(t)) {
                    if (healthList.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        maxLen=t.length();
                        healthList.add(t);
                    }
                }
                //识别部位
                if (RecognitionTool.isHealthPart(t)) {
                    if (healthList.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        maxLen=t.length();
                        healthList.add(t);
                        //往部位列表中添加词
                        partWords.add(t);
                        hasPart = true;
                    }
                }
                //识别分泌物
                if (RecognitionTool.isHealthSecretion(t)) {
                    if (healthList.parallelStream().filter(c -> c.contains(t)).count() < 1) {
                        healthList.add(t);
                        secretionWords.add(t);
                        maxLen=t.length();
                        hasOrSecretion = true;
                    }
                }

                if (len == 1) {
                    hit = false;
                    break;
                }
                len--;
            }

            if (hit) {
                String subText = text.substring(start, start + len);
                //TODO 在字典中命中之后仍然去查找是否有部位，分泌物的描述信息
                //有了顿号，且前面已经存在阴性词，说明是对阴性的停顿
                if (subText.equals("、")) {
                    if (negativePos.size() > 0) {
                        negativePos.add(ndx);
                        ndx++;
                    }
                } else if (!subText.equals("-") && !subText.equals("+") && !subText.equals("*"))
                    addWord(result, text, start, len);
            }
            //从待分词文本中向后移动索引，滑过已经分词的文本
            if(maxLen>1){
                start += maxLen;
            }else {
                start += len;
            }
            /*start += len;*/

            //每一次成功切词后都要重置截取长度
            len = getInterceptLength();
        }

        List<Word> wordList = new ArrayList<>();
        //去除无部位、无分泌物的描述
        if (!(hasPart || hasOrSecretion))
            healthList = new ArrayList<>();
        //二次切记验证
        //todo  数量与单位
        List<Word> secWords = new MaximumMatching().segImpl(String.join("", healthList));
        //虽然验证找不到精确的匹配，但是有产物且有部位则为可识别
        if (secWords.size() < 1) {
            if (healthList.size() > 0) {
                String healthText = String.join("", healthList);
                //将匹配结果进行自由组合到字典中进行查找，如果存在就添加到症状提取结果中
                int resultLen = result.size();
                //TODO 递归实现全排列,并到字典中进行查找
                /*for (int hIndex=0;hIndex<healthList.size();hIndex++){
                    permutateSearch(result,healthList);
                }*/
                //部位与分泌物
                if (hasOrSecretion && hasPart) {
                    addCombination(result, partWords, secretionWords);
                }
                //部位与动作
                if (hasPart && hasAction) {
                    addCombination(result, partWords, actionWords);
                }
                //分泌物与动作
                if (hasOrSecretion && hasAction) {
                    addCombination(result, actionWords, secretionWords);
                }
                int resultLen2 = result.size();
                //有1.部位且有2.分泌物，有部位有3.动作,有产物有动作
                if (resultLen2 == resultLen) {
                    //进行文件的读写，输出这段话的Number_id,text文本信息，匹配的词根信息
                    File logFile = new File("D:/word/ZEMR_EMR_CONTENT_DYYY-log.txt");
                    if (!logFile.exists()) {
                        try {
                            logFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter(logFile, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // FileWriter fileWriter=new FileWriter(file,true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    //输出的字符串参数变量
                    String dataList = "";
                    WordFile WF=new WordFile();
                    int numberId=WF.getNumberId();
                    //text;要提取症状的句子，healthList:提取的症状列表
                    for (int k = 0; k < healthList.size(); k++) {
                        int index = k + 1;
                        if (k == healthList.size() - 1) {
                            dataList +=numberId+" "+text + " " + index + " " + healthList.get(k);
                        } else {
                            dataList +=numberId+" "+text + " " + index + " " + healthList.get(k) + "\n";
                        }
                    }
                    try {
                        bufferedWriter.write(dataList + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                //方位后只能跟部位
                postionMustWithPart(healthList);
                healthText = String.join("", healthList);
                if ((hasOrSecretion && hasPart) || (hasPart && hasAction) || (hasOrSecretion && hasAction))
                    addWord(result, healthText, 0, healthText.length());
                }
            }
        }
        wordList.addAll(secWords);
        //部位不能作为结尾
        if (partWords.size()>0 && result.size()>0){
            removeEndWithPart(result,partWords);
        }
        if (result.size() > 0) {
            negativePos.forEach(c -> {
                if (result.size() > c) {
                    if (result.get(c) != null)
                        result.get(c).setPositive(false);
                }
            });
        }
        result.forEach(c -> {
            if (c.isPositive()) {
                wordList.add(c);
            }
        });

        return wordList;
    }

    //排列提取的词根信息进行组合查找
    private void permutateSearch(List<Word> result, List<String> healthList) {
        String strCombination="";
        for(int i=0;i<healthList.size();i++){
            permutate(result,healthList,strCombination,i);
        }
    }


    //递归实现排列组合
    private void permutate(List<Word> result, List<String> healthList, String strCombination, int i) {
       /* if()*/
    }

    //方位后只能跟部位
    private void postionMustWithPart(List<String> healthList) {
        for(int i=0;i<healthList.size();i++){
            //查找方位所在的index
            if(RecognitionTool.isHealthPosition(healthList.get(i))){
                //判断下一个是不是部位
                if(i+1<healthList.size()){
                    if(!RecognitionTool.isHealthPart(healthList.get(i+1))){
                        //方位的下一个不是部位，删掉方位信息
                        healthList.remove(i);
                        //如果是阴性词汇或者动词也删除掉
                        if(i<healthList.size()){
                            while(i<healthList.size() && (RecognitionTool.isNegative(healthList.get(i)) || RecognitionTool.isHeathAction(healthList.get(i)))){
                                if(i<healthList.size()){
                                    healthList.remove(i);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //移除结果数据中以部位结尾的单词
    private void removeEndWithPart(List<Word> result, List<String> partWords) {
        for(int i=0;i<result.size();i++){
            for(int j=0;j<partWords.size();j++){
                Word word=result.get(i);
                if(word.getText().endsWith(partWords.get(j))){
                    result.remove(i);
                    break;
                }
            }
        }
    }

    //自由组合
    public void addCombination(List<Word> result,List<String> words,List<String> words2){
        for (int i=0;i<words.size();i++){
            for(int j=0;j<words2.size();j++){
                addCombinationWord(result,words.get(i)+words2.get(j));
            }
        }
    }
}
