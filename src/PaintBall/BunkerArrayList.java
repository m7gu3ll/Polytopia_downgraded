package PaintBall;

public class BunkerArrayList implements ArrayList<Bunker> {
    public static final int DEFAULT_SIZE = 10;
    private Bunker[] array;
    private int len;

    public BunkerArrayList() {
        array = new Bunker[DEFAULT_SIZE];
        len = DEFAULT_SIZE;
    }

    @Override
    public void add(Bunker e) {
        if (len + 1 >= array.length) resize();
        int low = 0;
        int high = len - 1;
        while (low < high) {
            int pivot = low + ((high - low) >> 1);
            int result = array[pivot].toString().compareTo(e.toString());
            if (result < 0) {
                low = pivot + 1;
            } else {
                high = pivot - 1;
            }
        }
        for (int j = len; j > low; j--) {
            array[j] = array[j - 1];
        }
        array[low] = e;
        len++;
    }

    @Override
    public void remove(int i) {
        for (int j = i; j < len; j++) {
            array[j] = array[j + 1];
        }
        len--;
    }

    @Override
    public Bunker get(int i) {
        return array[i];
    }

    @Override
    public void resize() {
        Bunker[] newArray = new Bunker[array.length >> 1 + array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    @Override
    public int len() {
        return len;
    }
}
