package PaintBall;

public interface ArrayList<T> {
    void add(T e);
    void remove(int i);

    void remove(T e);

    T get(int i);
    void resize();

    int len();

    int find(T e);
}
