package PaintBall;

public class PlayerArrayList implements ArrayList<Player> {
    public static final int DEFAULT_SIZE = 10;
    private Player[] array;
    private int len;

    public PlayerArrayList() {
        array = new Player[DEFAULT_SIZE];
        len = 0;
    }

    @Override
    public void add(Player e) {
        if (len + 1 >= array.length) resize();
        array[len++] = e;
    }

    @Override
    public void remove(int i) {
        for (int j = i; j < len; j++) {
            array[j] = array[j + 1];
        }
        len--;
    }

    @Override
    public Player get(int i) {
        return array[i];
    }

    @Override
    public void resize() {
        Player[] newArray = new Player[array.length >> 1 + array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    @Override
    public int len() {
        return len;
    }


    public int find(Player defender) {
        boolean found = false;
        int i = 0;
        while (i < len && !found) {
            if (defender == array[i]) {
                found = true;
            }
        }
        return i;
    }
}
