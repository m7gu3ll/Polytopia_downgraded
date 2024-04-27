package PaintBall;

public interface ArrayList<T> {
    void add(T e);
    void remove(int i);
    T get(int i);
    void resize();

    int len();
}
