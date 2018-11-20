package onlinePusher;


public class TimerGoOffline implements Runnable {

    static long time;
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            time=System.currentTimeMillis() / 1000 / 60;
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (TimerGoOffline.time-server.time>6) {
                WriteTextFile.write(start.ComputerAndoperatingSystem + " aus false false "+ server.clientAdress+" - entferter Computer wohl abgeschmiert" );
                System.out.println(server.time+"-"+TimerGoOffline.time);
            }
            System.gc();
        }
    }
}
