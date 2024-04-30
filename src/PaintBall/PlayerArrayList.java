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
    public void remove(Player e) {
        remove(find(e));
    }

    @Override
    public Player get(int i) {
        return array[i];
    }

    @Override
    public void resize() {
        Player[] newArray = new Player[(array.length >> 1) + array.length];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    @Override
    public int len() {
        return len;
    }

    @Override
    public int find(Player e) {
        int id = -1;
        int i = 0;
        while (i < len && id == -1) {
            if (e == array[i]) {
                id = i;
            }
            i++;
        }
        return id;
    }
}
