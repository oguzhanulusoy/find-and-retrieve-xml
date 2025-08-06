package org.vf;

import java.io.IOException;

import static org.vf.XmlDeepChildChecker.hasChildTagUnderParent;
import static org.vf.XmlQueryEngine.isExists;

public class PerformanceTest {
    private final static int REPEAT_COUNT = 10000;

    public static void main(String[] args) throws IOException {
        String xml = FileUtil.readFileToString(CommonConstants.FILE_PATH_INPUT);
        doTestXmlQueryEngine(xml);
        doTestXmlDeepChildChecker(xml);
        doTestXmlPathEngine(xml);
    }

    private static void doTestXmlQueryEngine(String xml) throws IOException {
        long startTime = System.nanoTime();
        System.out.println("286025407712858 için amf var mı? ");
        final String query = "exists(/*/objects/auc[imsi = '286025407712858']/amf)";
        for (int i = 0; i < REPEAT_COUNT; i++) {
            isExists(query, CommonConstants.FILE_PATH_INPUT);
        }

        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime; // nanoseconds
        double averageDuration = totalDuration / (double) REPEAT_COUNT;

        System.out.println("Toplam süre (ns): " + totalDuration);
        System.out.println("Ortalama süre (ns): " + averageDuration);
        System.out.printf("Ortalama süre (ms): %.6f\n", averageDuration / 1_000_000);
        System.out.println("doTestXmlQueryEngine çalışmayı tamamladı");
    }

    private static void doTestXmlDeepChildChecker(String xml) throws IOException {
        long startTime = System.nanoTime();
        System.out.println("286025407712858 için amf var mı? ");
        for (int i = 0; i < REPEAT_COUNT; i++) {
            hasChildTagUnderParent("auc", "amf", xml);
        }

        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime; // nanoseconds
        double averageDuration = totalDuration / (double) REPEAT_COUNT;

        System.out.println("Toplam süre (ns): " + totalDuration);
        System.out.println("Ortalama süre (ns): " + averageDuration);
        System.out.printf("Ortalama süre (ms): %.6f\n", averageDuration / 1_000_000);
        System.out.println("doTestXmlDeepChildChecker çalışmayı tamamladı");
    }

    private static void doTestXmlPathEngine(String xml) {
        long startTime = System.nanoTime();
        System.out.println("286025407712858 için amf var mı? ");
        final String query = "*/objects/auc[imsi = '286025407712858']/amf";
        for (int i = 0; i < REPEAT_COUNT; i++) {
            XmlPathEngine.isExists(xml, query);
        }

        long endTime = System.nanoTime();

        long totalDuration = endTime - startTime; // nanoseconds
        double averageDuration = totalDuration / (double) REPEAT_COUNT;

        System.out.println("Toplam süre (ns): " + totalDuration);
        System.out.println("Ortalama süre (ns): " + averageDuration);
        System.out.printf("Ortalama süre (ms): %.6f\n", averageDuration / 1_000_000);
        System.out.println("doTestXmlPathEngine çalışmayı tamamladı");
    }
}
