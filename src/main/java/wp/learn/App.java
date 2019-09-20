package wp.learn;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {
        int i = 100;
        while (i > 1) {
            Singleton singleton = Singleton.getSingleton();
            Singleton singleton1 = Singleton.getSingleton();
            i--;
            if (singleton == singleton1) {
                System.out.println("单例一致！");
            } else {
                System.out.println("单例不一一致！");
            }
        }
    }
}
