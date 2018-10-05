package io.crazy88.beatrix.e2e;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AssertionsHelper {

    private static final int MAX_SECONDS_TO_WAIT = 10;

    private static final int MILLISECONDS_TO_SLEEP = 1000;

    public static void retryUntilSuccessful(Runnable assertion) {
        retryUntilSuccessful(MAX_SECONDS_TO_WAIT, MILLISECONDS_TO_SLEEP, assertion);
    }

    public static void retryUntilSuccessful(int maxSecondsToWait, Runnable assertion) {

        retryUntilSuccessful(maxSecondsToWait, MILLISECONDS_TO_SLEEP, assertion);
    }

    public static void retryUntilSuccessful(int maxSecondsToWait, long millisecondsToSleep,
                                          Runnable assertion) {
        long start = System.currentTimeMillis();
        while (true) {
            try {
                System.out.println("Retrying assertion");
                assertion.run();
                break;
            } catch (Throwable e) {
                if ((System.currentTimeMillis() - start) / 1000 > maxSecondsToWait) {
                    throw e;
                }

                try {
                    if (millisecondsToSleep == 0) {
                        millisecondsToSleep = 100L;
                    }
                    TimeUnit.MILLISECONDS.sleep(millisecondsToSleep);
                } catch (InterruptedException ie) {
                    System.out.println("Unable to run retryUntilItPasses");
                }
            }
        }
    }
}
