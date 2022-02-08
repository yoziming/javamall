package yoziming.mall.search.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main start ...");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任務1 煮紅蘿蔔start..");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任務1 end..");
            return "紅蘿蔔煮好了";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任務2 煮馬鈴薯start..");
            System.out.println("任務2 end..");
            return "馬鈴薯煮好了";
        });

        CompletableFuture<String> future3 = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println("任務3 組合配菜，需要配菜都煮熟");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任務3 end..");
            return "配菜完成";
        }, executor);

        CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2, future3);
        // all.get(); // 使方法阻塞，不阻塞若有人超時，主線程會提前關閉
        // System.out.println(future1.get() + future2.get() + future3.get());
        System.out.println("main end...");
    }
}

//
//     CompletableFuture<String> future = CompletableFuture
//             .supplyAsync(ThreadTest::asyncFunction, executor)
//             .whenComplete(ThreadTest::accept)
//             .exceptionally(ThreadTest::apply)
//             .handle(ThreadTest::handle)
//             .thenApply(ThreadTest::thenApply);
//     System.out.println("main end... 返回值：" + future.get());
// }
//
// public static String thenApply(String result) {
//     System.out.println("任務2啓動");
//     System.out.println("任務2可以獲取任務1的結果：" + result);
//     return "現在換煮馬鈴薯";
// }
//
// // 指派任務
// public static String asyncFunction() {
//     System.out.println("線程池執行任務 煮紅蘿蔔");
//     int i = 10 / 0; // 讓異常發生
//     return "煮好了";
// }
//
// // 指派任務完成後要幹的事
// public static void accept(String result, Throwable exception) {
//     System.out.println("獲取任務1的結果：" + result);
//     System.out.println("獲取任務1的異常：" + exception);
// }
//
// // 異常時要返回的
// public static String apply(Throwable exception) {
//     System.out.println("紅蘿蔔異常" + exception);
//     return "紅蘿蔔沒熟需要重煮";
// }
//
// public static String handle(String result, Throwable exception) {
//     System.out.println("獲取任務1的結果：" + result);
//     System.out.println("獲取任務1的異常：" + exception);
//     System.out.println("異常不會傳播，前面調用exceptionally方法處理了異常");
//     return result + "，並且基於任務1的結果，把鍋裡的東西撈出來";
// }


