package PaintBall;

public class TeamArrayList implements ArrayList<Team>{
    public static final int DEFAULT_SIZE = 10;
    private Team[] array;
    private int len;

    public TeamArrayList() {
        array = new Team[DEFAULT_SIZE];
        len = DEFAULT_SIZE;
    }

    @Override
    public void add(Team e) {
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
    public Team get(int i) {
        return array[i];
    }

    @Override
    public void resize() {
        Team[] newArray = new Team[array.length >> 1 + array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    @Override
    public int len() {
        return len;
    }
}
