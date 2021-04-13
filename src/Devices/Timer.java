package Devices;

//TODO Dynamic Device
public class Timer {
    static boolean isWaiting = false;
    static int currTimerCount = 0;

    public static void increaseTimer(){
        if(Timer.isWaiting)
            Timer.currTimerCount++;
    }

    public static void sleep(int seconds){
        currTimerCount=0;
        isWaiting=true;
        //Timer ~ 18mal pro Sekunde..
        while((currTimerCount/18)<seconds){ }
        isWaiting=false;
    }
}
