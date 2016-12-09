package com.zoe.snow.word.recognition.health;

import com.zoe.snow.word.recognition.PersonName;
import com.zoe.snow.word.util.AutoDetector;
import com.zoe.snow.word.util.ResourceLoader;
import com.zoe.snow.word.util.WordConfTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 否词识别
 *
 * @author Dai Wenqing
 * @date 2016/8/15
 */
public class Adv {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonName.class);
    private static final Set<String> NW = new HashSet<>();

    static {
        reload();
    }

    public static void reload() {
        AutoDetector.loadAndWatch(new ResourceLoader() {

            @Override
            public void clear() {
                NW.clear();
            }

            @Override
            public void load(List<String> lines) {
                LOGGER.info("副词");
                for (String line : lines) {
                    if (!line.startsWith("#"))
                        add(line);
                }
                LOGGER.info("副词初始化完毕：" + NW.size());
            }

            @Override
            public void add(String line) {
                NW.add(line);
            }

            @Override
            public void remove(String line) {
                if (line.length() == 1) {
                    NW.remove(line);
                }
            }

        }, WordConfTools.get("health/adv.txt", "classpath:health/adv.txt"));
    }

    public static boolean is(String text) {
        return NW.contains(text);
    }
}
