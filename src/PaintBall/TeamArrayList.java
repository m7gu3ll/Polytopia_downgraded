package PaintBall;

public class TeamArrayList implements ArrayList<Team>{
    public static final int DEFAULT_SIZE = 10;
    private Team[] array;
    private int len;

    public TeamArrayList() {
        array = new Team[DEFAULT_SIZE];
        len = 0;
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
    public void remove(Team e) {
        remove(find(e));
    }

    @Override
    public Team get(int i) {
        return array[i];
    }

    @Override
    public void resize() {
        Team[] newArray = new Team[(array.length >> 1)+ array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    @Override
    public int len() {
        return len;
    }

    @Override
    public int find(Team e) {
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
