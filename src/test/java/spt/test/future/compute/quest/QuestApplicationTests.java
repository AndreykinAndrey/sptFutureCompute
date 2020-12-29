package spt.test.future.compute.quest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.function.Function;

@SpringBootTest
class QuestApplicationTests {

    @Test
    void testAsyncComputers() {
        Function<String, String> f1 = String::toUpperCase;
        Function<Integer, String> f2 = (i) -> "zzzzz" + i;

        Runnable run1 = () -> {
            try {
                AsyncComputerImpl<String, String> c =  AsyncComputerImpl.getInstance();
                Future<?> retStr = c.compute("str1", f1 );

                try {
                    System.out.println(retStr.get());
                    System.out.println("r1toString():" + c.toString());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

        };
        Runnable run2 = () -> {
            AsyncComputerImpl<Integer, String> c = AsyncComputerImpl.getInstance();
            Future<?> retStr = c.compute(2, f2 );

            try {
                System.out.println(retStr.get());
                System.out.println("r2toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        Runnable run3 = () -> {
            AsyncComputerImpl<String, String> c =  AsyncComputerImpl.getInstance();
            Future<?> retStr = c.compute("str3", f1 );

            try {
                System.out.println(retStr.get());
                System.out.println("r3toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        Runnable run4 = () -> {
            AsyncComputerImpl<String, String> c = AsyncComputerImpl.getInstance();
            Future<?> retStr = c.compute("str1", f1 );

            try {
                System.out.println(retStr.get());
                System.out.println("r4toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(run1);
        service.submit(run2);
        service.submit(run3);
        service.submit(run1);
        service.submit(run4);



        service.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AsyncComputerImpl c = AsyncComputerImpl.getInstance();
        System.out.println(c.getHitCount());
        System.out.println(c.getMissCount());


    }

    @Test
    void testComputers() {
        Function<String, String> f1 = String::toUpperCase;
        Function<Integer, String> f2 = (i) -> "zzzzz" + i;

        Runnable run1 = () -> {
            try {
                ComputeImpl<String, String> c =  ComputeImpl.getInstance();
                Future<?> retStr = c.compute("str1", f1 );

                try {
                    System.out.println(retStr.get());
                    System.out.println("r1toString():" + c.toString());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

        };
        Runnable run2 = () -> {
            ComputeImpl<Integer, String> c = ComputeImpl.getInstance();
            Future<?> retStr = c.compute(2, f2 );

            try {
                System.out.println(retStr.get());
                System.out.println("r2toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        Runnable run3 = () -> {
            ComputeImpl<String, String> c =  ComputeImpl.getInstance();
            Future<?> retStr = c.compute("str3", f1 );

            try {
                System.out.println(retStr.get());
                System.out.println("r3toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        Runnable run4 = () -> {
            ComputeImpl<String, String> c = ComputeImpl.getInstance();
            Future<?> retStr = c.compute("str1", f1 );

            try {
                System.out.println(retStr.get());
                System.out.println("r4toString():" + c.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(run1);
        service.submit(run2);
        service.submit(run3);
        service.submit(run1);
        service.submit(run4);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();



        ComputeImpl c = ComputeImpl.getInstance();
        System.out.println(c.getHitCount());
        System.out.println(c.getMissCount());


    }

}
