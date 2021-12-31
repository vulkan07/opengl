import me.barni.Window;


public class Main {

    public static void main(String[] args) {
        Window window = new Window("Minecraft 2", 1920, 1080);
    }

    public static void binarySplitArray(int target, int[] array, int start, int end) {
        int halfIndex = (end-start)/2 + start;
        System.out.println(start + " - " + end);
        System.out.println("  "+halfIndex);

        int mid = array[halfIndex];

        if (mid == target)
        {
            System.out.println("\nFound!");
            System.out.println("  "+target);
            System.exit(0);
        }
        if (mid < target) {
            start += (end-start)/2;
        } else {
            end -= (end-start)/2;
        }
        binarySplitArray(target, array, start, end);
    }
}
